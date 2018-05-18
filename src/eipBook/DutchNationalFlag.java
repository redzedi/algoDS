package eipBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class DutchNationalFlag {
	
	public void threeWayPartition(int[] arr, int pivotIndex){
		
		int p = arr[pivotIndex], smaller=0 , equal = 0 , larger=arr.length;
		
		while(equal < larger){
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
		
	}
	
	private void swap(int[] arr, int i , int j){
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	@Test
	public void testThreeWayPartition(){
		int a[] = {4, 9, 4, 4, 1, 9, 4, 4, 9, 4, 4, 1, 4};
		threeWayPartition(a , 0);
		System.out.println(Arrays.toString(a));
		assertTrue( Arrays.equals( new int[]{1,  1,  4,  4,  4,  4,  4 , 4 , 4 , 4,  9,  9,  9},a));
	}

}
