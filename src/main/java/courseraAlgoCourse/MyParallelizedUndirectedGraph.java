package courseraAlgoCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import courseraAlgoCourse.MyUndirectedGraph.Edge;

public class MyParallelizedUndirectedGraph {
	
	public static class MyCoreGraph{
		 final Node[] nodes ;
		 final Edge[] edges ;
		
		MyCoreGraph(Node[] nodes,Edge[] edges){
			this.nodes = nodes;
			this.edges = edges;
		}
		
		public Node[] getAllNodes(){
			Node[] nodesCopy = new Node[nodes.length];
			System.arraycopy(nodes, 0, nodesCopy, 0, nodes.length);
			return nodesCopy;
		}
		
		public Edge[] getAllEdges(){
			Edge[] edgesCopy = new Edge[edges.length];
			System.arraycopy(edges, 0, edgesCopy, 0, edges.length);
			return edgesCopy;
		}
		
		public static class Node{
			public final int nodeNum;
			protected final List<Edge> incidentEdges = new ArrayList<Edge>();
			//SuperNode containingSuperNode;
			
			Node(int num){
				this.nodeNum = num;
			}
			
			

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + nodeNum;
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				MyCoreGraph.Node other = (MyCoreGraph.Node) obj;
				if (nodeNum != other.nodeNum)
					return false;
				return true;
			}
			
			
		}
		
		public static class Edge{
			public final MyCoreGraph.Node end1;
			public final MyCoreGraph.Node end2;
			
			//boolean selfLoop;
			
			Edge(MyCoreGraph.Node end1, MyCoreGraph.Node end2){
				this.end1 = end1;
				this.end2 = end2;
				this.end1.incidentEdges.add(this);
				this.end2.incidentEdges.add(this);
			}
		}
	}
	
	private final MyCoreGraph actualGraph;
	
	private List<MyCoreGraph.Edge> availableEdges=new ArrayList<MyCoreGraph.Edge>();
	private final Map<MyCoreGraph.Node,SuperNode> node2SuperNodeMap;
	private final Map<MyCoreGraph.Edge,Boolean> edge2SelfLoopFlagMap;
	
	MyParallelizedUndirectedGraph(MyCoreGraph actualGraph){
		this.actualGraph = actualGraph;
		rndEdge = new Random(this.actualGraph.edges.length-1);
		
		node2SuperNodeMap = new HashMap<MyCoreGraph.Node,SuperNode>();
		for(int i=0;i<this.actualGraph.nodes.length;i++){
			node2SuperNodeMap.put(this.actualGraph.nodes[i], null);
		}
		
		edge2SelfLoopFlagMap = new HashMap<MyCoreGraph.Edge,Boolean>();
		for(int i=0;i<this.actualGraph.edges.length;i++){
			edge2SelfLoopFlagMap.put(this.actualGraph.edges[i], false);
		}
		for(int i=0;i<this.actualGraph.edges.length;i++){
			availableEdges.add(this.actualGraph.edges[i]);
		}
		
		nonSuperNodes = new ArrayList<MyCoreGraph.Node>(Arrays.asList(this.actualGraph.nodes));
		superNodes = new ArrayList<SuperNode>();
	}
	
	private Random rndEdge;
	
	private List<MyCoreGraph.Node> nonSuperNodes;
	private List<SuperNode> superNodes;
	
	
	
	
	
	/*public List<MyCoreGraph.Node> getAvailableNodes(){
		ArrayList<MyCoreGraph.Node> nodelst = new ArrayList<MyCoreGraph.Node>();
		nodelst.addAll(nonSuperNodes);
		nodelst.addAll(superNodes);
		return nodelst;
	}*/
	public List<MyCoreGraph.Edge> getAvailableEdges(){
		/*ArrayList<MyCoreGraph.Edge> edgLst = new ArrayList<MyCoreGraph.Edge>();
		int numOfEdges = actualGraph.edges.length;
		for(int i=0;i<numOfEdges;i++){
			if(!edge2SelfLoopFlagMap.get(actualGraph.edges[i]))
				edgLst.add(actualGraph.edges[i]);
		}
		return edgLst;*/
		return this.availableEdges;
	}
	
	public MyCoreGraph.Edge selectEdgeForContraction(){
		int edgIndx = 0;
		Random rndEdge1 = new Random(availableEdges.size());
		//System.out.println("edges length --> "+ edges.length);
		/*for(edgIndx = rndEdge.nextInt();edgIndx >= edges.length || edgIndx < 0 || edges[edgIndx].selfLoop;edgIndx = rndEdge.nextInt());
		Edge selectedEdge = edges[edgIndx--];*/
		for(edgIndx = rndEdge1.nextInt();edgIndx >= availableEdges.size() || edgIndx < 0 || this.edge2SelfLoopFlagMap.get(availableEdges.get(edgIndx));edgIndx = rndEdge1.nextInt());
		MyCoreGraph.Edge selectedEdge =availableEdges.get(edgIndx-1);
		//System.out.println("randomly selected edge --> "+selectedEdge);
		return selectedEdge;
	}
	
	public int getNumOfAvailableNodes(){
		return this.nonSuperNodes.size()+this.superNodes.size();
	}
	
	public void contractEdge(){
		MyCoreGraph.Edge tobeContracted = selectEdgeForContraction();
		MyCoreGraph.Node end1Node = tobeContracted.end1;
		MyCoreGraph.Node end2Node = tobeContracted.end2;
		
		SuperNode end1SuperNode =this.node2SuperNodeMap.get(end1Node);
		SuperNode end2SuperNode = this.node2SuperNodeMap.get(end2Node);
		
		SuperNode superNodeCreated = null;
		if(end1SuperNode == null && end2SuperNode == null ){
			superNodeCreated = new SuperNode(end1Node, end2Node);
			this.nonSuperNodes.remove(end1Node);
			this.nonSuperNodes.remove(end2Node);
			this.superNodes.add(superNodeCreated);
		}else if(end1SuperNode != null && end2SuperNode != null ){
			superNodeCreated = new SuperNode(end1SuperNode, end2SuperNode);
			this.superNodes.remove(end1SuperNode);
			this.superNodes.remove(end2SuperNode);
			this.superNodes.add(superNodeCreated);
		}else{
			SuperNode endSuperNode = null;
			MyCoreGraph.Node endNode = null;
			if(end1SuperNode != null){
				endSuperNode = end1SuperNode;
				endNode = end2Node;
			}else{
				endSuperNode = end2SuperNode;
				endNode = end1Node;
			}
			superNodeCreated = new SuperNode(endSuperNode, endNode);
			this.superNodes.remove(endSuperNode);
			this.nonSuperNodes.remove(endNode);
			this.superNodes.add(superNodeCreated);
		}
	}
	
