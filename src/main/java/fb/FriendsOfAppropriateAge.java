package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class FriendsOfAppropriateAge {
	
	//https://leetcode.com/problems/friends-of-appropriate-ages/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	 public int numFriendRequests(int[] ages) {

	        Arrays.sort(ages);
	        int l=0,res=0;
	        for(int i=0;i<ages.length;i++){
	           for(;ages[l] <= (0.5*ages[i] + 7) && l<i;l++) ;
	           res +=(i-l);
	           int j=i;
	           for(;j<ages.length && ages[j] == ages[i];j++); 
	            res +=(j-i-1);
	        }
	        return res;
	        
	    }
	 
	 @Test
	 public void testNumFriendRequests() {
		 assertEquals(4,numFriendRequests(new int[] {8,85,24,85,69}));
		 assertEquals(2,numFriendRequests(new int[] {16,16}));
		 
		 assertEquals(29,numFriendRequests(new int[] {73,106,39,6,26,15,30,100,71,35,46,112,6,60,110}));
	 }
	 
	 public static void main(String[] args) {
		System.out.println(String.format("%c", 'a'+'b') );
		//Character.valueOf('a').
		
		//Map.of('0',Map.of('0',Map.of('0',new Character[] {'0','0'} , '1', new Character[] {'0',''})));
		
		ArrayDeque<Integer> q = new ArrayDeque<>();
		
		System.out.println("a".substring(1).length());
		String.valueOf('a');
         Stream.concat( Stream.of(1,2,3), Stream.empty()).forEach(System.out::println);     
        
	}

}
