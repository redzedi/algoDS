package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;

import org.junit.Test;

public class JumpGame2 {
	
  // https://leetcode.com/problems/jump-game-ii/?envType=company&envId=atlassian&favoriteSlug=atlassian-three-months	
	
	//greedy algorithm
	 public int jump(int[] nums) {
	        // The starting range of the first jump is [0, 0]
	        int answer = 0, n = nums.length;
	        int curEnd = 0, curFar = 0;

	        for (int i = 0; i < n - 1; ++i) {
	            // Update the farthest reachable index of this jump.
	            curFar = Math.max(curFar, i + nums[i]);

	            // If we finish the starting range of this jump,
	            // Move on to the starting range of the next jump.
	            if (i == curEnd) {
	                answer++;
	                curEnd = curFar;
	            }
	        }

	        return answer;
	    }
	
	
	
   public int jump1(int[] nums) {
//        
//	   if(nums[0] == nums.length-1) {
//		   return 1;
//	   }
	   ArrayDeque<Integer[]> currIdxJumpCntFwdJumpOptStk = new ArrayDeque<>();
	   
	   int minJumps = Integer.MAX_VALUE;
	   
	   boolean isDone = false;
	   
	   int j=0;
	   // load first entries
	   
	   for (int i = 1; i <= nums[0]; i++) {
		currIdxJumpCntFwdJumpOptStk.push(new Integer[] {0,0,i});
		
	}
	   while(!isDone && currIdxJumpCntFwdJumpOptStk.peek() != null) {
		   
		   //jump is done here - i am in target node
		   Integer[] currIdxJumpCntFwdJumpOpt = currIdxJumpCntFwdJumpOptStk.pop();
		   
		   Integer tgtNodeIdx = currIdxJumpCntFwdJumpOpt[0]+currIdxJumpCntFwdJumpOpt[2];
		   Integer jumpsToReachTgtNode  = currIdxJumpCntFwdJumpOpt[1]+1;
		   
		   ++j;
		   //System.out.println("At node  "+tgtNodeIdx+" * iteration --> "+ ++j);
		   
		   if(tgtNodeIdx == nums.length-1) {
			   if(jumpsToReachTgtNode < minJumps) {
				   minJumps = jumpsToReachTgtNode;
			   }
			   System.out.println("At node  "+tgtNodeIdx +" * jumpsToReachTgtNode --> "+jumpsToReachTgtNode+" * minJumps --> "+minJumps+" * iterations --> "+j+" * stack size --> "+currIdxJumpCntFwdJumpOptStk.size()+" * total items --> "+nums.length);
		   }else {
			   
			  if(jumpsToReachTgtNode<minJumps-1) {
				  for (int i = 1; tgtNodeIdx<nums.length && i <= nums[tgtNodeIdx]; i++) {
					    if(tgtNodeIdx+i < nums.length)
						currIdxJumpCntFwdJumpOptStk.push(new Integer[] {tgtNodeIdx,jumpsToReachTgtNode,i});
						
					}  
			  }
			   
			   
		   }
		   
		   
		   
	   }
	   
	   return minJumps==Integer.MAX_VALUE?0:minJumps;
	   
	   
    }

   @Test
   public void testMinJumps() {
	   
	   assertEquals(2, jump(new int[] {2,3,1,1,4}));
	   assertEquals(2, jump(new int[] {2,3,0,1,4}));
	   assertEquals(0, jump(new int[] {0}));
	   assertEquals(0, jump(new int[] {1}));
	   assertEquals(5, jump(new int[] {5,6,4,4,6,9,4,4,7,4,4,8,2,6,8,1,5,9,6,5,2,7,9,7,9,6,9,4,1,6,8,8,4,4,2,0,3,8,5}));
	   
	   assertEquals(16, jump(new int[] {5,8,1,8,9,8,7,1,7,5,8,6,5,4,7,3,9,9,0,6,6,3,4,8,0,5,8,9,5,3,7,2,1,8,2,3,8,9,4,7,6,2,5,2,8,2,7,9,3,7,6,9,2,0,8,2,7,8,4,4,1,1,6,4,1,0,7,2,0,3,9,8,7,7,0,6,9,9,7,3,6,3,4,8,6,4,3,3,2,7,8,5,8,6,0}));
   }
   
   
   
}
