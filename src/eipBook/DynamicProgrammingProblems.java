package eipBook;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class DynamicProgrammingProblems {
	
	public int longestArithmaticProgression(final List<Integer> xs) {
		int n = xs.size();
		int[][] dp = new int[n][n];
		int maxAPLength = Integer.MIN_VALUE;
		Collections.sort(xs);
		for(int i=0;i<n;i++){
			dp[i][n-1] = 2;
		}
		
		for(int j=n-2;j>=0;j--){
			int i=j-1,k=j+1;
			
			int twiceJ = 2*xs.get(j);
			while(i>=0 && k<n){
				int ikSum = xs.get(i)+xs.get(k);
				if(ikSum>twiceJ){
					dp[i][j]  = 2;
					i--;
				}else if(ikSum<twiceJ){
					k++;
				}else{
					dp[i][j] = dp[j][k] + 1;
					
					if(dp[i][j] > maxAPLength){
						maxAPLength = dp[i][j];
					}
					i--;
					k++;
					//break;
				}
			}
			
			while(i>=0){
				dp[i--][j]=2;
			}
			
		}
		return maxAPLength;
	}
	
	
	//Levenshtein distance
	public int minDistance(String a, String b) {
		
		if(a.isEmpty() ){
			return b.length();
		}else if(b.isEmpty()){
			a.length();
		}
		
		int[][] dp = new int[a.length()+1][b.length()+1];
		
		dp[0][0] = 0;
		
		for(int i=1;i<=a.length();i++){
		    dp[i][0]=i;
		}
		for(int j=1;j<=b.length();j++){
		    dp[0][j]=j;
		}
		for(int i=1;i<=a.length();i++){
			for(int j=1;j<=b.length();j++){

				if(a.charAt(i-1) == b.charAt(j-1)){
					dp[i][j] = dp[i-1][j-1];
				}else{
					dp[i][j] = 1 + Math.min(dp[i-1][j-1],Math.min(dp[i][j-1],dp[i-1][j]));
				}
			}
		}
		return dp[a.length()][b.length()];
    }
	
	 // max product subarray - Kadane's algorithm (variation)
	 public int maxProduct(final List<Integer> A) {
		  int maxTillNow = A.get(0);
		 int  minArrSumAtCurrPos = A.get(0);
		 int  maxArrSumAtCurrPos = A.get(0);
		  
		  for(int i=1;i<A.size();i++){
			  int tmpMin = Math.min(A.get(i), Math.min(minArrSumAtCurrPos*A.get(i), maxArrSumAtCurrPos*A.get(i)));
			  int tmpMax = Math.max(minArrSumAtCurrPos*A.get(i),Math.max(maxArrSumAtCurrPos*A.get(i),A.get(i)));
			  
			  minArrSumAtCurrPos = tmpMin;
			  maxArrSumAtCurrPos = tmpMax;
			  
			  if(maxArrSumAtCurrPos > maxTillNow){
				  maxTillNow = maxArrSumAtCurrPos;
			  }
		  }
		  
		  return maxTillNow;
	    }
	 
	 public int threeSumClosest(ArrayList<Integer> A, int B) {
		 java.util.Collections.sort(A);
		 int res = Integer.MAX_VALUE;
		 int minSum = -1;
		 for(int i=0;i<A.size();i++){
			 int k = i +1;
			 int j = A.size()-1;
			 while(k<j){
				 int currDiff = Math.abs(B - (A.get(i)+A.get(k) + A.get(j))) ;
				 minSum = currDiff<res?(A.get(i)+A.get(k) + A.get(j)):minSum;
				 res = currDiff<res?currDiff:res;
				 System.out.println(currDiff+" --- "+minSum);
				 if(B < (A.get(i)+A.get(k) + A.get(j))){
					j--;
				 }else{
					 k++;
				 }
			 }
		 }
		 return minSum;
	    }
	
	 //n^2 solution - WRONG
	 public int maxProfit(final List<Integer> ps) {
		 
		 int n = ps.size();
		 int maxGain = 0;
		 
		 if(n<2){
			 return 0;
		 }
		 
		 int[] s = new int[n];
		 
		 Arrays.fill(s,0);
		 s[1] = ps.get(1)>ps.get(0)?ps.get(1)-ps.get(0):0;
		 
		 if(s[1]>maxGain){
			 maxGain = s[1];
		 }
		 
		 for(int i=2;i<n;i++){
			 
			 s[i] = Math.max(s[i],(ps.get(i)-ps.get(0)));
			 for(int j=0;j<i;j++){
				 s[i] = Math.max(s[i],s[j]+(ps.get(i)-ps.get(j+1)));
			 }
			 if(s[i]>maxGain){
				 maxGain = s[i];
			 }
			 
		 }
		 
		 return maxGain;
		 
	    }
	//ouput max length array of indexes from xs that add up to r
		 // the index output should be lexicographically minimal
		 public ArrayList<Integer> solve(int r, ArrayList<Integer> xs) {
			 int minElem = Integer.MAX_VALUE;
			 int minElemIdx = -1;
			 for(int i= 0 ; i<xs.size();i++){
				 if(xs.get(i) < minElem){
					 minElem = xs.get(i);
					 minElemIdx = i;
				 }
			 }
			 int currCount = r/minElem;
			 Integer[] resArr = new Integer[currCount];
			 
			 for(int k=0;k<currCount;k++){
				 resArr[k] = minElemIdx;
			 }
			 int resSum = currCount*minElem;
			 int subIdx = 0; //index for possible 
			 for(int i=0;i<=minElemIdx && subIdx<currCount;){
				 if(xs.get(i) <= (r-(resSum-minElem))){
						 resArr[subIdx++] = i;
					 resSum = resSum-minElem+xs.get(i);
				 }else{
					 i++;
				 }
			 }
			 return new ArrayList<Integer>(Arrays.asList(resArr));
		 }
		 
		  // first increasing , then decreasing subsequence - bitonic subsequence
		  public int longestSubsequenceLength(final List<Integer> xs) {
			  if(xs.size() == 0){
   	  	      return 0;
   	  	  }
			  int[] lis = new int[xs.size()];
			  int[] lds = new int[xs.size()];
			  Arrays.fill(lis, 1);
			  Arrays.fill(lds, 1);
			  int n = xs.size();
			  int res=Integer.MIN_VALUE;
			  for(int i=1;i<n;i++){
				  for(int j=0;j<i;j++){
					  if(xs.get(i) > xs.get(j)){
						  lis[i] = Math.max(lis[i], lis[j]+1);
					  }
				  }
				  
			  }
			  
			  for(int i=n-1;i>=0;i--){
				  for(int j=i+1;j<n;j++){
					  if(xs.get(i) > xs.get(j)){
						  lds[i] = Math.max(lds[i],lds[j]+1);
					  }
				  }
			  }
			  
			  for(int i=0;i<n;i++){
				  if((lis[i]+lds[i]-1)>res){
					  res = lis[i]+lds[i]-1;
				  }
			  }
			  return res;
		    }
	
		  public int isInterleave(String s1, String s2,String t){
			  int s1n = s1.length();
				 int s2n = s2.length();
				 int tn = t.length();
				 
				 if((s1n+s2n) != tn){
					 return 0;
				 }
				 
			  boolean[][] dp = new boolean[s2n+1][s1n+1];
			  
			 // dp[0][0] = false;
			  
			  for(int i=0;i<s2n+1;i++){
				  for(int j=0;j<s1n+1;j++){
					  
					  if(i==0 && j==0){
						  dp[i][j] = true;
					  }else if(i==0){
						  dp[0][j] = dp[0][j-1] && (t.charAt(j-1) == s1.charAt(j-1));
					  }else if(j==0){
						  dp[i][0] = dp[i-1][0] && (t.charAt(i-1) == s2.charAt(i-1));
					  }else {
						  dp[i][j] = (((t.charAt(i+j-1) == s2.charAt(i-1)) && dp[i-1][j]) || ((t.charAt(i+j-1) == s1.charAt(j-1)) && dp[i][j-1])); 
					  }
				  }
			  }
			  
			  return dp[s2n][s1n]?1:0;
			  
		  }
		 
		 public int isInterleave1(String s1, String s2,String t){
			 int s1n = s1.length();
			 int s2n = s2.length();
			 int tn = t.length();
			 
			 int[][] dp = new int[tn+1][ s1n+1];
			 String[][] addedStr = new String[tn+1][s1n+1];
			 
			 boolean[] isMatched = new boolean[s1n];
			 
			 dp[0][0] = 0;
			 addedStr[0][0] = "";
			 
			 for(int i=1; i < s1n+1;i++){
				 dp[0][i] = i;
				 //addedStr[0][i] = String.valueOf(s1.charAt(i-1));
				 //addedStr[0][i] = addedStr[0][i-1]+String.valueOf(s1.charAt(i-1));
				 addedStr[0][i] = s1.substring(0,i);
				 
			 }
			 
			 for(int i=1; i < tn+1;i++){
				 dp[i][0] = i;
				 //addedStr[i][0] =  String.valueOf(t.charAt(i-1));
				 // addedStr[i][0] = addedStr[i-1][0] + String.valueOf(t.charAt(i-1));
				 addedStr[i][0] = t.substring(0, i);
			 }
			 
			 for(int i=1;i<=tn;i++){
				 for(int j=1;j<=s1n;j++){
					 if((s1.charAt(j-1) == t.charAt(i-1)) ){
						 dp[i][j] = dp[i-1][j-1];
						 addedStr[i][j] = addedStr[i-1][j-1];
						 /*if(!isMatched[j-1]){
							 
							 addedStr[i][j] = addedStr[i-1][j-1];
							 isMatched[j-1] = true;
						 }else{
							 addedStr[i][j] = addedStr[i-1][j] + t.charAt(i-1); 
						 }*/
						 //isMatched[j-1] = true;
						 
					 }else{
						 int[] rc = new int[3];
						 
						 rc[0] = dp[i-1][j]; //int addTChar
						 rc[1] = dp[i-1][j-1]; //int replaceS1WithT
						 rc[2] = dp[i][j-1]; //int droppingS1Char
						 
						 int tmp1 = rc[0]<rc[1]?0:1;
						 int minIdx = rc[tmp1] <= rc[2]?tmp1:2;
						 
						 switch(minIdx){
						 case 0:
							 System.out.println("Adding "+t.charAt(i-1));
							 addedStr[i][j] = addedStr[i-1][j] + t.charAt(i-1);
							 break;
						 case 1:
							 System.out.println("Replacing "+s1.charAt(j-1)+" with "+t.charAt(i-1));
							 addedStr[i][j] = addedStr[i-1][j-1] + t.charAt(i-1);
							 break;
						 case 2:
							 System.out.println("Deleting "+s1.charAt(j-1));
							 addedStr[i][j] = addedStr[i][j-1] ;
						 }
						 
						 dp[i][j] = rc[minIdx]+1;
						 
					 }
				 }
			 }
			 System.out.println("string to be added to s1 to get t --> "+addedStr[tn][s1n]);
			 System.out.println("Levenstein distance --> "+dp[tn][s1n]);
			 return s2.equals(addedStr[tn][s1n]) &&  (addedStr[tn][s1n].length() == dp[tn][s1n]) ? 1:0;
		 }
	
		 public ArrayList<ArrayList<Integer>> avgset(ArrayList<Integer> xs) {
			 
			 int N = xs.size();
			 int S = xs.stream().reduce(0, (x,y)->x+y);
			 Integer[] xsArr = xs.toArray(new Integer[0]);
			 return splitToEqualAvgSubset(0, 0, 0,  xsArr, N, (S/N));
			 
			 
			 
		    }
		 private ArrayList<ArrayList<Integer>> splitToEqualAvgSubset(int currSum , int currN ,  int currIdx ,  final Integer[] xsArr, final int N , final int AVG){
			 
			 
			 System.out.println("CurrSum --> "+currSum+" currN --> "+currN+" currIdx --> "+currIdx);
			 if(((currSum != 0) && (currN!=0)) && currSum == AVG*currN){
				 System.out.println("Found it !! "+Arrays.asList(Arrays.copyOfRange(xsArr, 0, currN)));
				 System.out.println("Found it !! 2nd part "+Arrays.asList(Arrays.copyOfRange(xsArr,  currN , N)));
				 List<Integer> leftList = Arrays.asList(Arrays.copyOfRange(xsArr, 0, currN));
				 Collections.sort(leftList);
				 List<Integer> rightList = Arrays.asList(Arrays.copyOfRange(xsArr,  currN , N ));
				 Collections.sort(rightList);
				 return new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(leftList),new ArrayList<Integer>(rightList)));
			 }
			 if(currIdx == N){
				 return new ArrayList<ArrayList<Integer>>();
			 }
			 ArrayList<ArrayList<Integer>> res1 = splitToEqualAvgSubset( currSum ,  currN ,  currIdx+1 ,  xsArr,  N , AVG);
			 if(res1.size() == 0){
				 /*if(currIdx > 0 && currN > 0)
				  swapArray(resArr, currIdx-1, currN-1);*/
				 int newSum = currSum+xsArr[currIdx];
				 //resArr[currN] = xsArr[currIdx];
				// System.out.println(" resArr --> "+Arrays.asList());
				 swapArray(xsArr, currIdx, currN);
				 ArrayList<ArrayList<Integer>> tmpRes = splitToEqualAvgSubset( newSum ,  currN+1 ,   currIdx+1 ,  xsArr,  N , AVG) ;;
				 if(tmpRes.size() == 0){
					 swapArray(xsArr, currIdx, currN);
				 }
				 return  tmpRes;
			 }else{
				 return res1;
			 }
			 
		 }
		 
		 private static <T> void  swapArray(T[] xs , int i,int j){
			 T tmp = xs[i];
			 xs[i] = xs[j];
			 xs[j] = tmp;
		 }
		 
		 // S[n,k] = min
		 public int paint(int numPainters, int painterRate, ArrayList<Integer> board) {
			 long[][] dp = new long[board.size()][numPainters];
			 
			 int[] boardSum = new int[board.size()];
			 
			 for(int i=0,acc=0;i<=board.size()-1;i++){
				 acc += board.get(i);
				 boardSum[i] = acc;
				 
				 
			 }
			 
			 for(int i=0;i<numPainters;i++){
				dp[0][i] = board.get(0);
			 }
			 
			 for(int i=1;i<board.size();i++){
				 dp[i][0]=boardSum[i];
			 }
			 
			 for(int i=1;i<board.size();i++){
				 for(int j=1;j<numPainters;j++){
					 long minCost = Long.MAX_VALUE;
					 for(int k=0;k<=i;k++){
						 /* if(dp[k][j-1] < minCost){
							 minCost = dp[k][j-1];
						 }*/
						 
						 minCost = Math.min(minCost, Math.max(dp[k][j-1],boardSum[i] - boardSum[k]));
					 }
					 dp[i][j] = minCost;
				 }
				 
			 
			 }
			 
			 return  new Long(((dp[board.size()-1][numPainters-1]% 10000003L)*(painterRate% 10000003L))% 10000003L).intValue();
		    }
		 
		 //using binary search
		 public int paint1(int numPainters, int painterRate, ArrayList<Integer> board) {
		    long  hiSum = 0,  loSum = Integer.MIN_VALUE;
		    
		    for(Integer cell:board){
		    	hiSum += cell;
		    	loSum = Math.max(loSum,cell);
		    }

		     while(loSum < hiSum){
		    	  int midSum = (int)(hiSum+loSum)/2;
		    	  double mid = requiredNumOfPartitions(board,midSum);
		    	  
		    	 // System.out.println(" midsum "+midSum+" losSum "+loSum+" hiSum "+hiSum+" mid "+mid);
		    	 
		    	 if(mid <= numPainters){
		    		 hiSum = midSum;
		    	 }else{
		    		 loSum=midSum+1L;
		    	 }
		    	 
		     }
		     
		     return new Long(((loSum% 10000003L))*((painterRate*1L)% 10000003L)% 10000003L).intValue();
		   
		 }
		 
		 private int requiredNumOfPartitions(List<Integer> xs,int maxPartitionSize){
			 int k=1,acc=0;
			 
			 for(Integer x:xs){
				 acc += x;
				 if(acc > maxPartitionSize){
					 acc = x;
					 k++;
				 }
			 }
			 return k;
			 
		 }
		 
		//dungeon and knight
			//WRONG
			 public int calculateMinimumHP(ArrayList<ArrayList<Integer>> grid) {
				 int m =grid.size(), n = grid.get(0).size();
				 int[][][] dp = new int[m+1][n+1][2];
				 
				 dp[0][0][0] = 0;
				 dp[0][0][1] = 0;
				 dp[0][1][0] = 1; // start with life
				 dp[0][1][1] = 0; // balance  life
				 dp[1][0][0] = 1;
				 dp[1][0][1] = 0;
				 for(int i=2;i<dp[0].length;i++){
					 dp[0][i][0] = Integer.MAX_VALUE;
					 dp[0][i][1] = 0;
				 }
				 for(int i=2;i<dp.length;i++){
					 dp[i][0][0] = Integer.MAX_VALUE;
					 dp[i][0][1] = 0;
				 }
				 
				 for(int i=1;i<dp.length;i++){
					 for(int j=1;j<dp[0].length;j++){
						 
						 if(grid.get(i-1).get(j-1) < 0){
							 int[] fromCell = dp[i-1][j][0]< dp[i][j-1][0]?dp[i-1][j]:dp[i][j-1];
							 
							 dp[i][j][0] = fromCell[0] - grid.get(i-1).get(j-1) - fromCell[1];
							 dp[i][j][1] = fromCell[1]+ grid.get(i-1).get(j-1)>0?fromCell[1]+ grid.get(i-1).get(j-1):0;
						 }else{
							 int[] fromCell = dp[i-1][j][0]< dp[i][j-1][0]?dp[i-1][j]:dp[i][j-1];
							 dp[i][j][0] = fromCell[0];
							 dp[i][j][1] = fromCell[1]+grid.get(i-1).get(j-1);
							 
						 }
					 }
				 }
				 return dp[m][n][0];
				 
			 }
		 
		 
		 
	
	//tests
	
	@Test
	public void testLongestAP(){
		assertEquals(3,longestArithmaticProgression(Arrays.asList(9, 4, 7, 2, 10)));
		assertEquals(4,longestArithmaticProgression(Arrays.asList(3, 6, 9, 12)));
		assertEquals(3,longestArithmaticProgression(Arrays.asList(8, 5, 2)));
		assertEquals(5,longestArithmaticProgression(Arrays.asList(8, 8,8,8,8)));
	}
	
	@Test
	public void testLevensteinDistance(){
		assertEquals(4,minDistance("saturday", "sundays"));
		assertEquals(1,minDistance("sun", "son"));
		assertEquals(2,minDistance("sun", "sons"));
	}
	
	@Test
	public void testThreeSumClosest() {
		assertEquals(2,threeSumClosest(new ArrayList<Integer>(Arrays.asList(-1,2,1,-4)), 1));
		assertEquals(-1,threeSumClosest(new ArrayList<Integer>(Arrays.asList( -5, 1, 4, -7, 10, -7, 0, 7, 3, 0, -2, -5, -3, -6, 4, -7, -8, 0, 4, 9, 4, 1, -8, -6, -6, 0, -9, 5, 3, -9, -5, -9, 6, 3, 8, -10, 1, -2, 2, 1, -9, 2, -3, 9, 9, -10, 0, -9, -2, 7, 0, -4, -3, 1, 6, -3 )), -1));
	}
	
	
	@Test
	public void testMaxProduct() {
		assertEquals(6,maxProduct(Arrays.asList(2, 3, -2, 4)));
		assertEquals(480,maxProduct(Arrays.asList(-2,1,-3,4,1,2,1,-5,4)));
		assertEquals(480,maxProduct(Arrays.asList(-2,1,-3,4,1,2,1,-5,4)));
		assertEquals(9,maxProduct(Arrays.asList(0, 0, 0, -3, -2, 0, 1, 0, 0, 0, 0, 0, -2, 0, 0, 0, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -3, 0, 0, 0, 0, -1, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, -2, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, 0, 0, 0, 0, 0, 0, 0, -1, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 )));
    }
	
	@Test
	public void testMaxProfitFromMultipleStockSell(){
		assertEquals(2,maxProfit(Arrays.asList(1,2,3)));
		assertEquals(4,maxProfit(Arrays.asList(1,2,3,4,5)));
		assertEquals(0,maxProfit(Arrays.asList(7,6,4,3,1)));
		assertEquals(7,maxProfit(Arrays.asList(7,1,5,3,6,4)));
		assertEquals(1270293755,maxProfit(Arrays.asList(5607815, 3316671, 8567241, 1953452, 5821723, 2107782, 3199656, 6500702, 1660943, 6106567, 4568376, 8233467, 1638185, 377730, 5259135, 6478497, 6263692, 6552746, 3362077, 5708016, 4539867, 3711341, 5627255, 277600, 6143721, 6259753, 644473, 1507454, 5325058, 6165165, 763455, 9984969, 6976876, 3892567, 5070435, 3819938, 6924694, 933602, 7834567, 8291211, 6739477, 9545438, 3206097, 2323842, 7803322, 1174139, 3607685, 191039, 9269119, 3552617, 1102590, 4356323, 3522620, 1903537, 6280411, 2766528, 8424337, 798933, 4870234, 4125373, 6170402, 3232242, 4406431, 9635073, 5723402, 2327910, 8163383, 5725707, 5758243, 454498, 3448372, 6540483, 7792016, 8569648, 8405688, 7659716, 443181, 1904980, 8752774, 9782165, 3263420, 2362405, 1272399, 1975615, 4785564, 3483341, 8506282, 7863969, 5004535, 9952854, 6918848, 8383187, 9577668, 6675789, 1811729, 4958207, 2478774, 578257, 9573520, 4529624, 9746119, 5062452, 1196924, 3295753, 4622543, 3897334, 2580607, 3386517, 4009710, 5881831, 2870213, 3280031, 8424727, 4443991, 3244444, 9964755, 4594031, 7839146, 5373680, 2667045, 6297282, 6913168, 5055678, 2146177, 8295695, 1359236, 7583380, 2719127, 5199640, 5601038, 1312220, 4304211, 6553177, 8199378, 7163949, 2023984, 9975949, 9232517, 7609041, 5985920, 2054590, 454561, 5482916, 2660135, 8527838, 5410594, 8451992, 7345999, 2234795, 8673975, 3633757, 9745518, 9387763, 560237, 9080534, 389344, 1167825, 4136688, 7053800, 2653286, 1612350, 324971, 5303542, 7695126, 6462474, 1477306, 8658282, 5516828, 3331274, 7235006, 3043695, 464183, 791128, 5146645, 1268058, 9047045, 5906692, 2852023, 2808918, 424668, 3783527, 870647, 9842209, 8769941, 3230988, 4080059, 808752, 2986269, 8129732, 926836, 9900762, 5331852, 2426541, 9206044, 2499321, 7366685, 972680, 106298, 5150104, 9403466, 5873953, 7739451, 6417370, 3236401, 6093656, 5707253, 5454452, 6107483, 9374599, 9312912, 6579208, 4665036, 1630996, 4482086, 1223359, 5925059, 6524074, 6518129, 6895018, 2857112, 1636513, 1239114, 8312802, 1663398, 572307, 4703625, 5211559, 1380893, 1456101, 6065819, 9600368, 9441022, 2123889, 2184671, 7106036, 3965869, 4920784, 8015842, 4114180, 3599133, 5923431, 1808461, 1217301, 1290812, 5807580, 4731508, 4940020, 664661, 6448821, 6492504, 5503474, 9363166, 2560913, 1978557, 5269068, 6743412, 4613052, 6518355, 8765820, 8481497, 7208085, 1178264, 6798102, 4020672, 4951009, 3724074, 9127609, 983019, 1854053, 9501770, 2899978, 2171135, 4173484, 9873035, 1638504, 2651438, 5832207, 9705774, 7315991, 9817970, 8629270, 613202, 6918296, 1776124, 6962356, 6975889, 895334, 4811405, 850100, 8719686, 1806236, 6467911, 1990911, 5751179, 9353956, 4803564, 1110447, 7415615, 7697424, 9364223, 2609202, 9980425, 6504662, 438730, 7096, 864543, 5588596, 8553444, 5555148, 2654639, 6310408, 9534846, 5696310, 2928890, 1980839, 9822702, 7803880, 2356800, 3361458, 8987980, 6172620, 7747543, 4169127, 2728565, 3050660, 3670161, 1022716, 434886, 3411803, 6259457, 2317992, 4853792, 8705581, 292988, 7470821, 6403835, 9553616, 4337225, 2659007, 730082, 2559847, 3945856, 2153601, 5237701, 5764748, 9660799, 4539969, 170685, 212370, 8282356, 5506991, 3372242, 4863239, 4632840, 7486372, 3404761, 2030008, 7126876, 4131493, 8734738, 8314838, 1548351, 6480805, 4249811, 1571259, 9205448, 2427521, 832867, 3566223, 5745791, 2597785, 6431230, 6359451, 9767646, 574420, 4342246, 614000, 7690489, 6840508, 6111863, 644347, 1231520, 742068, 7986519, 9827536, 5543467, 7369407, 5057635, 7923319, 8576725, 6900488, 1200118, 568580, 7373741, 2558364, 174833, 7613505, 8434181, 1464602, 7812576, 27102, 4791057, 9868005, 2826819, 5442788, 5380453, 3597562, 3908561, 5398211, 5315627, 3207827, 8637328, 8597955, 2497694, 2250161, 4640236, 9777914, 1460764, 4614905, 3622121, 2933308, 212147, 6732249, 6934918, 4106945, 7820238, 1263668, 7934723, 10851, 4748252, 1868471, 6779158, 5039908, 2354783, 5305700, 907512, 8473936, 2892992, 7163961, 5847552, 7663992, 3022118, 5388845, 129743, 6759444, 3761572, 2886094, 3776463, 2262452, 9202035, 4709130, 4142810, 9528526, 8284933, 3601048, 7218568, 4007635, 6851267, 1094400, 1157543, 8896396, 3630488, 3913569, 7431417, 7940810, 2431491, 9398399, 3917452, 378696, 6595832, 2849208, 6774198, 2892564, 8601365, 3965601, 1361602, 3610413, 9097647, 6822748, 768909, 3940412, 7807849, 3962640, 5457629, 68084, 3808344, 8086366, 6199981, 630330, 7017026, 6581528, 7991368, 7260849, 2488089, 9627795, 7054955, 3937726, 6101693, 2209522, 9073455, 5212540, 6002591, 2213056, 9506702, 95580, 8278868, 1961097, 8405656, 5790083, 4815501, 4049881, 3730381, 9401831, 9315385, 4729693, 3244170, 4530984, 8447594, 3158546, 4527686, 1005095, 2909568, 5099776, 7310182, 2634443, 3935595, 1446518, 2008850, 9148499, 4008794, 4016283, 3800271, 4121992, 6822984, 8652133, 4368449, 3678178, 5408316, 2952491, 3449449, 5531885, 2981026, 8911713, 4979659, 9811554, 9131874, 4948553, 8560318, 8090951, 5279869, 6495665, 2639388, 3872645, 751353, 270170, 5632428, 5262014, 9439606, 8380009, 1683881, 7709117, 1609187, 2775511, 8713611, 9673113, 8266178, 3247266, 7688598, 2419076, 8130726, 4281383, 4098387, 750777, 3554234, 5167562, 3587083, 6456217, 3375243, 8057274, 3426629, 8761015, 2592124, 4583593, 124011, 8193267, 8906919, 404699, 4395250, 2814951, 2858604, 9677450, 4037192, 48661, 7667293, 5855839, 5378896, 9976243, 4591492, 6976922, 6144942, 6781098, 8056420, 7323926, 689153, 8798036, 3109311, 8048219, 8955574, 309664, 3471841, 3654327, 2291916, 8080146, 6792197, 2406223, 9796082, 8676278, 1094297, 5540654, 9673603, 5108186, 3610730, 1871505, 4474810, 8137990, 7226681, 5261676, 7791659, 2778211, 6382581, 232040, 3504602, 646515, 6453725, 98782, 817896, 5748588, 3941055, 1285149, 4337264, 4861734, 8300993, 6749827, 5695724, 3172210, 5424488, 1851985, 6138318, 3192075, 4163764, 1702368, 9721295, 4647749, 1297078, 2575264, 8872304, 3375483, 3576595, 9263545, 5358260, 4602058, 6726911, 724603, 5489608, 4273306, 9447189, 7730837, 7578065, 9442551, 4076682, 5273670, 3144124, 1068838, 8007390, 1349218, 9481145, 8747986, 1299448, 2942009, 6032122, 9074683, 5317375, 7540153, 923821, 7029808, 7081268, 3239598, 1900259, 444249, 6208751, 2176965, 5301232, 4033708, 6569996, 2601589, 4932029, 368930, 8008150, 8278580, 975447, 4769515, 40109, 9065589, 2830638, 4373745, 962734, 359324, 9230525, 7782387, 3143786, 8592633, 1270713, 4652111, 8138868, 9251607, 2015304, 6491330, 701924, 4385305, 9020963, 8443216, 2843285, 2597757, 4567213, 3431958, 7318803, 406638, 1410336, 9772604, 6193593, 3886822, 2615443, 4811289, 3258804, 3922039, 259239, 5033857, 1220404, 772527, 8277795, 6296312, 1260979, 2415601 )));
	}
	
	
	@Test
	public void testNSumClosest(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(0,2)), solve(11,new ArrayList<Integer>(Arrays.asList(6,8,5,4,7))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(2,4)), solve(10,new ArrayList<Integer>(Arrays.asList(8, 8, 6, 5, 4))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(3,4,4)), solve(13,new ArrayList<Integer>(Arrays.asList(8, 7, 6, 5, 4))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(34 ,34, 34, 34, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43)), solve(1842,new ArrayList<Integer>(Arrays.asList(22692, 19309, 10377, 2225, 7455, 11158, 12946, 8630, 13168, 14312, 21396, 23230, 15051, 21052, 7784, 19786, 19719, 4471, 5395, 879, 4106, 17902, 18426, 17070, 16737, 18833, 19126, 8775, 8867, 20968, 8475, 22902, 15268, 10195, 118, 14066, 21344, 13055, 14039, 9504, 18711, 10426, 7725, 105, 22822, 15501, 19883, 17532 ))));
	}
	
	
	@Test
	public void testLongestIncreaseDecreaseSubsequence(){
		assertEquals(6, longestSubsequenceLength(Arrays.asList(1,11,2,10,4,5,2,1)));
		//assertEquals(9, longestSubsequenceLength(Arrays.asList(1, 3, 5 ,6 ,4 ,8 ,4 ,3 ,2 ,1)));
		assertEquals(3, longestSubsequenceLength(Arrays.asList(1, 1, 2, 1, 1)));
		assertEquals(8, longestSubsequenceLength(Arrays.asList(9, 6, 1, 10, 2, 5, 12, 30, 31, 20, 22, 18)));
	}
	
	@Test
	public void testInterLeavedString(){
		assertEquals(1,isInterleave("gtaa", "atc", "gattaca"));
		assertEquals(1,isInterleave("aabcc", "dbbca", "aadbbcbcac"));
		assertEquals(0,isInterleave("aabcc", "dbbca", "aadbbbaccc"));
		assertEquals(0,isInterleave("def", "", "de"));
		assertEquals(1,isInterleave("aabc", "abad", "aabadabc"));
		
		assertEquals(1,isInterleave("LgR8D8k7t8KIprKDTQ7aoo7ed6mhKQwWlFxXpyjPkh", "Q7wQk8rqjaH971SqSQJAMgqYyEToZKmlF4ybf", "Q7wLgR8D8Qkk7t88KIrpqjarHKD971SqSQJTQ7aoAMgoq7eYd6yETmhoKZKmlQwWFlF4xybXfpyjPkh"));
		assertEquals(1,isInterleave("LgR8D8k7t8KIprKDTQ7aoo7ed6mhKQwWlFxXpyjPkh", "Q7wQk8rqjaH971SqSQJAMgqYyETo4KmlF4ybf", "Q7wLgR8D8Qkk7t88KIrpqjarHKD971SqSQJTQ7aoAMgoq7eYd6yETmhoK4KmlQwWFlF4xybXfpyjPkh"));
	}
	
	@Test
	public void testAvgSet(){
		assertEquals(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(9,15)) , new ArrayList<Integer>(Arrays.asList(1,7,11,29)) ))
				,avgset(new ArrayList<Integer>(Arrays.asList(1,7,15,29,11,9))));
		assertEquals(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(2 ,4, 32, 47)) , new ArrayList<Integer>(Arrays.asList(1,7,11,29)) ))
				,avgset(new ArrayList<Integer>(Arrays.asList(47, 14, 30, 19, 30, 4, 32, 32, 15, 2, 6, 24))));
		assertEquals(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1,5)) , new ArrayList<Integer>(Arrays.asList(0,2,7)) ))
				,avgset(new ArrayList<Integer>(Arrays.asList( 1, 5, 7, 2, 0))));
	}

	
	@Test
	public void testLinearPartionProblem(){
		assertEquals(50,paint1(2, 5, new ArrayList<Integer>(Arrays.asList(1,10))));
		assertEquals(1700,paint1(3, 1, new ArrayList<Integer>(Arrays.asList(100,200,300,400,500,600,700,800,900))));
		assertEquals(17220,paint1(3, 10, new ArrayList<Integer>(Arrays.asList(640, 435, 647, 352, 8, 90, 960, 329, 859))));
		assertEquals(9400003,paint1(1, 1000000, new ArrayList<Integer>(Arrays.asList(1000000, 1000000))));
		
		
	}
	
	@Test
	public void testPrincessDungeon(){
		assertEquals(7,calculateMinimumHP(new ArrayList<ArrayList<Integer>>(Arrays.asList( new ArrayList<Integer>(Arrays.asList(-2,-3,3)), new ArrayList<Integer>(Arrays.asList(-5,-10,1)), new ArrayList<Integer>(Arrays.asList(10,30,-5)) ))));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println('a' == 'A');
		System.out.println("mul --> "+(1000000L*2000000L));

	}

}
