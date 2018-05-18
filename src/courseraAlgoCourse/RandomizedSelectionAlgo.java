package courseraAlgoCourse;

import java.util.Arrays;
import java.util.Random;

//exactly same as QuickSorts
//returns the i th smallest element in an array
public class RandomizedSelectionAlgo {
	
	public static int rSelect(int[] arr, int statPos){
		return rSelect(arr, 0, arr.length-1, statPos);
	}
	
	public static int rSelect(int[] arr, int start, int end, int statPos){
		if(!(start<end))
			return arr[start];//base case
		//partition identifies and places a pivot element in its rightful place
		//the first part of the array (left of pivot) is less than pivot but unsorted
		//the last part of the array (right of pivot) is greater than pivot but unsorted
		int newPivotIndx = partition1(arr, start, end);
		
		if(newPivotIndx == statPos){
			
			return arr[newPivotIndx]; //lucky case pivot is at statistics position
			
		}else if(statPos < newPivotIndx){
			return rSelect(arr, start,newPivotIndx-1,statPos); //sort the first part 
		}else{
			//statPos > newPivotIndx
			return rSelect(arr, newPivotIndx+1,end, statPos); //sort the last part
		}
		
		
	}
	private static Random rnd = new Random();
	private static int choosePivot(int[] arr,int start,int end){
		//return (start+end)/2;
		return start+rnd.nextInt(end-start);
	}
	
	//intuitive one - has some redundant swaps
	private static int partition(int[] arr,  int start, int end){
		int newPivotIndx = choosePivot(arr, start, end);
		
		for(int i=start;i<=end;i++){
			if(arr[i]<arr[newPivotIndx] && i > newPivotIndx){
				//smaller element to the right - bring to left of pivot
				//2 swaps 
				//first swap and bring the arr[i] to the immediate right of pivotIndx
				swap(arr, i, newPivotIndx+1);
				swap(arr, newPivotIndx+1, newPivotIndx);
				newPivotIndx++;
			}else if(arr[i]>arr[newPivotIndx] && i < newPivotIndx){
				//bigger element to the left - bring to right of pivot
				//2 swaps 
				//first swap and bring the arr[i] to the immediate left of pivotIndx
				swap(arr, i, newPivotIndx-1);
				swap(arr, newPivotIndx-1, newPivotIndx);
				newPivotIndx--;
			}
			
		}
		System.out.println("pivot Index --> "+newPivotIndx);
		return newPivotIndx;
	}
	
	//canonical form - as described in the video lecture
	private static int partition1(int[] arr,  int start, int end){
		int newPivotIndx = choosePivot(arr, start, end);
		int p = arr[newPivotIndx];
		if(newPivotIndx != start)
			swap(arr,newPivotIndx,start);
		
		//from here on assumption is pivot is placed in the first position
		int i = start+1;
		for(int j=start+1;j<=end;j++){
			if(arr[j]< p  ){
				swap(arr,j,i);
				i++;
			}
		}
		swap(arr,start,i-1);
		//System.out.println("pivot Index --> "+(i-1));
		return i-1;
	}
	
	private static void swap(int[] arr, int from, int to){
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
	
	public static void main(String[] args) {
		int[] arrTobeSorted = new int[]{5,9,2,6,10,3,7,1};
		 //4th largest element from the given array - expect 5
		System.out.println("4th largest element from the arr --> "+ rSelect(arrTobeSorted,3));
		//1st largest element from the given array - expect 1
	   System.out.println("1st largest element from the arr --> "+ rSelect(arrTobeSorted,0));
	 // largest element from the given array - expect 1
	   System.out.println("largest element from the arr --> "+ rSelect(arrTobeSorted,arrTobeSorted.length-1));
	}

}