/*	public void purge(){
       rndEdge = new Random(this.actualGraph.edges.length-1);
		
		nonSuperNodes = new ArrayList<MyCoreGraph.Node>(Arrays.asList(actualGraph.nodes));
		superNodes = new ArrayList<SuperNode>();
		int numOfNodes = this.actualGraph.nodes.length;
		for(int i = 0;i<numOfNodes;i++){
			this.actualGraph.nodes[i].containingSuperNode = null;
		}
		
		int numOfEdges = this.actualGraph.edges.length;
		for(int i = 0;i<numOfEdges;i++){
			this.actualGraph.edges[i].selfLoop = false;
		}
	}*/
	
	public static class GraphBuilder{
		private Map<Integer,MyCoreGraph.Node> nodes = new HashMap<Integer,MyCoreGraph.Node>();
		private Set<String> edges = new HashSet<String>();
		
		public GraphBuilder addEdge(int end1, int end2){
			if(!nodes.containsKey(end1))
			  nodes.put(end1, new MyCoreGraph.Node(end1));
			if(!nodes.containsKey(end2))
			  nodes.put(end2,new MyCoreGraph.Node(end2));
			String edgeStr = end1<end2?end1+"_"+end2:end2+"_"+end1;
			edges.add(edgeStr);
			return this;
		}
		
		public MyCoreGraph build(){
			int numOfEdges = edges.size();
			MyCoreGraph.Edge[] edges4Graph = new MyCoreGraph.Edge[numOfEdges];
			int i =0;
			for(String currEdgStr:edges){
				String[] ends = currEdgStr.split("_"); 
				edges4Graph[i++] = new MyCoreGraph.Edge(nodes.get(Integer.parseInt(ends[0])),nodes.get(Integer.parseInt(ends[1])));
			}
			MyCoreGraph.Node[] nodes4Graph = new ArrayList<MyCoreGraph.Node>(nodes.values()).toArray(new MyCoreGraph.Node[0]);
			return new MyCoreGraph(nodes4Graph, edges4Graph);
		}
		
	}
	
	
	
	
	private class SuperNode {
		public final Set<MyCoreGraph.Node> children = new HashSet<MyCoreGraph.Node>();
		SuperNode(SuperNode child1, MyCoreGraph.Node child2){
			this.children.addAll((child1).children);
			this.children.add(child2);
			addSuperNodeRefAndMarkSelfLoop();
		}
		
		
		SuperNode(SuperNode child1, SuperNode child2){
			this.children.addAll((child1).children);
			this.children.addAll((child2).children);
			addSuperNodeRefAndMarkSelfLoop();
		}
		SuperNode(MyCoreGraph.Node child1, MyCoreGraph.Node child2){
			
 			this.children.add(child1);
 			
			this.children.add(child2);
			MyParallelizedUndirectedGraph.this.node2SuperNodeMap.put(child1,this) ;
			MyParallelizedUndirectedGraph.this.node2SuperNodeMap.put(child2,this) ;
			
			//addSuperNodeRefAndMarkSelfLoop();
			
		}
		public void addSuperNodeRefAndMarkSelfLoop() {
			ArrayList<MyCoreGraph.Edge> allIncidentEdges = new ArrayList<MyCoreGraph.Edge>();
			for(MyCoreGraph.Node currNode:this.children){
				MyParallelizedUndirectedGraph.this.node2SuperNodeMap.put(currNode,this) ;
				allIncidentEdges.addAll(currNode.incidentEdges);
			}
			
			for(MyCoreGraph.Edge currEdg:allIncidentEdges){
				if(!MyParallelizedUndirectedGraph.this.edge2SelfLoopFlagMap.get(currEdg)){
					if(this.children.contains(currEdg.end1) && this.children.contains(currEdg.end2)){
						MyParallelizedUndirectedGraph.this.edge2SelfLoopFlagMap.put(currEdg,true);
						MyParallelizedUndirectedGraph.this.availableEdges.remove(currEdg);
					}
				}
			}
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((children == null) ? 0 : children.hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SuperNode other = (SuperNode) obj;
			if (children == null) {
				if (other.children != null)
					return false;
			} else if (!children.equals(other.children))
				return false;
			return true;
		}
		
		
		
	}
	
	

}
