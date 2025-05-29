package courseraAlgoCourse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyGraph {
	
	private Set<Node> nodes  = new HashSet<Node>();
	private List<Edge> edges = new ArrayList<Edge>();
	
	private Map<Integer,Node> numToNodeMap = new HashMap<Integer,Node>();
	
	//should move to a builder
	public void addEdges(int end1NodeNum, int end2NodeNum){
		Node end1Node = numToNodeMap.get(end1NodeNum), end2Node =  this.numToNodeMap.get(end1NodeNum);
		if(end1Node == null){
			end1Node = new Node(end1NodeNum);
			nodes.add(end1Node);
			numToNodeMap.put(end1NodeNum, end1Node);
		}
		
		if(end2Node == null){
			end2Node = new Node(end2NodeNum);
			nodes.add(end2Node);
			numToNodeMap.put(end2NodeNum, end2Node);
		}
		
		this.edges.add(new Edge(end1Node,end2Node));
	}
	
	public void contractEdge(Edge edg){
		Node nodeTobePreserved = edg.getEnd1();
		Node nodeTobeDeleted = edg.getEnd2();
		List<Edge> edgesTobeRewired = nodeTobeDeleted.getIncidentEdges();
		
		ArrayList<Edge> reWiredEdges = new ArrayList<Edge>();
		for(Edge currEdge:edgesTobeRewired){
			
		}
		
		
	}
	
	public Set<Node> getNodes(){
		return Collections.unmodifiableSet(this.nodes);
	}
	
	public List<Edge> getEdges(){
		return Collections.unmodifiableList(this.edges);
	}
   
	public static class Node{
	   private int nodeNum;
	   private ArrayList<Edge> incidentEdges = new ArrayList<Edge>();
	
	   public Node(int nodeNum){
		   this.nodeNum = nodeNum;
	   }
	   
	   void addIncidentEdge(Edge edg){
		   //do not allow edges not connected to this node or self loop edges
		   if(this.equals(edg.getEnd1()) ^ this.equals(edg.getEnd2())){
			   this.incidentEdges.add(edg);
		   }
	   }
	   
	   public List<Edge> getIncidentEdges(){
		   return Collections.unmodifiableList(this.incidentEdges);
	   }
	   
	public int getNodeNum() {
		return nodeNum;
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
	
   }

   public static class Edge{
	   private Node end1;
	   private Node end2;
	   
	   public Edge(Node end1, Node end2){
		   assert(end1 != null);
		   assert(end2 != null);
		   
		   if(end1.getNodeNum() < end2.getNodeNum()){
			   this.end1 = end1;
			   this.end2 = end2;
		   }else{
			   this.end1 = end2;
			   this.end2 = end1;
		   }
		   
		   this.end1.addIncidentEdge(this);
		   this.end2.addIncidentEdge(this);
	   }

	public Node getEnd1() {
		return end1;
	}

	public Node getEnd2() {
		return end2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end1 == null) ? 0 : end1.hashCode());
		result = prime * result + ((end2 == null) ? 0 : end2.hashCode());
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
		Edge other = (Edge) obj;
		if (end1 == null) {
			if (other.end1 != null)
				return false;
		} else if (!end1.equals(other.end1))
			return false;
		if (end2 == null) {
			if (other.end2 != null)
				return false;
		} else if (!end2.equals(other.end2))
			return false;
		return true;
	}
	   
	   
   }

}
