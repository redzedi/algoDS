package serde;

import java.util.Arrays;
import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class GsonDeserializatonExplore {

	static class Tst1{
		public String s1;
		public Integer i1;
		public String[] ss;
	}
	
	public static void main(String[] args) {
		
		Tst1 t1 = new Tst1();
		
		t1.s1="st1";
		t1.i1=42;
		t1.ss = new String[] {"s1","s2"};
		
		
		Gson g = new Gson();
		System.out.println(g.toJson(t1));
		
//		BitSet d = new BitSet(2);
//		System.out.println(d.cardinality());
//		d.set(0);
//		System.out.println(d.cardinality());
		 int[] s = new int[] {3,3,2};
		 
		 //Arrays.
		PriorityQueue<Integer> pq = new PriorityQueue<>((i1,i2)->s[i1]-s[i2]);
		pq.offer(0);
		pq.offer(1);
		pq.offer(2);
		
		while(!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
		
		System.out.println(0%1);
		
		TreeMap<Integer, TreeSet<String>> fMap = new TreeMap<>();
		
		fMap.put(1, new TreeSet<>());
		fMap.firstEntry().getValue().getFirst();
		
		int n = "1fad".charAt(0)-'0';
		
	
		
    Matcher m = 		Pattern.compile("\\d+").matcher("100abcd");
    if(m.find()) {
    	System.out.println("Found match at !! -- "+m.start()+" -- "+m.end());
    }
      StringBuilder sb = new StringBuilder();
      
		System.out.println(Arrays.toString(  "ab12cd".split("\\d")));
		System.out.println("abcd".split("\\d").length);
		System.out.println( "ab12cd".split("\\d")[2]);
		System.out.println( "ab12cd".toLowerCase());
		
		System.out.println('a' == 'A');
		
	}
}
