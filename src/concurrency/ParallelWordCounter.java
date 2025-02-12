package concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ParallelWordCounter implements AutoCloseable {
    private static final Pattern WORD_PATTERN = Pattern.compile("\\s+");
    private static final int CHUNK_SIZE = 8 * 1024 * 1024; // 8MB chunks
    private static final int MERGE_BATCH_SIZE = 100_000;
    
    private final Path filePath;
    private final Path tempDir;
    private final ForkJoinPool customThreadPool;
    
    public ParallelWordCounter(Path filePath, int parallelism) throws IOException {
        this.filePath = filePath;
        this.tempDir = Files.createTempDirectory("word_count_");
        this.customThreadPool = new ForkJoinPool(parallelism);
    }
    

    
    private Stream<Path> processFileInChunks() throws IOException {
        return StreamSupport.stream(
            new ChunkSpliterator(filePath, CHUNK_SIZE),
            true  // parallel
        )
        .map(this::processChunk)
        .map(this::writeCountsToDisk)
        .filter(Optional::isPresent)
        .map(Optional::get);
    }
    
    private Map<String, Long> processChunk(String chunk) {
        return WORD_PATTERN.splitAsStream(chunk.toLowerCase().trim())
            .filter(word -> !word.isEmpty())
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            ));
    }
    
    private Optional<Path> writeCountsToDisk(Map<String, Long> counts) {
        if (counts.isEmpty()) return Optional.empty();
        
        try {
            Path tempFile = Files.createTempFile(tempDir, "counts_", ".txt");
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
                for (Map.Entry<String, Long> entry : counts.entrySet()) {
                    writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
                }
            }
            return Optional.of(tempFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private Stream<Path> mergePaths(List<Path> paths) {
        if (paths.size() <= 1) {
            return paths.stream();
        }
        
        // Group files and merge them
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(
                new Iterator<Path>() {
                    private final Queue<Path> fileQueue = new LinkedList<>(paths);
                    
                    @Override
                    public boolean hasNext() {
                        return !fileQueue.isEmpty();
                    }
                    
                    @Override
                    public Path next() {
                        List<Path> batch = new ArrayList<>();
                        for (int i = 0; i < 3 && !fileQueue.isEmpty(); i++) {
                            batch.add(fileQueue.poll());
                        }
                        
                        if (batch.size() == 1) {
                            return batch.get(0);
                        }
                        
                        try {
                            Path merged = mergeFiles(batch);
                            batch.forEach(this::deleteFile);
                            return merged;
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }
                    
                    private void deleteFile(Path path) {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            // Log error but continue
                            e.printStackTrace();
                        }
                    }
                },
                Spliterator.ORDERED),
            false
        ).flatMap(path -> mergePaths(Collections.singletonList(path)));
    }
    
    private class ParallelMergeTask extends RecursiveTask<Path> {
        private final List<Path> files;
        private final int start;
        private final int end;
        
        ParallelMergeTask(List<Path> files, int start, int end) {
            this.files = files;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Path compute() {
            int length = end - start;
            if (length <= 3) {
                try {
                    return mergeFiles(files.subList(start, end));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            
            int mid = start + length / 2;
            ParallelMergeTask leftTask = new ParallelMergeTask(files, start, mid);
            ParallelMergeTask rightTask = new ParallelMergeTask(files, mid, end);
            
            leftTask.fork();
            Path rightResult = rightTask.compute();
            Path leftResult = leftTask.join();
            
            try {
                return mergeFiles(Arrays.asList(leftResult, rightResult));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
    
    public Map<String, Long> countWords(int topN) throws IOException {
        try {
            // Process file in chunks and get intermediate files
            List<Path> intermediateFiles = processFileInChunks()
                .collect(Collectors.toList());
            
            // Parallel merge using ForkJoinPool
            Path finalMergedFile = customThreadPool.submit(
                new ParallelMergeTask(intermediateFiles, 0, intermediateFiles.size())
            ).get();
            
            // Read final results
            return readCountsFromFile(finalMergedFile)
                .collect(Collectors.groupingBy(
                    WordCount::word,
                    Collectors.summingLong(WordCount::count)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("Parallel merge failed", e);
        } finally {
            close();
        }
    }
    
    // Enhanced mergeFiles with memory-efficient streaming for low cardinality words
    private Path mergeFiles(List<Path> files) throws IOException {
        Path outputFile = Files.createTempFile(tempDir, "merged_", ".txt");
        
        // Use a compact counting map for low cardinality words
        Map<String, LongAdder> wordCounts = new ConcurrentHashMap<>();
        
        // Process files in parallel streams
        files.parallelStream().forEach(file -> {
            try {
                Files.lines(file).forEach(line -> {
                    String[] parts = line.split("\t");
                    wordCounts.computeIfAbsent(parts[0], k -> new LongAdder())
                        .add(Long.parseLong(parts[1]));
                });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        
        // Write merged results
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            for (Map.Entry<String, LongAdder> entry : wordCounts.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue().sum() + "\n");
            }
        }
        
        // Clean up input files
        files.forEach(file -> {
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                System.err.println("Failed to delete intermediate file: " + file);
            }
        });
        
        return outputFile;
    }
    
    
    private Stream<WordCount> readCountsFromFile(Path path) {
        try {
            return Files.lines(path)
                .map(line -> {
                    String[] parts = line.split("\t");
                    return new WordCount(parts[0], Long.parseLong(parts[1]));
                });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private record WordCount(String word, long count) {}
    
    // Custom Spliterator for chunking file
    private static class ChunkSpliterator extends Spliterators.AbstractSpliterator<String> {
        private final BufferedReader reader;
        private final int chunkSize;
        private final char[] buffer;
        
        ChunkSpliterator(Path path, int chunkSize) throws IOException {
            super(Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.NONNULL);
            this.reader = Files.newBufferedReader(path);
            this.chunkSize = chunkSize;
            this.buffer = new char[4096];
        }
        
        @Override
        public boolean tryAdvance(Consumer<? super String> action) {
            try {
                StringBuilder chunk = new StringBuilder(chunkSize);
                int totalRead = 0;
                int charsRead;
                
                while (totalRead < chunkSize && 
                       (charsRead = reader.read(buffer)) != -1) {
                    chunk.append(buffer, 0, charsRead);
                    totalRead += charsRead;
                }
                
                if (totalRead > 0) {
                    action.accept(chunk.toString());
                    return true;
                }
                
                reader.close();
                return false;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
    
    @Override
    public void close() {
        try {
            Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete: " + path);
                    }
                });
        } catch (IOException e) {
            System.err.println("Failed to clean up temporary directory: " + tempDir);
        }
    }
    
    public static void main(String[] args) {
        try (ParallelWordCounter counter = new ParallelWordCounter(Path.of("input.txt"), Runtime.getRuntime().availableProcessors())) {
            counter.countWords(20)
                .entrySet().stream()
                .forEach(entry -> 
                    System.out.printf("%s: %d%n", entry.getKey(), entry.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}