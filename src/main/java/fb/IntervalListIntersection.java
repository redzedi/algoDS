package fb;

import java.util.ArrayList;

public class IntervalListIntersection {
	
	//https://leetcode.com/problems/interval-list-intersections/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	 public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
	        int fidx = 0, sidx =0;
	        ArrayList<Integer[]> res = new ArrayList<>();
	        while(fidx < firstList.length && sidx < secondList.length){
	            if( (secondList[sidx][0] <= firstList[fidx][1] && secondList[sidx][1] >= firstList[fidx][0]) || (firstList[fidx][0] <= secondList[sidx][1] && firstList[fidx][1] >= secondList[sidx][0] ) ){
	                res.add( new Integer[]{Math.max(firstList[fidx][0] , secondList[sidx][0]), Math.min(secondList[sidx][1] , firstList[fidx][1])} );
	            }
	            if(firstList[fidx][1] < secondList[sidx][1]){
	                fidx++;
	            }else{
	                sidx++;
	            }
	        }
	     int[][] resArr = new int[res.size()][2];

	     for(int j=0;j<resArr.length;j++){
	        resArr[j] = new int[]{ res.get(j)[0], res.get(j)[1]};
	     }

	        return resArr;
	    }
	 
	 public static void main(String[] args) {
		 int i =4;
		 double d = 4.111;
		 boolean b =i<d;
		 Math.abs(i-d);
		System.out.println(4 == 4.0000000001);
	}

}
