package courseraAlgoCourse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TwoSUMAlgo {
	
	public static void TwoSUMAlgoRun(List<Integer> srcNums, int range){
		HashMap<Integer,TreeSet<Integer>> arrangedNums = new HashMap<Integer,TreeSet<Integer>>();
		for(Integer currNum:srcNums){
			int key = currNum/range;
			TreeSet<Integer> closeNums = arrangedNums.get(key);
			if(closeNums == null){
				closeNums = new TreeSet<Integer>();
				arrangedNums.put(key, closeNums);
			}
			closeNums.add(currNum);
		}
		HashSet<Integer> doneKeys = new HashSet<Integer>();
		int cntr = 0;
		for(Integer currNum:srcNums){
			if (!doneKeys.contains(-(currNum/range))) {
				if (currNum > 0) {
					int upperBound = -currNum + range;
					int lowerBound = (-currNum - range);
					int uk = upperBound / range;
					int lk = lowerBound / range;
					Set<Integer> ys1 = arrangedNums.containsKey(uk) ? arrangedNums
							.get(uk).tailSet(upperBound) : null;
					Set<Integer> ys2 = arrangedNums.containsKey(lk) ? arrangedNums
							.get(lk).headSet(lowerBound) : null;
					cntr += (ys1 != null) ? ys1.size() : 0;
					cntr += (ys2 != null) ? ys2.size() : 0;
					printSums(currNum, ys1);
					printSums(currNum, ys2);
				} else {
					int lowerBound = currNum + range;
					int upperBound = (-currNum + range);
					int uk = upperBound / range;
					int lk = lowerBound / range;
					Set<Integer> ys1 = arrangedNums.containsKey(uk) ? arrangedNums
							.get(uk).tailSet(upperBound) : null;
					Set<Integer> ys2 = arrangedNums.containsKey(lk) ? arrangedNums
							.get(lk).headSet(lowerBound) : null;
					cntr += (ys1 != null) ? ys1.size() : 0;
					cntr += (ys2 != null) ? ys2.size() : 0;
					printSums(currNum, ys1);
					printSums(currNum, ys2);
				}
				doneKeys.add(currNum/range);
			}
		}
		
		System.out.println("Num of ts --> "+cntr);
	}

	public static void printSums(Integer currNum, Set<Integer> ys1) {
		if(ys1 != null){
			for(Integer currY:ys1)
				System.out.println("sum --. "+(currNum+currY));
		}
	}
	
	public static List<Integer> readIntegersFromGivenFile(String filePath) throws IOException{
		Reader rdr = null;
		try {
			rdr = new BufferedReader( new FileReader(filePath));
			StreamTokenizer flStrm = new StreamTokenizer( rdr);
			flStrm.eolIsSignificant(true);
			flStrm.parseNumbers();
			
			int currToken = -1;
			int currFromNode = -1;
			ArrayList<Integer> lineContents = new ArrayList<Integer>();
			while((currToken = flStrm.nextToken()) != StreamTokenizer.TT_EOF){
						if (currToken == StreamTokenizer.TT_NUMBER) {
							lineContents.add((int)flStrm.nval);
							/*System.out.println("number read from file --> "
									+ (int) flStrm.nval);*/
						}
					
			}
			
			return lineContents;
		} finally {
			if(rdr != null){
				rdr.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<Integer> testNums = readIntegersFromGivenFile("resources/2sum.txt");
		//TwoSUMAlgoRun(testNums, 1000);//41
		TwoSUMAlgoRun(testNums, 100);//5
		/*List<Integer> testNums = readIntegersFromGivenFile("resources/lecture2SUM_1.txt");
		TwoSUMAlgoRun(testNums, 10);*/
		
		//System.out.println("  "+Integer.parseInt("123145972439649"));
	}

}
