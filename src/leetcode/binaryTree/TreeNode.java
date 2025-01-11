package leetcode.binaryTree;


 public class TreeNode {
      int val;
        TreeNode left;
      TreeNode right;
   public  TreeNode() { this(0);}
   public TreeNode(int val) { this(0,null,null); }
   public  TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
 }
 