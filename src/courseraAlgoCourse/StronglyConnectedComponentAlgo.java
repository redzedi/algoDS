package courseraAlgoCourse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Comparator;
import java.util.PriorityQueue;

import courseraAlgoCourse.MyDirectedGraph.Node;

public class StronglyConnectedComponentAlgo {
	

	public static void detectSCC(MyDirectedGraph grph){
		PriorityQueue<MyDirectedGraph.Node> nodeHeap = new PriorityQueue<MyDirectedGraph.Node>(grph.nodes.length,new Comparator<MyDirectedGraph.Node>(){

			@Override
			public int compare(Node o1, Node o2) {
				return o2.finishTime - o1.finishTime;
			}});
		//1.run reverse DFS
		System.out.println("starting reverse DFS on graph with nodes --> "+grph.nodes.length);
		for(int i=1;i<grph.nodes.length;i++){
			if (grph.nodes[i] != null) {
				if (!grph.nodes[i].isVisited) {
					grph.runReverseDFS(i);
				}
				//System.out.println("calculated Node #"+grph.nodes[i].nodeNum+" finishTime "+grph.nodes[i].finishTime);
				nodeHeap.add(grph.nodes[i]);
			}else{
				System.out.println("Null node Indx ==> "+i);
			}
		}
		//2. Add all nodes to a heap
		 grph.markAllNodesAsNotVisited();
		 
		 //3. run actual DFS in the reverse order of the first DFS time
		 PriorityQueue<Integer> sccSizeHeap = new PriorityQueue<Integer>(11, new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2-o1;
			}
			 
		 });
		 for(Node currNode = nodeHeap.poll();currNode != null;currNode = nodeHeap.poll()){
			 if(!currNode.isVisited){
				// System.out.println("running dfs on node #"+currNode.nodeNum+" with finish time --> "+currNode.finishTime);
				 int sccSize = grph.runDFS(currNode.nodeNum);
				 System.out.println("detected SCC of size --> "+sccSize);
				 sccSizeHeap.add(sccSize);
			 }else{
				 System.out.println("**NOT**srunning dfs on node #"+currNode.nodeNum+" with finish time --> "+currNode.finishTime);
			 }
		 }
		 int a = 0;
		 StringBuilder resString = new StringBuilder();
		 for(Integer currNum = sccSizeHeap.poll();currNum != null && a<5;currNum = sccSizeHeap.poll(),a++){
			 if(a > 0)
				 resString.append(",");
			 resString.append(currNum);
		 }
		 for(int i= a;i<5;i++){
			 resString.append(",0");
		 }
		 // correct result is --> 434821,968,459,313,211
		System.out.println("Result is --> "+resString);
		
	}
	
	public static MyDirectedGraph buildGraphFromGivenFile(String filePath, int numOfNodes) throws IOException{
		Reader rdr = null;
		try {
			MyDirectedGraph grph = new MyDirectedGraph(numOfNodes);
			rdr = new BufferedReader( new FileReader(filePath));
			StreamTokenizer flStrm = new StreamTokenizer( rdr);
			flStrm.eolIsSignificant(true);
			flStrm.parseNumbers();
			int currToken = -1;
			Integer[] lineContents = new Integer[2];
			int lineContentIdx = 0;
			while((currToken = flStrm.nextToken()) != StreamTokenizer.TT_EOF){
				if(currToken != StreamTokenizer.TT_EOL){
					if(currToken == StreamTokenizer.TT_NUMBER){
						lineContents[lineContentIdx++] = ((int)flStrm.nval);
						//System.out.println("number read from file --> "+(int)flStrm.nval);
					}
				}else{
					//end of line - processing
					//System.out.println("Line to be addded to graph --> "+lineContents);
					grph.addEdge(lineContents[0], lineContents[1]);
					lineContentIdx = 0;
				}
			}
			
			return grph;
		} finally {
			if(rdr != null){
				rdr.close();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		MyDirectedGraph grph = buildGraphFromGivenFile("resources/SCC.txt",875715);
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph.txt", 10);//correct
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph_32221.txt", 12);
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph_33110.txt", 9);
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph_33200.txt", 9);
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph_61100.txt", 15);//nodes graph should be non-initialized with non-null stuff
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph_63210.txt", 13);// incorrect
		
		//MyDirectedGraph grph = buildGraphFromGivenFile("resources/lectureSCCGraph_71000.txt", 9); //correct
		detectSCC(grph);
	}

}
