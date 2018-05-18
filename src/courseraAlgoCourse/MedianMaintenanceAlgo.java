package courseraAlgoCourse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianMaintenanceAlgo {
	
	public static void findSumOfMedians(String filePath) throws IOException{
		Reader rdr = null;
		try {
			PriorityQueue<Integer> hMIN = new PriorityQueue<Integer>(11,new Comparator<Integer>(){

				@Override
				public int compare(Integer o1, Integer o2) {
					return o2-o1;
				}
				
			});
			PriorityQueue<Integer> hMAX = new PriorityQueue<Integer>();
			rdr = new BufferedReader( new FileReader(filePath));
			StreamTokenizer flStrm = new StreamTokenizer( rdr);
			flStrm.eolIsSignificant(true);
			flStrm.parseNumbers();
			int cntr = 1;
			int acc = 0;
			int currToken = -1;
			while((currToken = flStrm.nextToken()) != StreamTokenizer.TT_EOF){
						if (currToken == StreamTokenizer.TT_NUMBER) {
							int currNum = (int) flStrm.nval;
							Integer kthElem = hMIN.peek();
							Integer kPLUS1thElem = hMAX.peek();
							if(kthElem == null || currNum <= kthElem ){
								hMIN.offer(currNum);
							}else if(kPLUS1thElem == null || currNum >= kPLUS1thElem ){
								hMAX.offer(currNum);
							}
							//rebalance
							while(Math.abs(hMAX.size() - hMIN.size()) > 1){
								if(hMAX.size() > hMIN.size()){
									hMIN.offer(hMAX.poll());
								}else{
									hMAX.offer(hMIN.poll());
								}
							}
							//detect median
							if(cntr % 2 == 0){
								acc += hMIN.peek();
//								System.out.println("hmIN "+ hMIN);
//								System.out.println("hMAX "+ hMAX);
//								System.out.println(cntr+" th Median --> "+hMIN.peek());
							}else{
								if(hMAX.size() > hMIN.size()){
									acc += hMAX.peek();
//									System.out.println("hmIN "+ hMIN);
//									System.out.println("hMAX "+ hMAX);
//									System.out.println(cntr+" th Median --> "+hMAX.peek());
								}else{
									acc += hMIN.peek();
//									System.out.println("hmIN "+ hMIN);
//									System.out.println("hMAX "+ hMAX);
//									System.out.println(cntr+" th Median --> "+hMIN.peek());
								}
							}
							cntr++;
							
						}
					
			}
			System.out.println("sum of medians --> "+acc);
			
			
		} finally {
			if(rdr != null){
				rdr.close();
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
			//findSumOfMedians("resources/lectureMedianData_30.txt");
			findSumOfMedians("resources/Median.txt");
		
	}

}
