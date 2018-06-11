package eipBook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;


public class TreeProblems {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
			left = null;
			right = null;
		}
	}

	public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode A) {

		java.util.ArrayDeque<Object[]> nodeq = new java.util.ArrayDeque<Object[]>();
		nodeq.addLast(new Object[] { A, 1 });
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		Integer prevLvl = 1;
		java.util.ArrayDeque<Integer> evenLvlHolder = null;
		while (!nodeq.isEmpty()) {
			Object[] currTpl = nodeq.removeFirst();
			TreeNode curr = (TreeNode) currTpl[0];
			Integer currLvl = (Integer) currTpl[1];
			if (curr.left != null)
				nodeq.addLast(new Object[] { curr.left, currLvl + 1 });

			if (curr.right != null)
				nodeq.addLast(new Object[] { curr.right, currLvl + 1 });

			// visit curr
			if (res.size() < currLvl) {
				res.add(new ArrayList<Integer>());
			}
			if (currLvl % 2 != 0) {
				res.get(currLvl - 1).add(curr.val);
			} else {
				if (evenLvlHolder == null) {
					evenLvlHolder = new java.util.ArrayDeque<Integer>();
				}
				evenLvlHolder.addFirst(curr.val);

			}
			if (prevLvl != currLvl && evenLvlHolder != null && prevLvl % 2 == 0) {
				res.get(prevLvl - 1).addAll(evenLvlHolder);
				evenLvlHolder = null;
			}
			prevLvl = currLvl;
		}
		if (evenLvlHolder != null && prevLvl % 2 == 0) {
			res.get(prevLvl - 1).addAll(evenLvlHolder);
			evenLvlHolder = null;
		}
		return res;
	}



	public int maxDepth(TreeNode curr) {

		if (curr == null) {
			return 0;
		}
		int depthOfLChild = maxDepth(curr.left);
		int depthOfRChild = maxDepth(curr.right);

		return (depthOfRChild > depthOfLChild ? depthOfRChild : depthOfLChild) + 1;
	}
	
	public int distanceBetween2Nodes(TreeNode A, int B, int C) {
		ArrayList<Integer> rootPathToB = searchTree(A, B);
		ArrayList<Integer> rootPathToC = searchTree(A, C);
		
		System.out.println("rootPathTo  "+B+" -- > "+rootPathToB);
		System.out.println("rootPathTo  "+C+" -- > "+rootPathToC);
		
		int bIdx= rootPathToB.size()-1;
		int cIdx = rootPathToC.size()-1;
		
		for(;bIdx > -1 && cIdx > -1 && rootPathToB.get(bIdx).equals(rootPathToC.get(cIdx));bIdx--,cIdx--);
		int res = 0;
		for(int i=bIdx; i>-1 ;i--){
			res++;
			if(rootPathToB.get(i).equals(B)){
				break;
			}
		}
		for(int i=cIdx; i>-1;i--){
			res++;
			if( rootPathToC.get(i).equals(C)){
				break;
			}
		}
		return res;
	}
	
	//not using extra memory - searchTree method
	
	class LcaStatus{
		public final int numMatches;
		public final TreeNode lca;
		public LcaStatus(int numMatches, TreeNode lca) {
			super();
			this.numMatches = numMatches;
			this.lca = lca;
		}
		
	}
	public int lca1(TreeNode A, int B, int C) {
		LcaStatus res = lcaHelper(A,B,C);
		return (res!=null && res.numMatches==2)?res.lca.val:-1;
	}
	
	public LcaStatus lcaHelper(TreeNode rt, int k1 , int k2){
		if(rt == null)
			return new LcaStatus(0,null);

		LcaStatus lca_left = lcaHelper(rt.left,k1,k2);
		if(lca_left != null && lca_left.numMatches == 2){
			return lca_left;
		}
		
		LcaStatus lca_right = lcaHelper(rt.right,k1,k2);
		if(lca_right != null && lca_right.numMatches == 2){
			return lca_right;
		}
		int sumMatches = lca_left.numMatches + lca_right.numMatches + ((rt.val == k1)?1:0) + ((rt.val == k2)?1:0);
		return new LcaStatus( sumMatches , (sumMatches == 2)?rt:null );

	}
	
	public int distanceBetween2Nodes1(TreeNode A, int B, int C) {
		
		 LcaStatusWithHeight stat = distHelper(A,B,C,0);
		 
		 if(stat.numMatches < 2){
			 return -1;
		 }else{
			 return stat.d1+stat.d2 - 2*stat.lcaHeight;
		 }
		
	}
	
	public LcaStatusWithHeight distHelper(TreeNode rt, int k1 , int k2, int lvl){
		if(rt == null)
			return new LcaStatusWithHeight(0,null);

		LcaStatusWithHeight lca_left = distHelper(rt.left,k1,k2,lvl+1);
		if(lca_left.numMatches == 2){
			lca_left.lcaHeight += 1;
			lca_left.d1 += 1;
			lca_left.d2 += 1;
			return lca_left;
		}
		
		LcaStatusWithHeight lca_right = distHelper(rt.right,k1,k2, lvl+1);
		if( lca_right.numMatches == 2){
			lca_right.lcaHeight += 1;
			lca_right.d1 += 1;
			lca_right.d2 += 1;
			return lca_right;
		}
		int sumMatches = lca_left.numMatches + lca_right.numMatches + ((rt.val == k1)?1:0) + ((rt.val == k2)?1:0);
		LcaStatusWithHeight res = new LcaStatusWithHeight( sumMatches , (sumMatches == 2)?rt:null );
		
		 if(sumMatches == 2){
			 res.lcaHeight = 1;
		 }
		 
		 res.d1 = (lca_left.d1>0)?lca_left.d1+1:(lca_right.d1>0)?lca_right.d1+1:0;
		 res.d2 = (lca_left.d2>0)?lca_left.d2+1:(lca_right.d2>0)?lca_right.d2+1:0;
		 if(rt.val == k1){
			 res.d1=1;
		 }
		 if(rt.val == k2){
			 res.d2=1;
		 }
		
		return res;

	}
	
	class LcaStatusWithHeight{
		public final int numMatches;
		public final TreeNode lca;
		public int lcaHeight;
		public int d1;
		public int d2;
		public LcaStatusWithHeight(int numMatches, TreeNode lca) {
			super();
			this.numMatches = numMatches;
			this.lca = lca;
		}
		
	}
	
	

	// the general idea here is do a post order traversal of the tree
	// the first node visited after both the given nodes are visited is the LCA
	// of both the node
	public int lca(TreeNode A, int B, int C) {
		ArrayList<Integer> rootPathToB = searchTree(A, B);
		ArrayList<Integer> rootPathToC = searchTree(A, C);
		int res = -1;
		int bIdx = rootPathToB.size() - 1, cIdx = rootPathToC.size() - 1;

		if (bIdx != -1 && cIdx != -1) {
			for (; bIdx > -1 && cIdx > -1
					&& rootPathToB.get(bIdx).equals(rootPathToC.get(cIdx)); bIdx--, cIdx--) {

			}

			res = rootPathToB.get(bIdx + 1);
		}

		return res;

	}

	private ArrayList<Integer> searchTree(TreeNode curr, int v) {
		if (curr == null) {

			return new ArrayList<Integer>();
		} else if (curr.val == v) {
			return new ArrayList<Integer>(Arrays.asList(v));
		} else {
			ArrayList<Integer> resTree = null;
			if (!(resTree = searchTree(curr.left, v)).isEmpty()
					|| !(resTree = searchTree(curr.right, v)).isEmpty()) {
				resTree.add(curr.val);
			}
			return resTree;
		}
	}

	public TreeNode flatten(TreeNode a) {

		flattenAndReturnTailNode(a);
		return a;
	}

	private TreeNode flattenAndReturnTailNode(TreeNode x) {
		if (x == null) {
			return null;
		} else if (x.left == null && x.right == null) {

			return x;
		} else {
			TreeNode leftSubtreeHead = x.left;
			TreeNode rightSubtreeHead = x.right;
			TreeNode flattenedLeftSubtreeTail = null;
			TreeNode flattenedRightSubtreeTail = flattenAndReturnTailNode(rightSubtreeHead);
			x.left = null;

			if (leftSubtreeHead != null) {
				x.right = leftSubtreeHead;
				flattenedLeftSubtreeTail = flattenAndReturnTailNode(leftSubtreeHead);
				if (flattenedLeftSubtreeTail != null)
					flattenedLeftSubtreeTail.right = rightSubtreeHead;

			}

			return flattenedRightSubtreeTail != null ? flattenedRightSubtreeTail
					: flattenedLeftSubtreeTail;
		}
	}

	public TreeNode invertTree(TreeNode A) {
		if (A == null)
			return null;

		TreeNode lTree = A.left;
		TreeNode rTree = A.right;

		A.left = invertTree(rTree);
		A.right = invertTree(lTree);

		return A;

	}

	// Threaded binary tree
	public ArrayList<Integer> recoverTree(TreeNode A) {
		ArrayList<Integer> res = new ArrayList<Integer>();

		TreeNode curr = A;

		while (curr != null) {
			TreeNode nxt = curr.left;
			if (nxt == null) {
				res.add(curr.val);
			}
		}

		return res;
	}

	
	//from inrorder and postorder data
	public TreeNode buildTreeFromInorderAndPostOrder(ArrayList<Integer> A, ArrayList<Integer> B) {
		if(B.isEmpty() || A.isEmpty())
			return null;
		
		int rtNodeVal = B.get(B.size()-1);
		TreeNode rtNode = new TreeNode(rtNodeVal);
		ArrayList<Integer> leftInorderLst = new ArrayList<Integer>(A.subList(0, A.indexOf(rtNodeVal)));
		ArrayList<Integer> leftPostorderLst = new ArrayList<Integer>(B.subList(0, leftInorderLst.size()));
		
		ArrayList<Integer> rightInorderLst = new ArrayList<Integer>(A.subList( A.indexOf(rtNodeVal)+1, A.size()));
		ArrayList<Integer> rightPostorderLst = new ArrayList<Integer>(B.subList(leftInorderLst.size(),B.size()-1));
		
		rtNode.left = buildTreeFromInorderAndPostOrder(leftInorderLst,leftPostorderLst);
		rtNode.right = buildTreeFromInorderAndPostOrder(rightInorderLst,rightPostorderLst);
		
		return rtNode;
    }
	
	 public TreeNode buildTreeFromPreorderAndInOrder(ArrayList<Integer> A, ArrayList<Integer> B) {
			if(B.isEmpty() || A.isEmpty())
				return null;
			
			int rtNodeVal = A.get(0);
			TreeNode rtNode = new TreeNode(rtNodeVal);
			ArrayList<Integer> leftInorderLst = new ArrayList<Integer>(B.subList(0, B.indexOf(rtNodeVal)));
			ArrayList<Integer> leftPreorderLst = new ArrayList<Integer>(A.subList(1, leftInorderLst.size()+1));
			
			ArrayList<Integer> rightInorderLst = new ArrayList<Integer>(B.subList( B.indexOf(rtNodeVal)+1, B.size()));
			ArrayList<Integer> rightPreorderLst = new ArrayList<Integer>(A.subList(leftInorderLst.size()+1,A.size()));
			
			rtNode.left = buildTreeFromPreorderAndInOrder(leftPreorderLst, leftInorderLst);
			rtNode.right = buildTreeFromPreorderAndInOrder(rightPreorderLst, rightInorderLst);
			
			return rtNode;
			
			
	    }
	 
	 public ArrayList<String> prefix(ArrayList<String> strs) {
		 MultiTree root = new MultiTree("");
		 for(String str:strs){
			 MultiTree curr = root;
			 int idx = 0;
			 boolean next = true;
			 while(next){
				 next = false;
				 for(MultiTree chld:curr.children){
					 
					 if(!chld.val.isEmpty() && str.charAt(idx) == chld.val.charAt(0)){
						 int chldIdx = 0;
						 for(;chldIdx<chld.val.length() && str.charAt(idx) == chld.val.charAt(chldIdx);idx++,chldIdx++);
						  String suffixStr = chld.val.substring(chldIdx);
						  if(!suffixStr.isEmpty()){
							  chld.val  = chld.val.substring(0, chldIdx);
								 MultiTree suffixOfExisting = new MultiTree(suffixStr);
								 suffixOfExisting.children.addAll(chld.children);
								 chld.children.clear();
								 chld.children.add(suffixOfExisting);
						  }
						 
							 curr = chld;	 
							 next = true;
						 break;
					 }
				 }	 
			 }
			curr.children.add(new MultiTree(str.substring(idx))); 
		 }
		 
		 //{Node, commonPrefix}
		 java.util.Stack<Object[]> nodesToVisit = new java.util.Stack<Object[]>();
		 nodesToVisit.push(new Object[]{root,root.val});
		 ArrayList<String> prefixes = new ArrayList<String>();
		 HashMap<String,String> prefixMap = new HashMap<String,String>();
		 while(!nodesToVisit.isEmpty()){
			Object[] currTuple = nodesToVisit.pop();
			MultiTree currNode = (MultiTree)currTuple[0];
			String currPrefix = (String)currTuple[1];
			 
			 if(currNode.children.isEmpty()){
				 prefixMap.put(currPrefix + currNode.val, currPrefix + (currNode.val.isEmpty()?"":String.valueOf(currNode.val.charAt(0))));
				 //prefixes.add(currPrefix + currNode.val.substring(0,1));
				 
			 }else{
				 
				 for(MultiTree chld:currNode.children){
					 nodesToVisit.push(new Object[]{chld,currPrefix+currNode.val});
				 }
			 }
		 }
		 
		 for(String s :strs){
			 prefixes.add(prefixMap.get(s));
		 }
		 
		 return prefixes;
		 
	    }
	 
	 
	 //actually a trie
	 private static class MultiTree{
		 public  String val;
		 public final ArrayList<MultiTree> children = new ArrayList<MultiTree>();
		public MultiTree(String val) {
			super();
			this.val = val;
		}
	 }
	 
	 //kth smallest in a BST
	 public int kthsmallest(TreeNode rt, int k) {
         return kthSmallestInorder( rt,  k)[1];
        }
     private Integer[] kthSmallestInorder(TreeNode rt, int k){
         if(rt == null){
             return new Integer[]{0,null};
         }
         Integer[] ltRes =  kthSmallestInorder( rt.left,  k);
         if(ltRes[1] != null){
             return ltRes;
         }
         if(k==0 || ltRes[0].equals( k-1)){
             return new Integer[]{k,rt.val};
         }
        Integer[] rtRes =  kthSmallestInorder( rt.right,  k-((Integer)ltRes[0]+1));
         
         if(rtRes[1] != null){
             return rtRes;
         }else{
             return new Integer[]{(Integer)ltRes[0]+(Integer)rtRes[0]+1,null};
         }
     }

	 
	 
	 //Sort hotel reviews by number of good words it has 
	 public ArrayList<Integer> solve(String goodWords, ArrayList<String> reviews) {
		 MyTrie dict = new MyTrie();
		 String[] goodWordsArr = goodWords.split("_");
		 for(int i=0;i<goodWordsArr.length;i++){
			 dict.insert(goodWordsArr[i]);
		 }
		 int[][] index_goodWord = new int[reviews.size()][2];
		 for(int i=0;i<reviews.size();i++){
			 String[] reviewWords = reviews.get(i).split("_");
			 int goodWordCount = 0;
			 for(int j=0;j<reviewWords.length;j++){
				 if(dict.contains(reviewWords[j])){
					 goodWordCount++;
				 }
			 }
			 index_goodWord[i] = new int[]{i,goodWordCount};
		 }
		 
		 sortStable(index_goodWord,index_goodWord.length-1);
		 ArrayList<Integer> res = new ArrayList<>();
		 for(int i=0;i<index_goodWord.length;i++){
			 res.add(index_goodWord[i][0]);
		 }
		 return res;
	    }
	 
	 private void sortStable(int[][] xs, int n){
		 if(n <= 0){
			 return;
		 }
		 int[] curr = Arrays.copyOf(xs[n], 2) ;
		 sortStable(xs,n-1);
		 int i=n-1;
		 for(;i>=0 && xs[i][1]< curr[1];i--){
			 xs[i+1] = xs[i];
		 }
		 xs[++i]=curr;
	 }
	 
	 private static class MyTrie{
		 
		 private final MyTrieNode root = new MyTrieNode();
		 
		 public void insert(String w){
			 if( w==null || w.isEmpty()){
				 return;
			 }
			 MyTrieNode curr=root;
			 for(int i=0;i<w.length();i++){
				 if(curr.children[w.charAt(i)-97] == null ){
					 curr.children[w.charAt(i)-97] = new MyTrieNode();
				 }
					 curr =  curr.children[w.charAt(i)-97];
				 
				 if(i == w.length()-1){
					 curr.isWord=true;
				 }
			 }
		 }
		 
		 public boolean contains(String w){
			 MyTrieNode curr = root;
			 int i=0;
			 for(; i<w.length() && curr.children[w.charAt(i)-97] != null ;curr=curr.children[w.charAt(i)-97],i++);
			 return curr.isWord && !(i<w.length());
			// return !(i<w.length());
		 }
		 
		 
		 private static class MyTrieNode{
			 final public MyTrieNode[] children = new MyTrieNode[26];
			 public boolean isWord;
		 }
	 }
	// tests

	@Test
	public void testZigzagLevelOrder() {
		TreeNode nd20 = new TreeNode(20);
		nd20.left = new TreeNode(15);
		nd20.right = new TreeNode(7);
		TreeNode rt = new TreeNode(3);
		rt.left = new TreeNode(9);
		rt.right = nd20;
		assertEquals(new ArrayList<ArrayList<Integer>>() {
			{
				addAll(Arrays.asList(new ArrayList<Integer>(Arrays.asList(3)),
						new ArrayList<Integer>(Arrays.asList(20, 9)),
						new ArrayList<Integer>(Arrays.asList(15, 7))));
			}
		}, zigzagLevelOrder(rt));

		TreeNode nd15_1 = new TreeNode(15);
		nd15_1.left = new TreeNode(1);
		nd15_1.right = new TreeNode(2);
		TreeNode nd20_1 = new TreeNode(20);
		nd20_1.left = nd15_1;
		nd20_1.right = new TreeNode(7);
		TreeNode rt_1 = new TreeNode(3);
		rt_1.left = new TreeNode(9);
		rt_1.right = nd20_1;
		assertEquals(new ArrayList<ArrayList<Integer>>() {
			{
				addAll(Arrays.asList(new ArrayList<Integer>(Arrays.asList(3)),
						new ArrayList<Integer>(Arrays.asList(20, 9)),
						new ArrayList<Integer>(Arrays.asList(15, 7)),
						new ArrayList<Integer>(Arrays.asList(2, 1))));
			}
		}, zigzagLevelOrder(rt_1));
	}

	@Test
	public void testMaxDepth() {
		TreeNode nd20 = new TreeNode(20);
		nd20.left = new TreeNode(15);
		nd20.right = new TreeNode(7);
		TreeNode rt = new TreeNode(3);
		rt.left = new TreeNode(9);
		rt.right = nd20;
		assertEquals(3, maxDepth(rt));

		TreeNode nd15_1 = new TreeNode(15);
		nd15_1.left = new TreeNode(1);
		nd15_1.right = new TreeNode(2);
		TreeNode nd20_1 = new TreeNode(20);
		nd20_1.left = nd15_1;
		nd20_1.right = new TreeNode(7);
		TreeNode rt_1 = new TreeNode(3);
		rt_1.left = new TreeNode(9);
		rt_1.right = nd20_1;
		assertEquals(4, maxDepth(rt_1));

		assertEquals(1, maxDepth(new TreeNode(0)));
	}

	@Test
	public void testLCA() {

		TreeNode nd15_1 = new TreeNode(15);
		nd15_1.left = new TreeNode(1);
		// nd15_1.right = new TreeNode(2);
		TreeNode nd20_1 = new TreeNode(20);
		nd20_1.left = nd15_1;
		TreeNode nd7_1 = new TreeNode(7);
		nd7_1.left = new TreeNode(2);
		nd20_1.right = nd7_1;
		TreeNode rt_1 = new TreeNode(3);
		rt_1.left = new TreeNode(9);
		rt_1.right = nd20_1;
		assertEquals(20, lca(rt_1, 1, 2));

		assertEquals(-1, lca(rt_1, 1, 12));
		assertEquals(3, lca(rt_1, 9, 2));
		assertEquals(20, lca(rt_1, 20, 1));

		// assertEquals(1, maxDepth(new TreeNode(0)));
	}
	
	@Test
	public void testLCA1() {
		/*
		 *       3
		 *    9        20
		 *         15      7
		 *       1        2
		 */

		TreeNode nd15_1 = new TreeNode(15);
		nd15_1.left = new TreeNode(1);
		// nd15_1.right = new TreeNode(2);
		TreeNode nd20_1 = new TreeNode(20);
		nd20_1.left = nd15_1;
		TreeNode nd7_1 = new TreeNode(7);
		nd7_1.left = new TreeNode(2);
		nd20_1.right = nd7_1;
		TreeNode rt_1 = new TreeNode(3);
		rt_1.left = new TreeNode(9);
		rt_1.right = nd20_1;
		assertEquals(20, lca(rt_1, 1, 2));

		assertEquals(-1, lca1(rt_1, 1, 12));
		assertEquals(3, lca1(rt_1, 9, 2));
		assertEquals(20, lca1(rt_1, 20, 1));

		// assertEquals(1, maxDepth(new TreeNode(0)));
	}

	@Test
	public void testFlatten() {
		TreeNode nd2 = new TreeNode(2);
		nd2.left = new TreeNode(3);
		nd2.right = new TreeNode(4);

		TreeNode nd5 = new TreeNode(5);
		nd5.right = new TreeNode(6);

		TreeNode rt = new TreeNode(1);
		rt.left = nd2;
		rt.right = nd5;

		assertEquals(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6)),
				listOfRightTreeNodes(flatten(rt)));
	}
	
	@Test
	public void testDistanceBetween2Nodes(){
		TreeNode nd2 = new TreeNode(2);
		nd2.left = new TreeNode(4);
		nd2.right = new TreeNode(5);

		TreeNode nd3 = new TreeNode(3);
		nd3.left = new TreeNode(6);
		nd3.right = new TreeNode(7);

		TreeNode rt = new TreeNode(1);
		rt.left = nd2;
		rt.right = nd3;
		
		assertEquals(2, distanceBetween2Nodes(rt, 4, 5));
		assertEquals(4, distanceBetween2Nodes(rt, 4, 6));
		assertEquals(3, distanceBetween2Nodes(rt, 3,4));
		assertEquals(1, distanceBetween2Nodes(rt, 2,4));
		assertEquals(1, distanceBetween2Nodes(rt, 2,4));
		
	}
	
	
	@Test
	public void testDistanceBetween2Nodes1(){
		TreeNode nd2 = new TreeNode(2);
		nd2.left = new TreeNode(4);
		nd2.right = new TreeNode(5);

		TreeNode nd3 = new TreeNode(3);
		nd3.left = new TreeNode(6);
		nd3.right = new TreeNode(7);

		TreeNode rt = new TreeNode(1);
		rt.left = nd2;
		rt.right = nd3;
		
		assertEquals(2, distanceBetween2Nodes1(rt, 4, 5));
		assertEquals(4, distanceBetween2Nodes1(rt, 4, 6));
		assertEquals(3, distanceBetween2Nodes1(rt, 3,4));
		assertEquals(1, distanceBetween2Nodes1(rt, 2,4));
		assertEquals(1, distanceBetween2Nodes1(rt, 2,4));
		
	}
	
	@Test
	public void testPrefixes(){
		assertEquals(new ArrayList<String>(Arrays.asList("z", "dog", "du", "dov")), prefix(new ArrayList<String>(Arrays.asList("zebra", "dog", "duck", "dove"))));
		assertEquals(new ArrayList<String>(Arrays.asList("f", "km", "ku", "u", "v", "d")), prefix(new ArrayList<String>(Arrays.asList("fwkho", "kmcoqhnw", "kuewhsqmgb", "uqcljj", "vsw", "dkqtbxi" ))));
		assertEquals(new ArrayList<String>(Arrays.asList("abcdefgv" ,"abcdefgr", "abcdefgl", "abcdefgt", "abcdefa", "abcdefgws", "abcdefgh", "abcdefgwp" , "abcdefgcb", "abcdefgce")), prefix(new ArrayList<String>(Arrays.asList("abcdefgv", "abcdefgrr", "abcdefglj", "abcdefgtnsnfwzqfj", "abcdefafadr", "abcdefgwsofsbcnuv", "abcdefghffbsaq", "abcdefgwp", "abcdefgcb", "abcdefgcehch"  ))));
	}
	
	
	@Test
	public void testHoteReviewsTrie(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(2, 0, 1)), solve("cool_ice_wifi",new ArrayList<String>(Arrays.asList("water_is_cool", "cold_ice_drink", "cool_wifi_speed"))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(2,0,1,3)), solve("coolsai",new ArrayList<String>(Arrays.asList("cool","sai","coolsai", "coolsaii"))));

	}
	

	// utils
	
	public static ArrayList<Integer> listOfRightTreeNodes(TreeNode rt) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		TreeNode curr = rt;
		while (curr != null) {
			res.add(curr.val);
			curr = curr.right;
		}

		return res;

	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.asList(1,2).indexOf(2));
		System.out.println(Arrays.asList(1).subList(0,0));
		System.out.println('a'-97);
	}

}
