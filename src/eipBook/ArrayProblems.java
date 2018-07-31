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
	
	  public ArrayList<Integer> spiralOrder(final List<ArrayList<Integer>> xs) {
		  
		   int m = xs.size(), n= xs.get(0).size();
		   ArrayList<Integer> res = new ArrayList<Integer>();
		   int currx = 0, curry = 0;
		   while(m>0 && n>0){
              for(int i=0;i<n;i++){
            	  res.add(xs.get(currx).get(curry++));
              }
              curry--;
              currx++;
              for(int i=0;i<m-1 ;i++,currx++){
            	  res.add(xs.get(currx).get(curry));
              }
              currx--;
              curry--;
              for(int i=0;i<n-1 ;i++,curry--){
            	  res.add(xs.get(currx).get(curry));
              }
              curry++;
              currx--;
              for(int i=0;i<m-2 && curry != n-1 ;i++,currx--){
            	  res.add(xs.get(currx).get(curry));
              }
              currx++;
              m -=2;
              n -=2;
              curry++;              
		   }
	         // Populate result;
	         return res;
	    }
	  
	  
	  
	  
	  public ArrayList<Integer> spiralOrder1(final List<ArrayList<Integer>> xs) {
          
          int fx=0,m = xs.size()-1, fy=0, n= xs.get(0).size()-1;
          ArrayList<Integer> res = new ArrayList<Integer>();
         
         while(fx<=m && fy<=n){
             
             for(int i=fy;i<=n;i++){
                 res.add(xs.get(fx).get(i));
             }
             fx++;
             for(int i=fx;i<=m;i++){
                 res.add(xs.get(i).get(n));
             }
             n--;
             
             if(fy <= n && fx<=m){
                for(int i=n;i>=fy;i--){
                    System.out.println("Adding --> "+xs.get(m).get(i));
                 res.add(xs.get(m).get(i));
             }
             m--;  
             
             }
             
             if( fx <= m && fy<=n){
                 for(int i=m;i>=fx;i--){
                 res.add(xs.get(i).get(fy));
             }
             fy++; 
             }
             
             
             
         }
         return res;
	  }
	  
	    public int firstMissingPositive(ArrayList<Integer> xs) {
	        int sa=0, min = 1 , max=1, cnt = 0 , n =xs.size() ;
	        Integer[] xsArr = xs.toArray(new Integer[0]);
	        for(int i=n-1;i>=0;i--){
	            if((1<=xsArr[i] && xsArr[i]<=n) && ((i+1) != xsArr[i] )){
	                 int tmp = xsArr[xsArr[i]-1];
	                 
	                 
	                 xsArr[xsArr[i]-1] = xsArr[i];
	                 
	                 while(1<=tmp && tmp<=n){
	                	 if(tmp == i+1 || (tmp<1 || tmp>n)){
		                	 
		                	 xsArr[i]=tmp;
		                	 tmp = -1;
		                 }else{
		                	 int tmp1 = xsArr[tmp-1];
		                	 xsArr[tmp-1] = tmp;
		                	 tmp = tmp1;
		                 }
	                 }
	                
	            }
	        }
	        
	        for(;min<n+1 && xsArr[min-1] == min ; min++ );
	        
	        return min;
	    
	}
	
	//tests
	
	@Test
	public void testMaximumGap(){
		assertEquals(2, maximumGap(Arrays.asList(3,5,4,2)));
		assertEquals(4, maximumGap(Arrays.asList(3,5,4,2,7)));
		assertEquals(0, maximumGap(Arrays.asList(5,4,3,2)));
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
	
	@Test
	public void testSpiralOrder(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 6, 9, 8, 7, 4, 5)),spiralOrder1(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1,2,3)),new ArrayList<Integer>(Arrays.asList(4,5,6)),new ArrayList<Integer>(Arrays.asList(7,8,9)))));
	    assertEquals(new ArrayList<Integer>(Arrays.asList(1, 2, 3)),spiralOrder1(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1)),new ArrayList<Integer>(Arrays.asList(2)),new ArrayList<Integer>(Arrays.asList(3)))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(1, 2, 3,6,5,4)),spiralOrder1(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1,2,3)),new ArrayList<Integer>(Arrays.asList(4,5,6)))));
	}
	
	@Test
	public void testFirstMissingPositiveInteger(){
		/*assertEquals(3,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(1,2,0))));
		assertEquals(2,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(3,4,-1,1))));
		assertEquals(1,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(-1,-9,0))));
		assertEquals(2,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(1,9,10))));
		assertEquals(2,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(9,1,10))));
		assertEquals(2,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(9,10,1))));
		assertEquals(2,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(3,1,4))));*/
		assertEquals(3,firstMissingPositive(new ArrayList<Integer>(Arrays.asList(4,1,2))));
		assertEquals(2,firstMissingPositive(new ArrayList<Integer>(Arrays.asList( 110, 483, 137, 881, 946, 231, 378, 449, 68, 518, 476, 898, 685, 384, 839, 553, 304, 689, 467, 292, 414, 679, 301, 30, 457, 300, 93, 427, 619, 439, 4, 348, 714, 966, 142, 538, 484, 827, 237, 447, 442, 133, 771, 578, 190, 782, 182, 23, 192, 177, 164, 276, 273, 45, 624, 12, 354, 938, 472, 899, 42, 612, 813, 386, 380, 63, 136, 589, 334, 349, 757, 999, 526, 156, 803, 991, 961, 535, 297, 336, 409, 59, 558, 678, 755, 720, 88, 906, 571, 770, 673, 581, 399, 868, 446, 123, 707, 643, 548, 226, 854, 830, 423, 621, 14, 767, 801, 651, 932, 492, 462, 321, 250, 591, 208, 862, 413, 418, 724, 746, 388, 925, 882, 808, 543, 171, 737, 994, 283, 950, 528, 443, 853, 671, 699, 19, 344, 365, 134, 818, 531, 48, 464, 124, 377, 379, 792, 387, 11, 796, 159, 65, 842, 109, 631, 600, 987, -3, 263, 28, 833, 779, 100, 73, 790, 179, 889, 557, 887, 455, 502, 511, 907, 686, 713, 864, 271, 372, 850, 534, 347, 649, 21, 169, 712, 435, 1, 222, 509, 866, 628, 604, 885, 385, 896, 498, 964, 598, 508, 575, 893, 294, 520, 918, 729, 789, 772, 660, 87, 569, 677, 602, 962, 217, 69, 648, 72, 331, 749, 637, 180, 489, 733, 481, 393, 40, 274, 320, 113, 930, 633, 516, 878, 835, 754, 126, 15, 593, 338, 494, 972, 603, 34, 739, 947, 592, 886, 583, 127, 172, 675, 975, 232, 394, 763, 92, 512, 616, 983, 856, 955, 166, 157, 608, 218, 56, 66, 696, 310, 933, 185, 563, 647, 960, 323, 727, 471, 397, 367, 582, 223, 369, 759, 706, 35, 588, 478, 317, 564, 768, 904, 149, 202, 165, 695, 985, 731, 486, 312, 561, 466, 197, 245, 980, 979, 620, 774, 438, 193, 146, 653, 468, 963, 485, 178, 29, 434, 272, 296, 700, 549, 181, 586, 595, 410, 108, 91, 640, 175, 750, 490, 20, 101, 879, 74, 798, 910, 611, 187, 286, 997, 138, 488, 901, 154, 267, 403, 122, 572, 787, 942, 391, 525, 176, 383, 1000, 458, 24, 780, 691, 738, 151, 170, 351, 441, 776, 453, 590, 545, 139, 859, 461, 970, 491, 785, 969, 670, 570, 219, 99, 353, 723, 470, 716, 249, 115, 680, 501, 128, 851, 9, 120, 256, 820, 114, 416, 329))));

	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
