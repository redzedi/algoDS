package courseraAlgoCourse;

import java.util.ArrayList;
import java.util.List;

public class MyDirectedGraph {
	public final Node[] nodes;
	
	
	public MyDirectedGraph(int numOfNodes){
		nodes = new Node[numOfNodes];
	}
	
	public void addEdge(int fromNode, int toNode){
		Node frm = nodes[fromNode];
		if(frm == null){
			nodes[fromNode] = new Node(fromNode);
			frm = nodes[fromNode];
		}
		frm.outgoingEdges.add(toNode);
		
		Node to = nodes[toNode];
		if(to == null){
			nodes[toNode] = new Node(toNode);
			to = nodes[toNode];
		}
		to.incomingEdges.add(fromNode);
	}
	
	private int commonFinishTime = 1;
	
	public int runDFS(int nodeNum){
		nodes[nodeNum].isVisited = true;
		int a = 0;
		for(Integer chld:nodes[nodeNum].outgoingEdges){
			if(!nodes[chld].isVisited)
				a += runDFS(chld);
		}
		//mark finish time
		//nodes[nodeNum].finishTime = commonFinishTime++;
		return a+1;
	}
	
	public int runReverseDFS(int nodeNum){
		nodes[nodeNum].isVisited = true;
		int a = 0;
		for(Integer chld:nodes[nodeNum].incomingEdges){
			if(!nodes[chld].isVisited)
			  a += runReverseDFS(chld);
		}
		//mark finish time
		nodes[nodeNum].finishTime = commonFinishTime++;
		return a+1;
	}
	
	public void markAllNodesAsNotVisited(){
		commonFinishTime = 1;
		for(int i=1;i<nodes.length;i++){
			nodes[i].isVisited = false;
		}
	}
	
	public static class Node{
		public final Integer nodeNum;
		public final List<Integer> outgoingEdges = new ArrayList<Integer>();
		public final List<Integer> incomingEdges = new ArrayList<Integer>();
		
		public boolean isVisited;
		public int finishTime;

		public Node(Integer nodeNum) {
			super();
			this.nodeNum = nodeNum;
		}
	}
	

}
