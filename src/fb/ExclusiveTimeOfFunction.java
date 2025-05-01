package fb;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ExclusiveTimeOfFunction {
	
	 public int[] exclusiveTime(int n, List<String> logs) {
	        int[] res = new int[n];
	        Arrays.fill(res,0);
	        ArrayDeque<Integer[]> stk = new ArrayDeque<>();
	        int prevFinishTs =0;
	        for(int i=0;i<logs.size();i++){
	              String[] currLog = logs.get(i).split(":");
	              Integer currId =  Integer.parseInt(currLog[0]);
	              Integer currTs =  Integer.parseInt(currLog[2]);
	              // System.out.println(" log type "+currLog[1]);
	              if(currLog[1].equals("start")){
	                  if(!stk.isEmpty()){
	                     res[stk.peek()[0]] += (currTs- Math.max(stk.peek()[1],prevFinishTs));   
	                  }
	                  stk.push(new Integer[]{currId , currTs});
	              }else{
	                  Integer[] prev = stk.pop();
	                  
	                  res[prev[0]] += (currTs- Math.max(prevFinishTs,prev[1]));   
	                  prevFinishTs = currTs;
	              }
	        }
	        
	        System.out.println(Arrays.toString(res) );
	        return res;
	    }
	 
	 @Test
	 public void testExclusiveTime() {
		 assertArrayEquals(new int[] {3,4}, exclusiveTime(2, Arrays.asList("0:start:0","1:start:2","1:end:5","0:end:6")));
	 }

}
