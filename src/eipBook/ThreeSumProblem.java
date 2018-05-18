package eipBook;
//This is the text editor interface. 
//Anything you type or change here will be seen by the other person in real time.

//arr = [10, 7, 24, 99, 100, 1, 4] num = 23
//[10, 7, 4]

//num = 40
//[24, 10, 7]

//num = 75
//[99, 1, 4]

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreeSumProblem {
 
 public static List<Integer> threeSum(List<Integer> xs, int target){
     java.util.Collections.sort(xs);
     ArrayList<Integer> res = new ArrayList<Integer>();
     boolean isDone =false;
     for(int i=0;i<xs.size() && !isDone;i++){
         int rem = target - xs.get(i);
         int j = i;
         int maxJ = xs.size()-1;
         while(j < maxJ){
             //System.out.println(rem);
             //System.out.println(maxJ);
             //System.out.println(xs.get(j)+xs.get(maxJ));
              if( ( (xs.get(j)+xs.get(maxJ)))  < rem){
             j++;
         }else if(  (xs.get(j)+xs.get(maxJ)) > rem){
             maxJ--;
         }else{
             //got result
             res.add(xs.get(i));
             res.add(xs.get(j));
             res.add(xs.get(maxJ));
             isDone = true;
             break;
         }
         }
        
         //
     }
     return res;
 }
 
 public static List<Integer> threeSumClosest(List<Integer> xs, int target){
     java.util.Collections.sort(xs);
     ArrayList<Integer> res = new ArrayList<Integer>();
     boolean isDone =false;
     int leastDiff = Integer.MAX_VALUE;
     int r1=-1,r2=-1,r3=-1;
     for(int i=0;i<xs.size() && !isDone;i++){
         int rem = target - xs.get(i);
         int j = i+1;
         int maxJ = xs.size()-1;
         while(j < maxJ){
             //System.out.println(rem);
             //System.out.println(maxJ);
             //System.out.println(xs.get(j)+xs.get(maxJ));
        	 int diff = Math.abs(rem - (xs.get(j)+xs.get(maxJ)));
        	 if(diff < leastDiff){
        		 leastDiff = diff;
        		 r1=i;r2=j;r3=maxJ;
        	 }
              if( ( (xs.get(j)+xs.get(maxJ)))  < rem){
             j++;
         }else if(  (xs.get(j)+xs.get(maxJ)) > rem){
             maxJ--;
         }else{
             //got result
//             res.add(xs.get(i));
//             res.add(xs.get(j));
//             res.add(xs.get(maxJ));
             isDone = true;
             break;
         }
         }
        
         //
     }
     return (r1==-1 || r2==-1 || r3==-1)?Collections.<Integer>emptyList():Arrays.asList(xs.get(r1),xs.get(r2), xs.get(r3));
 }
 
 @Test
 public void testThreeSum(){
	 assertEquals(Arrays.asList(1,4,24), threeSum(Arrays.asList(10, 7, 24, 99, 100, 1, 4), 29) );
 }
 
 @Test
 public void testThreeSumClosest(){
	 assertEquals(Arrays.asList(1,4,24), threeSumClosest(Arrays.asList(10, 7, 24, 99, 100, 1, 4), 29) );
	 assertEquals(Arrays.asList(4,7,10), threeSumClosest(Arrays.asList(10, 7, 24, 99, 100, 1, 4), 23) );
 }
 public static void main(String[] args) {
     //int a = 10;
     //List<Integer> nums = Arrays.asList(101,Integer.MAX_VALUE);
     System.out.println(threeSum(Arrays.asList(10, 7, 24, 99, 100, 1, 4), 29));
     
     // 1,4,7,10,24,99,100
 }
}

