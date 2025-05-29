package salesforce;

import java.util.Collections;
import java.util.List;

//https://leetcode.com/problems/closest-equal-element-queries/description/?envType=company&envId=salesforce&favoriteSlug=salesforce-thirty-days

public class ClosestEqualElement {
	
	public static void main(String[] args) {
		
		System.out.println(Collections.binarySearch(List.of(2,3,4).subList(1, 2), 4));
		
		char c =Character.forDigit(1, 10);
		
		System.out.println(c);
		for(char c1:"abc".toCharArray()) {
			System.out.println(c1);
		}
	}

}
