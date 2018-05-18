package courseraAlgoCourse;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;



public class QuickSortAlgo {
	
	static class QSortParam{
		public final int[] arr;
		public final int start;
		public final int end;
		public QSortParam(int[] arr, int start, int end) {
			super();
			this.arr = arr;
			this.start = start;
			this.end = end;
		}
		
		
	}
	static class SortTask extends RecursiveAction{
		
		private final int[] arr;
		private final int start;
		private final int end;
		
		

		public SortTask(int[] arr, int start, int end) {
			super();
			this.arr = arr;
			this.start = start;
			this.end = end;
		}



		@Override
		protected void compute() {

			if(!(start < end))
				return;//base case
			//partition identifies and places a pivot element in its rightful place
			//the first part of the array (left of pivot) is less than pivot but unsorted
			//the last part of the array (right of pivot) is greater than pivot but unsorted
			int newPivotIndx = partition1(arr, start, end);
			SortTask s1 = new SortTask(arr, start,newPivotIndx-1); //sort the first part
			SortTask s2  = new SortTask(arr, newPivotIndx+1,end); //sort the last part
			//ForkJoinTask.invokeAll(s1,s2);
			if(newPivotIndx <= (start+end)/2 ){
				s2.fork();
				s1.compute();
			}else{
				s1.fork();
				s2.compute();	
				
			}
			
		
			
		}
		
	}
	
	public static void sort(int[] arr){
		sort(arr, 0, arr.length-1);
	}
	
	public static void sort(int[] arr, int start, int end){
		if(!(start < end))
			return;//base case
		//partition identifies and places a pivot element in its rightful place
		//the first part of the array (left of pivot) is less than pivot but unsorted
		//the last part of the array (right of pivot) is greater than pivot but unsorted
		int newPivotIndx = partition1(arr, start, end);
		sort(arr, start,newPivotIndx-1); //sort the first part
		sort(arr, newPivotIndx+1,end); //sort the last part
	}
	private static Random rnd = new Random();
	public static int choosePivot(int[] arr,int start,int end){
		//return (start+end)/2;
		//return start+rnd.nextInt(end-start);
		//return start;
		int medianElemIndx = start + (((end-start)/2));
		int lesserOfFirst2Indx = arr[start] < arr[medianElemIndx] ? start:medianElemIndx;
		int greaterOfFirst2Indx = arr[start] > arr[medianElemIndx] ? start:medianElemIndx;
		return arr[end] < arr[lesserOfFirst2Indx] ? lesserOfFirst2Indx :(arr[greaterOfFirst2Indx]>arr[end]?end:greaterOfFirst2Indx) ;
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
	
	public static int numOfComparisons = 0;
	//canonical form - as described in the video lecture
	private static int partition1(int[] arr,  int start, int end){
		int newPivotIndx = choosePivot(arr, start, end);
		int p = arr[newPivotIndx];
		if(newPivotIndx != start)
			swap(arr,newPivotIndx,start);
		
		numOfComparisons += (end-start); 
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
	
	public static void main(String[] args) throws Exception {
		ForkJoinPool pool = new ForkJoinPool();
		int[] arrTobeSorted = new int[]{5,9,2,6,10,3,7,1};
		System.out.println(" pivot  "+choosePivot(arrTobeSorted, 1, 4));
		//sort(arrTobeSorted);
		pool.invoke(new SortTask(arrTobeSorted,0,arrTobeSorted.length-1));
		System.out.println("sorted --> "+ Arrays.toString(arrTobeSorted));
		int[] numsForQS =  MergeSortAlgo.readIntegersFromFile("/Users/syadav1/techWork/algoAnalysis/QuickSort.txt");

		long t1 = System.currentTimeMillis();
		pool.invoke(new SortTask(numsForQS, 0, numsForQS.length-1));
		//sort(numsForQS);
		System.out.println("number of comparisons --> "+QuickSortAlgo.numOfComparisons);
		System.out.println("Time taken to sort "+numsForQS.length+" numbers is "+(System.currentTimeMillis() - t1)+" ms");
		int [] xs = new int[]{5,9,2,6,10,3,7,1};
		sort(xs);
		System.out.println("sort serially --> "+Arrays.toString(xs));
	}

}
