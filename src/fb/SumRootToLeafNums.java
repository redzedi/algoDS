package fb;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import leetcode.binaryTree.TreeNode;

public class SumRootToLeafNums {
	
	public int sumNumbers(TreeNode root) {
	      int[] stk = new int[10];
	      Arrays.fill(stk,-1);
	      //stk[0] = root.val;
	      res=0;
	      recurse(root, -1 , stk);

	      return res;
	        
	    }
	     private int res;
	    private void recurse(TreeNode nd, int lvl, int[] stk ){

	        if(nd.left == null && nd.right== null){
	            //calculate the number and add to res 
	            int acc = 0;
	            int currLvl = lvl+1;
	            for(int j=0;j<=lvl;j++){
	                acc += stk[j]*Math.pow(10,currLvl-j);
	            }
	            acc += nd.val;
	            res += acc;
	        }else{
	            stk[++lvl] = nd.val;
	            if(nd.left != null){
	                recurse(nd.left , lvl , stk);
	            }

	             if(nd.right != null){
	                recurse(nd.right , lvl , stk);
	            }
	             stk[lvl--]=-1;

	        }

	    }
	    
	    @Test
	    public void testSumNumbers() {
	    	
	    	TreeNode n2 = new TreeNode(2);
	    	TreeNode n3 = new TreeNode(3);
	    	
	    	TreeNode rt = new TreeNode(1, n2,n3);
	    	
	    	assertEquals(25, sumNumbers(rt));
	    }
	    
	    @Test
	    public void testSumNumbers1() {
	    	
	    	TreeNode n5 = new TreeNode(5);
	    	TreeNode n1 = new TreeNode(1);
	    	TreeNode n9 = new TreeNode(9,n5,n1);
	    	
	    	TreeNode n0 = new TreeNode(0);
	    	
	    	TreeNode rt = new TreeNode(4, n9,n0);
	    	
	    	assertEquals(1026, sumNumbers(rt));
	    }

}
