package eipBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTest1 {
	
	public static void main(String[] args) {
		System.out.println(Arrays.asList(1,2,3));
		System.out.println(Arrays.asList("a,b,c".split(",")));
		System.out.println(Arrays.asList("   you'll    be fine  ".replaceAll("(\\w)\\s+(\\w)","$1|$2").replaceAll("^\\s+", "").replaceAll("\\s+$", "").split("\\|")[1]));
		System.out.println();
		
		String document =  
				"Practice makes perfect, you'll get perfecT by practice. just practice! just just just!!";
		Supplier<Stream<String>> strm =()->Stream.of(document.replaceAll("\\s+"," ").replaceAll("^\\s+", "").replaceAll("\\s+$", "").split(" ")).map(s->s.replaceAll("\\W","").toLowerCase());
		System.out.println("sum of chars "+strm.get().collect(Collectors.summingInt(String::length)));
		Supplier<List<Integer>> sup =  ()->new ArrayList<Integer>();
		System.out.println("sum of chars1 "+strm.get().collect( ()->Arrays.asList(0),(  a, s)-> a.set(0,a.get(0)+s.length()),(a,a1)->a.addAll(a1)));
		//System.out.println("sum of chars "+strm.get().reduce()
		LinkedHashMap<String,Integer> freqMap = new LinkedHashMap<>();
		
		strm.get().forEach(s->freqMap.merge(s, 1, Integer::sum));
		String[][] res =  freqMap.entrySet().stream().sorted((e1,e2)->e2.getValue()-e1.getValue()).map(e->new String[] {e.getKey(),e.getValue().toString()}).toArray(l->{String t[][] = new String[l][2]; return t;});
		System.out.println(Arrays.asList(res).stream().map(Arrays::toString).reduce("", String::concat));
	}

}
