package fb;

import static org.junit.Assert.assertArrayEquals;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class DiagonalTraverseOfMatrix {
	
	public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int mn = m*n;
        int[] res = new int[mn];
        int cx=0,cy=0;
        boolean isUp = true;

        for(int i=0;i<mn;i++){
        	
        	System.out.println("cx= "+cx+" , cy= "+cy+" val= "+mat[cx][cy]);
        	
            res[i] = mat[cx][cy];

            if(isUp){
               if( cx>0 && cy<=n-1){
                  cx = cx-1;
                  cy = cy+1;
               }else{
            	   
            	   if(cx == m) {
                   	cy=cy+1;
                   }else if(cy==0 ){
                    cx=cx+1;
                }
               
                 
                 isUp = false;
               }
            }else{
                if( cx<=m-1 && cy>0){
                  cx = cx+1;
                  cy = cy-1;
               }else{
            	   if(cy == n-1) {
            		   cx=cx+1;
            	   }else if(cx == 0) {
            		   cy=cy+1;
            	   }
            	                   isUp = true;
               }

            }
        }

        return res;
        
    }
	
	@Test
	public void testDiagonalTraversal() {
		assertArrayEquals(new int[] {1,2,4,7,5,3,6,8,9}, findDiagonalOrder(new int[][] {new int[] {1,2,3},new int[] {4,5,6},new int[] {7,8,9}} ));
		
		new ArrayList<>(new HashMap<String,List<String>>().values());
		
		
		
		//List.of("abc" , "def").stream().map((s)-> )
	}
	
	public static void main(String[] args) {
		//Arrays.stre
		List<String> v = Arrays.stream( new String[] {"a","b"}).map(i-> new SimpleEntry<>(i,i)).collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.mapping( SimpleEntry::getValue, Collectors.toList() ))).values().stream().collect(Collectors.toList()).get(0);
		System.out.println();  
		//"abc".chars().mapToObj((c)->  )
		System.out.println( Integer.parseInt( IntStream.range(0, "abc".length()-2+1).mapToObj(i-> String.valueOf( Math.abs("abc".charAt(i)-"abc".charAt(i+1)))).collect(Collectors.joining())));  
		
	}

}
