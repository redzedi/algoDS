package eipBook;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DutchNationalFlag {
	
	public void quickSort(Integer[] arr, int l , int r){
		
		if(!(l<r))
			return;
		System.out.println("Array to be sorted ** "+Arrays.toString(arr));
		System.out.println("l--> "+l+" r --> "+r);
		int[] partRes = threeWayPartition(arr,l,r);
		quickSort(arr,l , partRes[0]);
		quickSort(arr, partRes[1],r);
		//quickSort(arr, partRes[0],partRes[1]);
		
	}
	
	public int[] threeWayPartition(Integer[] arr, int smaller, int larger ){
		
		int p = arr[smaller], equal=smaller ;
		
		while(equal < larger){
			//p = arr[equal];
			System.out.println("smaller --> "+smaller+" equal --> "+equal+" larger --> "+larger);
			//equal is always >= smaller
			//element at smaller has already been examined (it must be =< p) hence after swap when advancing smaller pointer can advance equal has well
			if(arr[equal] < p){
				swap(arr , equal, smaller);
				smaller++;
				equal++; // the element 
			}else if(arr[equal] == p){
				//do nothing
				equal++;
			}else{
				//arr[equal] > p 
				larger--;
				swap(arr , equal , larger);
			}
		}
		System.out.println("smaller* --> "+smaller+" equal* --> "+equal+" larger* --> "+larger);
		
		return new int[]{smaller, equal};
		
	}
	
	
	private void swap(Integer[] arr, int i , int j){
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	@Test
	public void testThreeWayPartition(){
		//Integer a[] = {4, 9, 4, 4, 1, 9, 4, 4, 9, 4, 4, 1, 4};
		Integer a[] = { 1,4, 4, 4, 9, 4, 9, 4, 9, 4, 4, 1, 4};
		//Integer a[] = { 9,4, 4, 4, 1, 4, 9, 4, 9, 4, 4, 1, 4};
		quickSort(a , 0,a.length);
		
		//threeWayPartition1(a,0);
		
		System.out.println(Arrays.toString(a));
		assertEquals( Arrays.asList(1,  1,  4,  4,  4,  4,  4 , 4 , 4 , 4,  9,  9,  9),Arrays.asList(a));
	}

}
