package fb;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MaximumSwap {
	
	//https://leetcode.com/problems/maximum-swap/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	 public int maximumSwap(int num) {
	        
	        ArrayList<Integer> ds = new ArrayList<>();
	       ArrayDeque<Integer[]> stk = new ArrayDeque<>();
	        for(int n1 = num , i=0; n1>0; n1 = n1/10,i++){
	            int curr = n1%10;
	            ds.add(curr);
	            if(stk.isEmpty()){
	                stk.push(new Integer[]{i,i});
	            }else{
	               if(curr > ds.get(stk.peek()[1])){
	                  stk.push(new Integer[]{i,i});
	               }else if(curr < ds.get(stk.peek()[1])){
	                   stk.peek()[0] = i;
	               }
	            }
	        }

	        while(!stk.isEmpty()){
	            Integer[] curr = stk.pop();
	            if(curr[0] != curr[1]){
	                int tmp = ds.get(curr[0]);
	                ds.set(curr[0], ds.get(curr[1]));
	                ds.set(curr[1], tmp);
	                break;
	            }
	        }
	        int res =0;
	        for(int i =0 ; i<ds.size();i++){
	            res += ds.get(i)*Math.pow(10,i);
	        }

	        return res;
	        
	    }

}
