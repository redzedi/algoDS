package courseraAlgoCourse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeSortAlgo {
	
	//merge sort cant be in place
	public static int[] sort(final int[] arr){
		
		if(arr == null || arr.length == 1)
			return arr;
		int[] unsortedLeftArr = new int[arr.length/2];
		int[] unsortedRightArr = new int[arr.length%2==0?arr.length/2:(arr.length+1)/2];
		
		System.arraycopy(arr, 0, unsortedLeftArr, 0, unsortedLeftArr.length);
		System.arraycopy(arr, arr.length/2, unsortedRightArr, 0, unsortedRightArr.length);
//		System.out.println("Unsorted left --> "+Arrays.toString(unsortedLeftArr));
//		System.out.println("Unsorted Right --> "+Arrays.toString(unsortedRightArr));
		int[] leftSortedArr = sort(unsortedRightArr); //
		int[] rightSortedArr = sort(unsortedLeftArr);
		
		return merge(leftSortedArr, rightSortedArr);
	}
	
	private static int[] merge(final int[] leftArr, final int[] rightArr){
		int[] mergedArr = new int[leftArr.length+rightArr.length];
		int left = 0 , right = 0,  mrgIndx = 0;
		while( left < leftArr.length && right < rightArr.length){
			if( leftArr[left] < rightArr[right]){
				mergedArr[mrgIndx++] = leftArr[left++];
			}else{
				mergedArr[mrgIndx++] = rightArr[right++];
			}
		}
		while(left < leftArr.length){
			mergedArr[mrgIndx++] = leftArr[left++];
		}
		while(right < rightArr.length){
			mergedArr[mrgIndx++] = rightArr[right++];
		}
		return mergedArr;
	}
	
  public static long countInversions(final int[] arr){
		
		if(arr == null || arr.length == 1)
			return 0;
		int halfOfArrLength = arr.length/2;
		int[] unsortedLeftArr = new int[arr.length/2];
		int[] unsortedRightArr = new int[arr.length%2==0?arr.length/2:(arr.length+1)/2];
		
		System.arraycopy(arr, 0, unsortedLeftArr, 0, unsortedLeftArr.length);
		System.arraycopy(arr, unsortedLeftArr.length, unsortedRightArr, 0, unsortedRightArr.length);
		
		return countInversions(unsortedLeftArr) + countInversions(unsortedRightArr) + countCrossInversions(unsortedLeftArr, unsortedRightArr);
	}
	
	private static long countCrossInversions(final int[] leftArr, final int[] rightArr){
		int[] sortedLeftArr = sort(leftArr);
		int[] sortedRightArr = sort(rightArr);
		int mergedArrLength = leftArr.length+rightArr.length;
		int numOfInversions = 0;
		//for(int i = 0,left=0, right = 0; i < mergedArrLength && (right < sortedRightArr.length);i++){
		for(int i = 0,left=0, right = 0;  (right < sortedRightArr.length);i++){
			if((left < sortedLeftArr.length && sortedLeftArr[left] < sortedRightArr[right])){
				//mergedArr[mrgIndx++] = leftArr[left++];
				left++;
			}else{
				//debug
				/*for(int k=left;k<leftArr.length;k++){
					System.out.print(" ("+sortedLeftArr[k]+","+sortedRightArr[right]+" )  ");
				}*/
				//debug
				right++;
                
				//actual 1 
				numOfInversions += (leftArr.length - left) ;
				
				//mergedArr[mrgIndx++] = rightArr[right++];
			}
		}
		return numOfInversions;
	}
	
	public static void main(String[] args) throws Exception {
		//FileReader flRdr = null;
		try {
			System.out.println("sorted --> "+ Arrays.toString(MergeSortAlgo.sort(new int[]{10,9,8,7,6,5,4,3,2,1})));
			
			System.out.println("inversions --> "+ MergeSortAlgo.countInversions(new int[]{10,9,8,7,6,5,4,3,2,1}));
			// (5,2) (5,3) (5,1)  (9,2) (9,6) (9,3) (9,7) (9,1) (2,1) (6,3) (6,1) (10,3) (10,7) (10,1) (3,1) (7,1)   --> 16 (num of inversions)
			int[] intNums = readIntegersFromFile("resources/IntegerArray.txt");
			System.out.println("number of Integers in Coursera prob --> "+intNums.length);
			System.out.println("number of Integers in Coursera prob --> "+intNums[intNums.length-1]);
			
			System.out.println("inversions Of Coursera problem --> "+ MergeSortAlgo.countInversions(intNums));
			int maxInts = intNums.length;
			for(int i=0;i<maxInts;intNums[i]=i,i++);
			System.out.println("inversions Of a sorted set  --> "+ MergeSortAlgo.countInversions(intNums));
			System.out.println("Integer max value --> "+Integer.MAX_VALUE);
			
		}finally{
			/*if(flRdr != null)
			flRdr.close();*/
		}
		
	}

	public static int[] readIntegersFromFile(String flName) throws FileNotFoundException,
			IOException {
		FileReader flRdr = null;
		int[] intNums;
		try {
			flRdr = new FileReader(flName);
			char[] cbuf = new char[512];
			StringBuilder fileContents = new StringBuilder();
			while(flRdr.read(cbuf) != -1){
				fileContents.append(cbuf);
				cbuf = new char[512];
				//Arrays.fill(cbuf,'\u0000');
			}
			String flStr = fileContents.toString();
			
			//flStr = flStr.replace("\"", "");
			//System.out.println("String --> "+flStr);
			flStr = flStr.trim();
			String[] nums  = flStr.split("\n");
			System.out.println("number of number strings in Coursera prob --> "+nums.length);
			System.out.println("array --> "+nums[0]+nums[1]+nums[2]+nums[nums.length-1] +" == end");
			intNums = new int[nums.length];

			for(int i=0;i<nums.length;i++){
				intNums[i] = Integer.parseInt(nums[i].trim());
			}
		} finally{
			if(flRdr != null)
				flRdr.close();
		}
		return intNums;
	}

}
