package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import leetcode.binaryTree.TreeNode;

public class NodesAtDistanceKInBinaryTree {
	//https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/editorial/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days

    private record DiscoverRes(int dist,ArrayList<Integer> kDistNodesFromTarget){}

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
       return discoverTarget(target , root,k, 0).kDistNodesFromTarget();
    }

    private DiscoverRes discoverTarget(TreeNode target , TreeNode root , int k , int depth){

        if(root == null){
            return new DiscoverRes(-1, new ArrayList<Integer>());
        }

        if(root.val == target.val){
        	if( k>0) {
        		 ArrayList<Integer> leftRes = getNodesAtDistanceK(root.left,k-1);
                 ArrayList<Integer> rightRes = getNodesAtDistanceK(root.right,k-1);
                 leftRes.addAll(rightRes);
                 System.out.println(root.val+" Found the target "+leftRes);
                 return new DiscoverRes(depth, leftRes);
        	}else {
        		ArrayList<Integer> res = new ArrayList<>();
        		res.add(root.val);
        		return new DiscoverRes(depth, res);
        		
        	}
           
        } else{
        	
           DiscoverRes res = discoverTarget(target , root.left,  k, depth+1);
           boolean isRightTraversed = false;
           if(res.dist == -1){
               isRightTraversed = true;
        	   res = discoverTarget(target , root.right,  k, depth+1);
             
           }
           
           if(res.dist == -1) {
        	   return res;
           }
           
           if(res.dist-depth == k){
        	   
              res.kDistNodesFromTarget().add( root.val);
              return res;
              
           }else if(res.dist-depth > k){
               return res;
           }else{
        	   
        	   
        	   if(!isRightTraversed) {
        		   ArrayList<Integer> rightRes = getNodesAtDistanceK(root.right,k-(res.dist() -depth)-1);
                   res.kDistNodesFromTarget().addAll( rightRes);
        	   }else {
        		   ArrayList<Integer> leftRes = getNodesAtDistanceK(root.left,k-(res.dist() -depth)-1);
                   res.kDistNodesFromTarget().addAll( leftRes);
        	   }
             
               return res;
           }
        }

    }

    private ArrayList<Integer> getNodesAtDistanceK(TreeNode root ,  int depth){
        if(root == null){
            return new ArrayList<Integer>();
        }
        if(depth == 0){
            System.out.println("Found the elem "+root.val);
            ArrayList<Integer> res = new ArrayList<>();
            res.add(root.val);
            return res;
        }else {
            ArrayList<Integer> leftNodes = getNodesAtDistanceK(root.left , depth-1);
            ArrayList<Integer> rightNodes = getNodesAtDistanceK(root.right , depth-1);
            leftNodes.addAll(rightNodes);
            return leftNodes;
        }

    }
    
    @Test
    public void testDistanceK() {
    	TreeNode n7 = new TreeNode(7);
    	TreeNode n4 = new TreeNode(4);
    	TreeNode n6 = new TreeNode(6);
    	TreeNode n2 = new TreeNode(2, n7,n4);
    	TreeNode n5 = new TreeNode(5, n6,n2);
    	
    	TreeNode n0 = new TreeNode(0);
    	TreeNode n8 = new TreeNode(8);
    	TreeNode n1 = new TreeNode(1,n0,n8);
    	TreeNode rt = new TreeNode(3,n5,n1);
    	
    	assertEquals(Arrays.asList(7,4,1) , distanceK(rt, n5, 2));
    	
    	
    }
    
    @Test
    public void testDistanceK1() {
    	
    	TreeNode n3 = new TreeNode(3);
    	TreeNode n2 = new TreeNode(2);
    	TreeNode n1 = new TreeNode(1,n3,n2);
    	
    	TreeNode rt = new TreeNode(0,n1,null);
    	
    	assertEquals(Arrays.asList(1) , distanceK(rt, n2, 1));
    	
    	
    }
    
    @Test
    public void testDistanceK2() {
    	
    	TreeNode n2 = new TreeNode(2);
    	
    	TreeNode n3 = new TreeNode(3);
    	TreeNode n1 = new TreeNode(1,n3,null);
    	
    	TreeNode rt = new TreeNode(0,n2,n1);
    	
    	assertEquals(Arrays.asList(2) , distanceK(rt, n3, 3));
    	
    	
    }
    
    
    @Test
    public void testDistanceK3() {
    	
    	
    	TreeNode n4 = new TreeNode(4);
    	TreeNode n3 = new TreeNode(3,null,n4);
    	TreeNode n2 = new TreeNode(2,null,n3);
 
    	TreeNode n5 = new TreeNode(5);
    	
    	TreeNode n1 = new TreeNode(1,n2,n5);
    	
    	TreeNode rt = new TreeNode(0,null,n1);
    	
    	assertEquals(Arrays.asList(4,5,0) , distanceK(rt, n2,2));
    	
    	
    }
    
    public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		System.out.println(Arrays.stream(new String[] {null,"a",null,"b",null}).filter(Objects::nonNull).collect(Collectors.joining()));
		;
		
	}

}
