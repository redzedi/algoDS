package atlassian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        return new ArrayList<List<String>>(	Arrays.asList(strs).stream()
		.collect(
				Collectors.groupingBy( str -> str.chars().sorted().mapToObj(i->String.valueOf(i)).reduce("", String::concat) )) .values());
		
	
        
    }
    
    
    public static void main(String[] args) {
		// 
    	
    	String str = "abc";
    	
     
    	Short[] arr = new Short[26];
    	Arrays.fill(arr, (short)0);
    	
//    	Arrays.asList("abc","pqr","bac","rqp").stream()
//    	.map(null)
    	
    	System.out.println(Arrays.deepToString(arr));
    	ArrayList<Short> ss = new ArrayList<>(26);
        Collections.fill(ss, (short)0);
        
       // Collections.
        Supplier<List<Short>> alphListMaker = ()-> IntStream.generate(()->0).limit(26).mapToObj(i->Short.valueOf((short)i)).collect(Collectors.toList());
        
        List<Short> alphList = alphListMaker.get();
        
        //str.chars().mapToObj(null)
        
        
        System.out.println(ss.size());
    	ss.add(25, (short)1);
    	
    	System.out.println(ss);
    	
	}
}
