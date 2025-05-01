package fb;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class GroupShiftedStrings {
	
	  public List<List<String>> groupStrings(String[] strings) {

		    return Arrays.stream(strings).map(s->new java.util.AbstractMap.SimpleEntry<>(getPositionIntRepresentation(s),s)).collect(Collectors.groupingBy(java.util.AbstractMap.SimpleEntry::getKey, Collectors.mapping(java.util.AbstractMap.SimpleEntry::getValue, Collectors.toList()))).values().stream().collect(Collectors.toList());
		    }

		    private Integer getPositionIntRepresentation(String s){
		    	System.out.println(" got string "+s);
		        return Integer.parseInt( s.length()<2?"0" : IntStream.range(0, s.length()-2+1).mapToObj(i-> String.valueOf( Math.abs(s.charAt(i)-s.charAt(i+1)))).collect(Collectors.joining()));
		    }
		    
	
    @Test		    
	public void testGroupStrings() {	    
		assertEquals( Arrays.asList(Arrays.asList("acef"),Arrays.asList("a","z"),Arrays.asList("abc","bcd","xyz"),Arrays.asList("az","ba")), groupStrings(new String[] {"abc","bcd","acef","xyz","az","ba","a","z"}));
	}
    
    public static void main(String[] args) {
		System.out.println("a".charAt(1));
	}

}
