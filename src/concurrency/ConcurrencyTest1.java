package concurrency;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.OptionalInt;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class ConcurrencyTest1 {
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		//readWriteLockTest();
		
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		
		//offer at end
		dq.offer(1);
		dq.offer(2);
		dq.offer(3);
		
		//poll from front
		System.out.println(dq.poll());
		System.out.println(dq.poll());
		System.out.println(dq.poll());
		
		int oM=  Arrays.stream(new int[] {1,2,3}).max().getAsInt();
		
		
		
		
		
//		ex.shutdownNow();
		
		
	}

	private static void readWriteLockTest() throws InterruptedException {
		final HashMap<Integer,String> mp = new HashMap<>();
		
		ReentrantReadWriteLock lk = new ReentrantReadWriteLock();
		
		Function<Integer, Runnable> readerTaskFactory = (Integer i) -> {
		   final AtomicInteger k = new AtomicInteger();
		    return ()->{
				String lastVal = "";
				try {
					
					while(k.get() < 5) {
						
						try {
							
							 lk.readLock().lock();
							 
							 System.out.println(" Reader task "+i+" running . Round --> "+ k.get()+" Read value -->"+mp.get(i)+" threads waiting on readLock "+lk.getReadLockCount());
							 Thread.sleep(Duration.ofSeconds(2));
							 lk.readLock().unlock();
							 System.out.println(" Unlocked !! Reader task "+i+" running . Round --> "+ k.get()+" Read value -->"+mp.get(i)+" threads waiting on readLock "+lk.getReadLockCount());
							 if(mp.get(i) != null && !mp.get(i).equals(lastVal) ) {
								 k.incrementAndGet();
								 lastVal = mp.get(i);
							 }
							 
							 Thread.sleep(Duration.ofSeconds(1));
							
						}catch(InterruptedException e) {
							System.out.println("Thread  "+i+" interrupted "+e);
						}
			    		
						 
			    	}
					
					System.out.println("Reader thread completed "+i);
					
				}finally {
					Thread.currentThread().interrupt();
				}
		    	
				
			
			};
		};
		
		try(ExecutorService ex = Executors.newCachedThreadPool();){
			
			for (int j = 0; j < 5; j++) {
				
				ex.submit(readerTaskFactory.apply(j));
			}
			
			Thread.sleep(Duration.ofMillis(500L));
			
			for (int j = 0; j < 5; j++) {
				
				lk.writeLock().lock();
				
				System.out.println("Writer thread going to write");
				
				for (int jj = 0; jj < 5; jj++) {
					
					String currVal = mp.getOrDefault(jj, String.valueOf(jj));
					
					mp.put(jj, currVal+"-"+currVal);
				}
				Thread.sleep(Duration.ofSeconds(1));
				lk.writeLock().unlock();
				System.out.println("Writer thread going to Sleep");
			}
			
			System.out.println("Writer thread - writing done  sleeping for 10 seconds ");
			Thread.sleep(Duration.ofSeconds(10L));

			System.out.println("Writer thread - awaiting termination ");
			
		ex.awaitTermination(5, TimeUnit.SECONDS);
		System.out.println("Writer thread - terminated ");
			
		}
	}

}
