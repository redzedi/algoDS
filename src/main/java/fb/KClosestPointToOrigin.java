package fb;

import java.util.PriorityQueue;

public class KClosestPointToOrigin {
	
	  public int[][] kClosest(int[][] points, int k) {
	        
		     PriorityQueue<Double[]> polarPoints = new PriorityQueue<>((p1,p2)->(int)(p2[0]-p1[0]));
		     
		     for(int i=0;i<points.length;i++) {
		    	 Double[] currPolar = new Double[]{Math.pow(points[i][0],2.0)+Math.pow(points[i][1],2.0), i*1.0};
		    	 
		    	 if(polarPoints.size() < k ) {
		    		 polarPoints.add(currPolar);
		    	 }else if(polarPoints.peek()[0] > currPolar[0]){
	                polarPoints.poll();
	                 polarPoints.add(currPolar);
	             }
		     }
		     
		     int[][] res = new int[k][2];
		     int j =0;
		     for(Double[] currP:polarPoints) {
		    	 res[j][0] = points[currP[1].intValue()][0];
	             res[j][1] = points[currP[1].intValue()][1];
	             j++;
		     }
		     return res;
		     
	        
	    }
	  
	  public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("1");
		sb.append("2");
		System.out.println(sb.length());
		System.out.println(sb.charAt(sb.length()-1));
		System.out.println(sb.delete(sb.length()-1 , sb.length()));
		System.out.println(sb.substring(sb.length()-1 , sb.length()));
		System.out.println(sb.toString());
	}

}
