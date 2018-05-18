package eipBook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class ArrayProblems {
	
	
	//generate Pascal's triangle
	public ArrayList<ArrayList<Integer>> generate(int maxRows) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		if(maxRows < 1){
			return res;
		}
		res.add(new ArrayList<Integer>(Arrays.asList(1)));
		for(int r=1; r < maxRows;r++){
			ArrayList<Integer> currCol = new ArrayList<Integer>();
			res.add(currCol);
			for(int c=0;c<r+1;c++){
				int lv =0 , rv=0;
				if(r>0 && c>0 ){
					lv = res.get(r-1).get(c-1);
				}
				if(r>0 && c<r){
					rv = res.get(r-1).get(c);
				}
				currCol.add(lv+rv);
			}
		}
		
		return res;
		
    }
	
	
	public int maxProfitBuyingSellingStocksOnce(List<Integer> xs){
		
		int minTillNow = Integer.MAX_VALUE, maxGain = 0;
		
		for(Integer x:xs){
			if(x < minTillNow){
				minTillNow = x;
			}
			if((x-minTillNow) > maxGain){
				maxGain = (x-minTillNow);
			}
			
		}
		return maxGain;
		
	}
	
	
	// find max j-i such that A[i]<A[j] in O(nlogn) time
	public int maximumGap(final List<Integer> xs) {
        int n = xs.size(), maxGap = -1, maxTillNow=Integer.MIN_VALUE;
        int[][] sortedXsWithOrigIdx = new int[xs.size()][2];
        for(int j=0;j<n;j++){
        	sortedXsWithOrigIdx[j][0]=xs.get(j);
        	sortedXsWithOrigIdx[j][1]=j;
        }
        java.util.Arrays.sort(sortedXsWithOrigIdx, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return o1[0]-o2[0];
			}
		});
        for(int j=n-1;j>=0;j--){
        	if(sortedXsWithOrigIdx[j][1] > maxTillNow){
        		maxTillNow = sortedXsWithOrigIdx[j][1];
        	
        	}
        	if((maxTillNow - sortedXsWithOrigIdx[j][1])>maxGap){
        		maxGap = maxTillNow - sortedXsWithOrigIdx[j][1];
        	}
        }
        return maxGap;
    }
	
	
	public String largestNumber(final List<Integer> xs) {
		java.util.Collections.sort(xs, new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				String s1=String.valueOf(o1),s2=String.valueOf(o2);
				
				return (s2+s1).compareTo(s1+s2);
			}
		});
		StringBuilder res  = new StringBuilder();
		boolean nonZeroMSBAdded = false;
		int cntr=0;
		for(Integer x:xs){
			nonZeroMSBAdded = x!=0;
			
			if(nonZeroMSBAdded || cntr++ == xs.size()-1)
			 res.append(x);
		}
		
		return res.toString();
    }
	
	//sieve of Erastothenes
	public List<Integer> getAllPrimesSmallerThan(int n){
		boolean[] isPrime = new boolean[n+1];
		Arrays.fill(isPrime,true);
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		for(int i=2;i<n+1;i++){
			if(isPrime[i]){
				res.add(i);
			}
			for(long j=i,p=i*j;p<n+1; p=i*++j){
				isPrime[(int)p]=false;
			}
		}
		
		return res;
	}
	
	
	//tests
	
	@Test
	public void testMaximumGap(){
		assertEquals(2, maximumGap(Arrays.asList(3,5,4,2)));
		assertEquals(4, maximumGap(Arrays.asList(3,5,4,2,7)));
		//assertEquals(-1, maximumGap(Arrays.asList(5,4,3,2)));
		assertEquals(0, maximumGap(Arrays.asList(1)));
		assertEquals(2, maximumGap(Arrays.asList(-1,-1,2)));
		assertEquals(239, maximumGap(Arrays.asList(83564666, 2976674, 46591497, 24720696, 16376995, 63209921, 25486800, 49369261, 20465079, 64068560, 7453256, 14180682, 65396173, 45808477, 10172062, 28790225, 82942061, 88180229, 62446590, 77573854, 79342753, 2472968, 74250054, 17223599, 47790265, 24757250, 40512339, 24505824, 30067250, 82972321, 32482714, 76111054, 74399050, 65518880, 94248755, 76948016, 76621901, 46454881, 40376566, 13867770, 76060951, 71404732, 21608002, 26893621, 27370182, 35088766, 64827587, 67610608, 90182899, 66469061, 67277958, 92926221, 58156218, 44648845, 37817595, 46518269, 44972058, 27607545, 99404748, 39262620, 98825772, 89950732, 69937719, 78068362, 78924300, 91679939, 52530444, 71773429, 57678430, 75699274, 5835797, 74160501, 51193131, 47950620, 4572042, 85251576, 49493188, 77502342, 3244395, 51211050, 44229120, 2135351, 47258209, 77312779, 37416880, 59038338, 96069936, 20766025, 35497532, 67316276, 38312269, 38357645, 41600875, 58590177, 99257528, 99136750, 4796996, 84369137, 54237155, 64368327, 94789440, 40718847, 12226041, 80504660, 8177227, 85151842, 36165763, 72764013, 36326808, 80969323, 22947547, 76322099, 7536094, 18346503, 65759149, 45879388, 53114170, 92521723, 15492250, 42479923, 20668783, 64053151, 68778592, 3669297, 73903133, 28973293, 73195487, 64588362, 62227726, 17909010, 70683505, 86982984, 64191987, 71505285, 45949516, 28244755, 33863602, 18256044, 25110337, 23997763, 81020611, 10135495, 925679, 98158797, 73400633, 27282156, 45863518, 49288993, 52471826, 30553639, 76174500, 28828417, 41628693, 80019078, 64260962, 5577578, 50920883, 16864714, 54950300, 9267396, 56454292, 40872286, 33819401, 75369837, 6552946, 26963596, 22368984, 43723768, 39227673, 98188566, 1054037, 28292455, 18763814, 72776850, 47192134, 58393410, 14487674, 4852891, 44100801, 9755253, 37231060, 42836447, 38104756, 77865902, 67635663, 43494238, 76484257, 80555820, 8632145, 3925993, 81317956, 12645616, 23438120, 48241610, 20578077, 75133501, 46214776, 35621790, 15258257, 20145132, 32680983, 94521866, 43456056, 19341117, 29693292, 38935734, 62721977, 31340268, 91841822, 22303667, 96935307, 29160182, 61869130, 33436979, 32438444, 87945655, 43629909, 88918708, 85650550, 4201421, 11958347, 74203607, 37964292, 56174257, 20894491, 33858970, 45292153, 22249182, 77695201, 34240048, 36320401, 64890030, 81514017, 58983774, 88785054, 93832841, 12338671, 46297822, 26489779, 85959340 )));
	}
	
	
	@Test
	public void testMaxProfitBuyingSellingStocksOnce(){
		assertEquals(30 , maxProfitBuyingSellingStocksOnce(Arrays.asList(310,315,275,295,260,270,290,230,255,250)));
	}
	
	@Test
	public void testPascalTriangle(){
		assertEquals(new ArrayList<ArrayList<Integer>>(){{add(new ArrayList<Integer>(Arrays.asList(1)));add(new ArrayList<Integer>(Arrays.asList(1,1)));add(new ArrayList<Integer>(Arrays.asList(1,2,1)));add(new ArrayList<Integer>(Arrays.asList(1,3,3,1)));add(new ArrayList<Integer>(Arrays.asList(1,4,6,4,1)));}}
				,generate(5));
	}
	
	@Test
	public void testLargestNumber(){
		assertEquals("9534330", largestNumber(Arrays.asList(3, 30, 34, 5, 9)));
	}
	
	@Test
	public void testSieveOfErastothenes(){
		assertEquals(Arrays.asList(2,3,5,7,11,13,17),getAllPrimesSmallerThan(18));
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
