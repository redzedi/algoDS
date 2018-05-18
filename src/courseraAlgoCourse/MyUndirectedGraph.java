package courseraAlgoCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MyUndirectedGraph {
	private final Node[] nodes ;
	private final Edge[] edges ;
	
	//private Random rndEdge;
	
	//private List<Node> nonSuperNodes;
	//private List<SuperNode> superNodes;
	//private List<Edge> availableEdges=new ArrayList<Edge>();
	
	private int nonSuperNodeCnt;
	private int superNodeCnt;
	
	MyUndirectedGraph(Node[] nodes,Edge[] edges){
		this.nodes = nodes;
		this.edges = edges;
		
		//rndEdge = new Random(this.edges.length-1);
		nonSuperNodeCnt = nodes.length;
		superNodeCnt = 0;
		/*nonSuperNodes = new ArrayList<Node>(Arrays.asList(nodes));
		superNodes = new ArrayList<SuperNode>();*/
		
		/*for(int i=0;i<this.edges.length;i++){
			availableEdges.add(this.edges[i]);
		}*/
	}
	
	public Node[] getAllNodes(){
		return this.nodes;
	}
	
	public Edge[] getAllEdges(){
		return this.edges;
	}
	
	/*public Node[] getAllNodes(){
		Node[] nodesCopy = new Node[nodes.length];
		System.arraycopy(nodes, 0, nodesCopy, 0, nodes.length);
		return nodesCopy;
	}
	
	public Edge[] getAllEdges(){
		Edge[] edgesCopy = new Edge[edges.length];
		System.arraycopy(edges, 0, edgesCopy, 0, edges.length);
		return edgesCopy;
	}*/
	
	/*public List<Node> getAvailableNodes(){
		ArrayList<Node> nodelst = new ArrayList<Node>();
		nodelst.addAll(nonSuperNodes);
		nodelst.addAll(superNodes);
		return nodelst;
	}*/
	public List<Edge> getAvailableEdges(){
		//return this.availableEdges;
		ArrayList<Edge> edgLst = new ArrayList<Edge>();
		int numOfEdges = edges.length;
		for(int i=0;i<numOfEdges;i++){
			if(!isSelfLoop(edges[i]))
				edgLst.add(edges[i]);
		}
		return edgLst;
	}
	
	public Edge selectEdgeForContraction(){
		int edgIndx = 0;
		//Random rndEdge1 = new Random(availableEdges.size());
		Random rndEdge1 = new Random();
		//System.out.println("edges length --> "+ edges.length);
		for(edgIndx = rndEdge1.nextInt(edges.length);edgIndx >= edges.length || edgIndx < 0 || isSelfLoop(edges[edgIndx]);edgIndx = rndEdge1.nextInt(edges.length));
		Edge selectedEdge = edges[edgIndx];
		/*for(edgIndx = rndEdge1.nextInt();edgIndx >= availableEdges.size() || edgIndx < 0 || isSelfLoop(availableEdges.get(edgIndx));edgIndx = rndEdge1.nextInt());
		Edge selectedEdge =availableEdges.get(edgIndx);*/
		//System.out.println("randomly selected edge --> "+selectedEdge);
		return selectedEdge;
	}
	private boolean isSelfLoop(Edge edg){
		return ((edg.end1.containingSuperNode != null) &&
		       (edg.end2.containingSuperNode != null) &&
		       (edg.end1.containingSuperNode.equals(edg.end2.containingSuperNode)));
	}
	
	public int getNumOfAvailableNodes(){
		//System.out.println("superNodes --> "+this.superNodes);
		//System.out.println("NonsuperNodes --> "+this.nonSuperNodes);
		//return this.nonSuperNodes.size()+this.superNodes.size();
		return this.nonSuperNodeCnt + this.superNodeCnt;
	}
	
	public void contractEdge(){
		//long t1 = System.currentTimeMillis();
		Edge tobeContracted = selectEdgeForContraction();
		//System.out.println("time taken for random select --> "+(System.currentTimeMillis() - t1));
		Node end1Node = tobeContracted.end1;
		Node end2Node = tobeContracted.end2;
		
		SuperNode end1SuperNode = end1Node.containingSuperNode;
		SuperNode end2SuperNode = end2Node.containingSuperNode;
		
		SuperNode superNodeCreated = null;
		if(end1SuperNode == null && end2SuperNode == null ){
			superNodeCreated = new SuperNode(end1Node, end2Node);
			nonSuperNodeCnt -= 2;
			superNodeCnt++; 
			/*this.nonSuperNodes.remove(end1Node);
			this.nonSuperNodes.remove(end2Node);
			this.superNodes.add(superNodeCreated);*/
		}else if(end1SuperNode != null && end2SuperNode != null ){
			superNodeCreated = new SuperNode(end1SuperNode, end2SuperNode);
			superNodeCnt--;
			/*this.superNodes.remove(end1SuperNode);
			this.superNodes.remove(end2SuperNode);
			this.superNodes.add(superNodeCreated);*/
		}else{
			SuperNode endSuperNode = null;
			Node endNode = null;
			if(end1SuperNode != null){
				endSuperNode = end1SuperNode;
				endNode = end2Node;
			}else{
				endSuperNode = end2SuperNode;
				endNode = end1Node;
			}
			superNodeCreated = new SuperNode(endSuperNode, endNode);
			nonSuperNodeCnt--;
			/*this.superNodes.remove(endSuperNode);
			this.nonSuperNodes.remove(endNode);
			this.superNodes.add(superNodeCreated);*/
		}
	}
	
	public void purge(){
       //rndEdge = new Random(this.edges.length-1);
       nonSuperNodeCnt = nodes.length;
		superNodeCnt = 0;
		/*nonSuperNodes = new ArrayList<Node>(Arrays.asList(nodes));
		superNodes = new ArrayList<SuperNode>();*/
		int numOfNodes = this.nodes.length;
		for(int i = 0;i<numOfNodes;i++){
			this.nodes[i].containingSuperNode = null; 
		}
		
		int numOfEdges = this.edges.length;
		/*for(int i = 0;i<numOfEdges;i++){
			this.edges[i].selfLoop = false;
		}*/
		/*this.availableEdges.clear();
		for(int i=0;i<this.edges.length;i++){
			availableEdges.add(this.edges[i]);
		}*/
	}
	
	public static class GraphBuilder{
		private Map<Integer,Node> nodes = new HashMap<Integer,Node>();
		private Set<String> edges = new HashSet<String>();
		
		public GraphBuilder addEdge(int end1, int end2){
			if(!nodes.containsKey(end1))
			  nodes.put(end1, new Node(end1));
			if(!nodes.containsKey(end2))
			  nodes.put(end2,new Node(end2));
			String edgeStr = end1<end2?end1+"_"+end2:end2+"_"+end1;
			edges.add(edgeStr);
			return this;
		}
		
		public MyUndirectedGraph build(){
			int numOfEdges = edges.size();
			Edge[] edges4Graph = new Edge[numOfEdges];
			int i =0;
			for(String currEdgStr:edges){
				String[] ends = currEdgStr.split("_"); 
				edges4Graph[i++] = new Edge(nodes.get(Integer.parseInt(ends[0])),nodes.get(Integer.parseInt(ends[1])));
			}
			Node[] nodes4Graph = new ArrayList<Node>(nodes.values()).toArray(new Node[0]);
			return new MyUndirectedGraph(nodes4Graph, edges4Graph);
		}
		
	}
	
	public static class WeightedDirectedGraphBuilder{
		private Map<Integer,Node> nodes = new HashMap<Integer,Node>();
		private Set<String> edges = new HashSet<String>();
		
		public WeightedDirectedGraphBuilder addEdge(int end1, int end2, int weight){
			if(!nodes.containsKey(end1))
			  nodes.put(end1, new Node(end1));
			if(!nodes.containsKey(end2))
			  nodes.put(end2,new Node(end2));
			String edgeStr = end1+"_"+end2+"_"+weight;
			edges.add(edgeStr);
			return this;
		}
		
		public MyUndirectedGraph build(){
			int numOfEdges = edges.size();
			Edge[] edges4Graph = new Edge[numOfEdges];
			int i =0;
			for(String currEdgStr:edges){
				String[] ends = currEdgStr.split("_"); 
				edges4Graph[i++] = new Edge(nodes.get(Integer.parseInt(ends[0])),nodes.get(Integer.parseInt(ends[1])), Integer.parseInt(ends[2]));
			}
			Node[] nodes4Graph = new ArrayList<Node>(nodes.values()).toArray(new Node[0]);
			return new MyUndirectedGraph(nodes4Graph, edges4Graph);
		}
		
	}
	
	
	public static class Node{
		public final int nodeNum;
		protected final List<Edge> incidentEdges = new ArrayList<Edge>();
		SuperNode containingSuperNode;
		
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
			Node other = (Node) obj;
			if (nodeNum != other.nodeNum)
				return false;
			return true;
		}



		@Override
		public String toString() {
			return "Node [nodeNum=" + nodeNum + ", HascontainingSuperNode="
					+ (containingSuperNode != null) + "]";
		}
		
	}
	
	private  class SuperNode {
		//public final Set<Node> children = new HashSet<Node>();
		public final Map<Integer,Node> children = new HashMap<Integer,Node>();
		
		//public final Set<Edge> outgoingEdges = new HashSet<Edge>();
		
		SuperNode(SuperNode child1, Node child2){
			this.children.putAll((child1).children);
			//this.children.put(child2.nodeNum,child2);
			//this was optimization
			/*for(Node currNode:this.children.values()){
				currNode.containingSuperNode = this;
			}
			for(Edge currEdge:child2.incidentEdges){
				if(this.equals(currEdge.end1.containingSuperNode) || this.equals(currEdge.end2.containingSuperNode)){
				currEdge.selfLoop = true;
				MyUndirectedGraph.this.availableEdges.remove(currEdge);
			}
				}*/
			
			//this is original
			this.children.put(child2.nodeNum,child2);
			addSuperNodeRefAndMarkSelfLoop();
		}
		
		
		SuperNode(SuperNode child1, SuperNode child2){
			this.children.putAll((child1).children);
			this.children.putAll((child2).children);
			/*for(Node currNode:this.children.values()){
				currNode.containingSuperNode = this;
			}
			HashSet<Edge> tmp = new HashSet<Edge>();
			tmp.addAll(child1.outgoingEdges);
			tmp.addAll(child2.outgoingEdges);
			for(Edge currEdg:tmp){
				if((child1.equals(currEdg.end1.containingSuperNode) || child2.equals(currEdg.end1.containingSuperNode)) &&
						(child1.equals(currEdg.end2.containingSuperNode) || child2.equals(currEdg.end2.containingSuperNode))){
					currEdg.selfLoop = true;
				}else{
					outgoingEdges.add(currEdg);
				}
			}*/
			addSuperNodeRefAndMarkSelfLoop();
		}
		SuperNode(Node child1, Node child2){
			
 			this.children.put(child1.nodeNum,child1);
 			
			this.children.put(child2.nodeNum,child2);
			//child1.containingSuperNode = this;
			//child2.containingSuperNode = this;
			/*for(Node currNode:this.children.values()){
				currNode.containingSuperNode = this;
				//allIncidentEdges.addAll(currNode.incidentEdges);
			}*/
			
			addSuperNodeRefAndMarkSelfLoop();
			
		}
		public void addSuperNodeRefAndMarkSelfLoop() {
			//HashSet<Edge> allIncidentEdges = new HashSet<Edge>();
			for(Node currNode:this.children.values()){
				currNode.containingSuperNode = this;
				//allIncidentEdges.addAll(currNode.incidentEdges);
			}
			
			/*for(Edge currEdg:allIncidentEdges){
				if(!currEdg.selfLoop){
					if(this.children.get(currEdg.end1.nodeNum) != null && this.children.get(currEdg.end2.nodeNum) != null){
						currEdg.selfLoop = true;
						MyUndirectedGraph.this.availableEdges.remove(currEdg);
					}
				}
			}*/
		}


		/*@Override
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
		}*/


		@Override
		public String toString() {
			return "SuperNode [children=" + children + "]";
		}
		
		
		
	}
	
	public static class Edge{
		public final Node end1;
		public final Node end2;
		public final Integer weight;
		
		//boolean selfLoop;
		Edge(Node end1, Node end2){
			this(end1,end2,0);
		}
		Edge(Node end1, Node end2,int weight){
			this.end1 = end1;
			this.end2 = end2;
			this.end1.incidentEdges.add(this);
			this.end2.incidentEdges.add(this);
			this.weight = weight;
		}
		@Override
		public String toString() {
			return "Edge [end1=" + end1 + ", end2=" + end2 + ", weight="
					+ weight + "]";
		}
		
		
	}

}
