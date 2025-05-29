package fb;

public class FindKthPositiveNumber {
	
	public int findKthPositive(int[] arr, int k) {
        int numOfMissingInRange = 0;
        int prev = 0;
        for(int i=1;i<= arr.length;i++){
             int currNumOfMissing = arr[i-1] - prev-1;
             if(k-numOfMissingInRange>currNumOfMissing){
                numOfMissingInRange += currNumOfMissing;
                prev = arr[i-1];
             }else{
                return prev+(k-numOfMissingInRange);
             }
        }

        return   prev+(k-numOfMissingInRange);
        
    }

}
