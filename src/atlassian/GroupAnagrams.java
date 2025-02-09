package atlassian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    	System.out.println(Arrays.deepToString(arr));
    	
    	
	}
}
