package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

import org.junit.Test;

public class FindHighAccessEmployees {
	
	//https://leetcode.com/problems/high-access-employees/description/?envType=company&envId=atlassian&favoriteSlug=atlassian-three-months
	
	 public List<String> findHighAccessEmployees(List<List<String>> access_times) {
		 
		 
//	     if(access_times==null || access_times.size()<2) {
//	    	 return Collections.emptyList();
//	     }else {
	    	 return  access_times.stream()
	    			 .collect(groupingBy(as->as.get(0), mapping(as->parseHHMMTime(as.get(1)), toList())))
	    			 .entrySet().stream()
	    			 .map(e-> new AbstractMap.SimpleImmutableEntry<String, List<List<Integer>>>(e.getKey() ,e.getValue().stream().sorted(FindHighAccessEmployees::timeComparator).collect(toList()) ))
	    			 .map(e-> new AbstractMap.SimpleImmutableEntry<String, Integer>(e.getKey() ,findMaxCountOfTimesIn1HrWindow(e.getValue()) ))
	    			 .collect(groupingBy( Map.Entry::getValue , mapping( Map.Entry::getKey, toList())))
	    			 .entrySet().stream()
	    			 .filter(e->e.getKey()>=3)
	    			 //.max(Map.Entry.comparingByKey())
	    			 .map(Map.Entry::getValue)
	    			 .map(Collection::stream)
	    			 .collect(reducing(Stream::concat))
	    			 .map(s->s.collect(toList()))
	    			 .orElse(new ArrayList<String>());
	    // }
		 
		
	    }
	 
	 private static List<Integer> parseHHMMTime(String dt) {
		 return Arrays.asList(Integer.parseInt(dt.substring(0,2)) , Integer.parseInt(dt.substring(2)) );
	 }
	 
	 private static int minutesBetweenTimes(List<Integer> lowerDt , List<Integer> higherDt) {
		 return (higherDt.get(0)-lowerDt.get(0))*60 + (higherDt.get(1)-lowerDt.get(1));
	 }
	 
	 private static int timeComparator(List<Integer> dt1 , List<Integer> dt2) {
		 
		 if(dt1.get(0) != dt2.get(0)) {
			 return dt1.get(0) - dt2.get(0);
		 }else {
			 return dt1.get(1) - dt2.get(1);
		 }
		 
	 }
	 
	 private static int findMaxCountOfTimesIn1HrWindow(List<List<Integer>> xs) {
		 int lt=0, rt=1, max1hrTimesCnt = 0, curr1hrTimesCnt=1;
		 
		 
		 while(lt<=rt && rt < xs.size()) {
			 System.out.println("examinging "+xs.get(lt)+" -- "+ xs.get(rt)+" -- "+ minutesBetweenTimes(xs.get(lt), xs.get(rt)));
			 int intervalLen = minutesBetweenTimes(xs.get(lt), xs.get(rt));
				/*
				 * if(intervalLen == 0) { // curr1hrTimesCnt++; rt++; }else
				 */ if(intervalLen < 60 ) {
					 if(lt!=rt)
						 curr1hrTimesCnt++;
				 rt++;
			 }else {
				 if(max1hrTimesCnt < curr1hrTimesCnt) {
					 max1hrTimesCnt = curr1hrTimesCnt;
				 }
				 curr1hrTimesCnt = 1;
				 lt++;
			 }
		 }
		 
		 if(max1hrTimesCnt < curr1hrTimesCnt) {
			 max1hrTimesCnt = curr1hrTimesCnt;
		 }
		 
		 return max1hrTimesCnt;
	 }
	 
	 
	 @Test
	 public void testFindHighAccessEmployees() {
		 assertEquals(Arrays.asList("a"), findHighAccessEmployees(Arrays.asList(Arrays.asList("a","0549"),Arrays.asList("b","0457"),Arrays.asList("a","0532"),Arrays.asList("a","0621"),Arrays.asList("b","0540"))));
		 assertEquals(Arrays.asList("c", "d"), findHighAccessEmployees(Arrays.asList(Arrays.asList("d","0002"),Arrays.asList("c","0808"),Arrays.asList("c","0829"),Arrays.asList("e","0215"),Arrays.asList("d","1508"),Arrays.asList("d","1444"),Arrays.asList("d","1410"),Arrays.asList("c","0809"))));
		 assertEquals(Arrays.asList("ab", "cd"), findHighAccessEmployees(Arrays.asList(Arrays.asList("cd","1025"),Arrays.asList("ab","1025"),Arrays.asList("cd","1046"),Arrays.asList("cd","1055"),Arrays.asList("ab","1124"),Arrays.asList("ab","1120"))));
		 assertEquals(Arrays.asList(), findHighAccessEmployees(Arrays.asList(Arrays.asList("baipstt","1456"))));
		 assertEquals(Arrays.asList(), findHighAccessEmployees(Arrays.asList(Arrays.asList("a","0039"),Arrays.asList("a","0042"))));
		 assertEquals(Arrays.asList("ff"), findHighAccessEmployees(Arrays.asList(Arrays.asList("ff","1508"),Arrays.asList("ff","1508"),Arrays.asList("ff","1516"))));
		 assertEquals(Arrays.asList("akuhmu"), findHighAccessEmployees(Arrays.asList(Arrays.asList("akuhmu","0454"),Arrays.asList("aywtqh","0523"),Arrays.asList("akuhmu","0518"),Arrays.asList("ihhkc","0439"),Arrays.asList("ihhkc","0508"),Arrays.asList("akuhmu","0529"),Arrays.asList("aywtqh","0530"),Arrays.asList("aywtqh","0419"))));
		 // 
	 }
	 
	 public static void main(String[] args) {
		System.out.println("0520".substring(0,2)+" -- "+"0520".substring(2));
		System.out.println(FindHighAccessEmployees.parseHHMMTime("0520"));
		
		System.out.println("minutesBetweenTimes "+minutesBetweenTimes(parseHHMMTime("1149"), parseHHMMTime("1246")));
		
		
		List<List<Integer>> ys = Arrays.asList( parseHHMMTime("1149"), parseHHMMTime("0520"), parseHHMMTime("0020"), parseHHMMTime("0529"),parseHHMMTime("1248"), parseHHMMTime("1249"),  parseHHMMTime("1246"));
		Collections.sort(ys, FindHighAccessEmployees::timeComparator);
		System.out.println(ys);
		System.out.println(findMaxCountOfTimesIn1HrWindow(ys));
		System.out.println("findMaxCountOfTimesIn1HrWindow --> "+findMaxCountOfTimesIn1HrWindow( Arrays.asList( parseHHMMTime("0532") , parseHHMMTime("0549"), parseHHMMTime("0621"))));
		System.out.println("findMaxCountOfTimesIn1HrWindow 1 --> "+findMaxCountOfTimesIn1HrWindow( Arrays.asList( parseHHMMTime("0808") , parseHHMMTime("0809"), parseHHMMTime("0829"))));
		
		System.out.println(Arrays.asList("a","b","n","a","b").stream()
				.collect(groupingBy(Function.identity(), counting()))
				.entrySet()
				.stream()
				.collect(groupingBy( Map.Entry::getValue , mapping( Map.Entry::getKey, toList())))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByKey())
				.map(Map.Entry::getValue)
				.orElse(new ArrayList<>()));
		        
	}

}
