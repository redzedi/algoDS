package leetcode;

import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;

import org.junit.Test;

public class CloneGraph {
	
	
	
	private static class NodePair{
		public final Node nd1;
		public final Node nd2;
		
		public NodePair(Node nd1, Node nd2) {
			super();
			this.nd1 = nd1;
			this.nd2 = nd2;
		}
		
		
		
	}

	/**
	 * https://leetcode.com/problems/clone-graph/
	 * 
	 * 
	 * @param node
	 * @return
	 */
	public Node cloneGraph(Node node) {
		
		
		Queue<NodePair> origNodes = new ArrayDeque<>();
		origNodes.add(new NodePair(null, node));
		
		Node res = null;
		NodePair oNodeP= null;
		while(( oNodeP= origNodes.poll()) != null) {
			
			Node cNode = new Node(oNodeP.nd2.val);
			
			//add dependency to the parent clone Node
			if(oNodeP.nd1 != null) {
				oNodeP.nd1.neighbors.add(cNode);
			}else {
				res = cNode;
			}
			
			//
			oNodeP.nd2.neighbors
			.forEach(on-> {
				if(on.val != cNode.val) {
					origNodes.add( new NodePair(cNode, on));
				}
			});
			
		}
		
		return res;
	}
	
	@Test
	public void testCloneGraph() {
		
		Node orig = Node.createGraph(Arrays.asList(Arrays.asList(2,4),Arrays.asList(1,3),Arrays.asList(2,4),Arrays.asList(1,3)));

		System.out.println(orig);
		
		Node cloned = cloneGraph(orig);
		
		System.out.println(cloned);
		
		assertTrue(true);
		
	}
	

}
