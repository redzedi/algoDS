package courseraAlgoCourse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import courseraAlgoCourse.MyParallelizedUndirectedGraph.MyCoreGraph;

public class ParallelizedMinCutAlgo {
	
	public static MyCoreGraph buildGraphFromGivenFile(String filePath) throws IOException{
		Reader rdr = null;
		try {
			MyParallelizedUndirectedGraph.GraphBuilder builder = new MyParallelizedUndirectedGraph.GraphBuilder();
			rdr = new BufferedReader( new FileReader(filePath));
			StreamTokenizer flStrm = new StreamTokenizer( rdr);
			flStrm.eolIsSignificant(true);
			flStrm.parseNumbers();
			int currToken = -1;
			ArrayList<Integer> lineContents = new ArrayList<Integer>();
			while((currToken = flStrm.nextToken()) != StreamTokenizer.TT_EOF){
				if(currToken != StreamTokenizer.TT_EOL){
					if(currToken == StreamTokenizer.TT_NUMBER){
						lineContents.add((int)flStrm.nval);
						//System.out.println("number read from file --> "+(int)flStrm.nval);
					}
				}else{
					//end of line - processing
					//System.out.println("Line to be addded to graph --> "+lineContents);
					Integer fromNode = lineContents.get(0);
					int lineSize = lineContents.size();
					for(int i=1;i<lineSize;i++){
						builder.addEdge(fromNode, lineContents.get(i));
					}
					lineContents.clear();
				}
			}
			
			return builder.build();
		} finally {
			if(rdr != null){
				rdr.close();
			}
		}
	}
	
	public static int getMinCutOfGraphByKargersAlgo(MyCoreGraph crGrph){
		int minCutNum = -1;
		
		int numOfNodes = crGrph.getAllNodes().length;
		Double numOfItr = numOfNodes*numOfNodes*Math.log(numOfNodes);
		int numOfItr_intVal = numOfItr.intValue();
		System.out.println("Total number of Iterations --> "+numOfItr);
		for(int i=0; i < numOfItr_intVal; i++){
			System.out.println("** Starting iteration "+i+" the minCutNum is --> "+minCutNum);
			int numOfAvlablNodes = 0;
			long t1 = System.currentTimeMillis();
			MyParallelizedUndirectedGraph grph = new MyParallelizedUndirectedGraph(crGrph);
			while((numOfAvlablNodes = grph.getNumOfAvailableNodes()) > 2){
				//System.out.println("numOfAvlablNodes --> "+numOfAvlablNodes);
				grph.contractEdge();
			}
			System.out.println("Time taken to complete iteration --> "+i+" is(second) ==> "+(System.currentTimeMillis()-t1)/1000);
			if(i==0){
				minCutNum = grph.getAvailableEdges().size();
				System.out.println("Itr -->"+i+"mincut registered --> "+minCutNum);
			}else{
				int currCutNum = grph.getAvailableEdges().size();
				System.out.println("Itr -->"+i+" mincut found --> "+currCutNum);
				if(currCutNum < minCutNum){
					minCutNum = currCutNum;
					System.out.println("Itr -->"+i+"mincut registered --> "+minCutNum);
				}
			}
			//grph.purge();
		}
		
		return minCutNum;
	}
	
	public static void main(String[] args) throws IOException {
		MyCoreGraph grph = buildGraphFromGivenFile("/home/suman/techWork/algoAnalysisCourse/kargerMinCut.txt");
		int minCutForGivenGraph = getMinCutOfGraphByKargersAlgo(grph);
		System.out.println("MyUndirectedGraph --> "+minCutForGivenGraph);
	}

}
