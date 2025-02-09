package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FindKClosestNeighbours {
	
	     public List<Integer> findClosestElements(int[] arr, int k, int x) {
			 
			 int lo =0 , hi=arr.length-1, midx=-1 , lx = -1, hx=-1;
			 ArrayList<Integer> res = new ArrayList<>();
			 
			 while(lo<=hi) {
				 int mid = (lo+hi)/2;
				 if(arr[mid] == x) {
					 midx = mid;
					 break;
				 }else if(arr[mid] > x) {
					 hi = mid-1;
				 }else {
					 lo = mid+1;
				 }
			 }
			 
			 if(hi==-1 ) {
				 lo=0;
				 hi=0;
			 }else if(lo>=arr.length) {
				 lo=arr.length;
				 hi=arr.length;
			 }
			 
			 if(midx != -1) {
				 lx=midx;
				 hx = midx;
			 }else if(hi <0){
				 lx=0;
				 hx = k-1<arr.length-1?k-1:arr.length-1;
			 }else if(lo>=arr.length){
				 hx = arr.length-1;
				 lx = arr.length-1-(k-1)<0?0:arr.length-1-(k-1); 
			 }
			 else {
				 if(isCloser(hi,lo,x,arr)) {
					 lx=hi;
					 hx=hi;
				 }else {
					 lx=lo;
					 hx=lo;
				 }
			 }
			 while(hx-lx<k-1) {
				 
				 if(lx == 0) {
					 hx=k-1>=arr.length?arr.length:k-1;
					 break;
				 }else if(hx == arr.length-1) {
					 lx = arr.length-1-(k-1)<0?0:arr.length-1-(k-1);
					 break;
				 }
				 
				 int lxx = lx>0?lx-1:lx;
				 int hxx = hx<arr.length-1?hx+1:hx;
				 
//				 if(lxx == 0 && hxx==arr.length-1) {
//					 lx=0;
//					 hx=arr.length-1;
//					 break;
//				 }else if(lxx==0 ) {
//					 lx = 0;
//					 hx = hxx;
//				 }else if(hxx == arr.length-1) {
//					 lx = lxx;
//					 hx=arr.length-1;
//				 }else 
				 if(isCloser(lxx,hxx,x,arr) ) {
					 lx = lxx;
					// hx = arr.length-1;
				 }else {
					 hx = hxx;
				 }
				 
			 }
			 
			 for(int i=lx;i<=hx;i++) {
				 res.add(arr[i]);
			 }
			 
			 
			// System.out.println(">>>>> ***lx --> "+lx+" ***hx --> "+hx);
			 
		       return res;       
		    }
		 
		 
		 private boolean isCloser(int a1, int a2 , int v , int[] arr ) {
			 int a1Diff = Math.abs(v - arr[a1]);
			 int a2Diff = Math.abs(v - arr[a2]);
			 
			  return a1Diff<a2Diff|| (a2Diff==a1Diff&& a1<a2);
		 }
		 
		 
		 @Test
		 public void testFindClosestElements() {
			 assertEquals( Arrays.asList(1,2,3,4) , findClosestElements(new int[] {1,2,3,4,5}, 4, 3) );
			 assertEquals( Arrays.asList(1,1,2,3) , findClosestElements(new int[] {1,1,2,3,4,5}, 4, -1) );
		 }

}
