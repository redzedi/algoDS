package courseraAlgoCourse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import courseraAlgoCourse.MyUndirectedGraph.Edge;
import courseraAlgoCourse.MyUndirectedGraph.Node;


public class DijkstraAlgo {
	
	public static void runDijkstra(MyUndirectedGraph grph){
		final int FROM_NODE = 1;
		int numOfNodes = grph.getAllNodes().length;
		final int[] shortestPathLengthFromSrc = new int[numOfNodes];
		Arrays.fill(shortestPathLengthFromSrc, 1000000);
		Node[] allNodes = grph.getAllNodes();
		boolean[] doneNodes = new boolean[numOfNodes];
		int numOfNodesDone = 0;
		PriorityQueue<Edge> currAvlablEdges = new PriorityQueue<Edge>(11,new Comparator<Edge>(){

			@Override
			public int compare(Edge o1, Edge o2) {
				return (shortestPathLengthFromSrc[o1.end1.nodeNum-1] + o1.weight) - (shortestPathLengthFromSrc[o2.end1.nodeNum-1] + o2.weight);
			}
			
		}); 
		
		//do the first node
		shortestPathLengthFromSrc[FROM_NODE-1] = 0;//distance to itself
		doneNodes[FROM_NODE-1] = true;//distance to itself
		numOfNodes++;
		currAvlablEdges.addAll(allNodes[FROM_NODE-1].incidentEdges);
		ArrayList<Edge> toAddDuringAnItr = new ArrayList<Edge>(); 
		while(numOfNodesDone < numOfNodes && !currAvlablEdges.isEmpty()){
			Edge leastWtEdge = currAvlablEdges.poll();
			while(doneNodes[leastWtEdge.end2.nodeNum-1] && !currAvlablEdges.isEmpty()){
				leastWtEdge = currAvlablEdges.poll();
			}
			if(doneNodes[leastWtEdge.end2.nodeNum-1]){
				break;
			}
			List<Edge>  candidateEdges = leastWtEdge.end2.incidentEdges;
			for(Edge currEdge:candidateEdges){
				if(!currEdge.equals(leastWtEdge) && currAvlablEdges.contains(currEdge)){
					currAvlablEdges.remove(currEdge);
				}else{
					if(!doneNodes[currEdge.end2.nodeNum-1]){
						//currAvlablEdges.offer(currEdge);
						toAddDuringAnItr.add(currEdge);
					}
				}
			}
			doneNodes[leastWtEdge.end2.nodeNum-1] = true;
			numOfNodesDone++;
			shortestPathLengthFromSrc[leastWtEdge.end2.nodeNum-1] = shortestPathLengthFromSrc[leastWtEdge.end1.nodeNum-1] + leastWtEdge.weight;
			currAvlablEdges.addAll(toAddDuringAnItr);
			toAddDuringAnItr.clear();
		}
		//System.out.println("shortest paths lengths -->"+Arrays.asList(shortestPathLengthFromSrc));
		//System.out.println("shortest paths lengths -->"+Arrays.toString(shortestPathLengthFromSrc));
		for(int i=0;i<shortestPathLengthFromSrc.length;i++ )
			System.out.println("[ "+(i+1)+", "+shortestPathLengthFromSrc[i]+" ]");
		System.out.println("Shortest path from 1 to the following are  7,37,59,82,99,115,133,165,188,197 --> " +
				""+shortestPathLengthFromSrc[7-1]+","+shortestPathLengthFromSrc[37-1]+","+shortestPathLengthFromSrc[59-1]+","+
				shortestPathLengthFromSrc[82-1]+","+shortestPathLengthFromSrc[99-1]+","+shortestPathLengthFromSrc[115-1]+
				","+shortestPathLengthFromSrc[133-1]+","+shortestPathLengthFromSrc[165-1]+","+shortestPathLengthFromSrc[188-1]+","+shortestPathLengthFromSrc[197-1]);
	}
	
	public static MyUndirectedGraph buildGraphFromGivenFile(String filePath) throws IOException{
		Reader rdr = null;
		try {
			MyUndirectedGraph.WeightedDirectedGraphBuilder builder = new MyUndirectedGraph.WeightedDirectedGraphBuilder();
			rdr = new BufferedReader( new FileReader(filePath));
			StreamTokenizer flStrm = new StreamTokenizer( rdr);
			flStrm.eolIsSignificant(true);
			flStrm.parseNumbers();
			
			int currToken = -1;
			int currFromNode = -1;
			ArrayList<Integer> lineContents = new ArrayList<Integer>();
			while((currToken = flStrm.nextToken()) != StreamTokenizer.TT_EOF){
					if (currToken != StreamTokenizer.TT_EOL) {
						if (currToken == StreamTokenizer.TT_NUMBER) {
							lineContents.add((int)flStrm.nval);
							/*System.out.println("number read from file --> "
									+ (int) flStrm.nval);*/
						}
					}else{
						if(!lineContents.isEmpty()){
							currFromNode = lineContents.get(0);
							for(int i=1;i<lineContents.size();i++){
								if(i%2 == 0){
									builder.addEdge(currFromNode, lineContents.get(i-1), lineContents.get(i));
								}
							}
							lineContents.clear();
						}
					}
			}
			
			return builder.build();
		} finally {
			if(rdr != null){
				rdr.close();
			}
		}
	}

	
	public static void main(String[] args) throws IOException {
		MyUndirectedGraph grph = buildGraphFromGivenFile("resources/dijkstraData.txt");
		//MyUndirectedGraph grph = buildGraphFromGivenFile("resources/lectureDijkstra_1.txt");
		//MyUndirectedGraph grph = buildGraphFromGivenFile("resources/lectureDijkstra_2.txt");
		runDijkstra(grph);
	}
}
