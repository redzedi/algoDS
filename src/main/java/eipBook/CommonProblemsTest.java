package eipBook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class CommonProblemsTest {

	public static class MyTuple<K, V> {
		public final K k;
		public V v;

		public MyTuple(K k, V v) {
			this.k = k;
			this.v = v;
		}

		@Override
		public String toString() {
			return v + "" + k;
		}

	}

	// 8.10

	public static <K> List<K> evenOddMerge(List<K> ips) {

		return null;
	}

	// 7.8 Look-And-Say series - 〈1, 11, 21, 1211, 111221,
	// 312211,13112221,1113213211〉.

	public static String getNthLookAndSaySeriesTerm(int n) {

		return getNthLookAndSaySeriesTermInner("1", n);
	}

	public static String getNthLookAndSaySeriesTermInner(String currTerm, int n) {

		if (n == 1) {
			return currTerm;
		} else {

			LinkedList<MyTuple<Character, Integer>> charsToCount = new LinkedList<MyTuple<Character, Integer>>();

			for (Character currDigit : currTerm.toCharArray()) {
				if (!charsToCount.isEmpty()
						&& charsToCount.peek().k.equals(currDigit)) {
					charsToCount.peek().v = charsToCount.peek().v + 1;
				} else {
					MyTuple<Character, Integer> e = new MyTuple<>(currDigit, 1);
					charsToCount.push(e);
				}
			}
			StringBuilder nextTerm = new StringBuilder();
			Iterator<MyTuple<Character, Integer>> tupleItr = charsToCount
					.descendingIterator();

			while (tupleItr.hasNext()) {
				nextTerm.append(tupleItr.next().toString());
			}

			/*
			 * for(MyTuple<Character,Integer>
			 * currTuple:charsToCount.descendingIterator()){
			 * nextTerm.append(currTuple.toString()); }
			 */

			return getNthLookAndSaySeriesTermInner(nextTerm.toString(), --n);
		}

		// return null;
	}

	// 7.7 - Phone Mnemonic problem
	private static final String[] DEFAULT_PHONE_KEYPAD_MAP = new String[] {
			"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ" };

	public static List<String> phoneNumberToMnemonic(String phoneNum) {
		if (phoneNum.isEmpty()) {
			ArrayList<String> res = new ArrayList<String>();
			res.add("");
			return res;
		} else {
			int currDigit = Integer.parseInt(phoneNum.substring(0, 1));
			String charsForDigit = DEFAULT_PHONE_KEYPAD_MAP[currDigit];

			List<String> restOfTheList = phoneNumberToMnemonic(phoneNum
					.substring(1));
			ArrayList<String> cloneRestOfTheList = new ArrayList<String>();
			for (char currChar : charsForDigit.toCharArray()) {
				for (int i = 0; i < restOfTheList.size(); i++) {
					cloneRestOfTheList.add(i, currChar + restOfTheList.get(i));
				}
			}
			return cloneRestOfTheList;
		}
	}

	// assumption1: no -ve are present
	// 6.5
	public static int[] removeDuplicatesFromArray(int[] ipArr) {
		int[] opArr = null;
		if (ipArr != null) {
			int arrLen = ipArr.length;
			int replacementPos = 1;
			for (int i = 0; i < arrLen; i++) {
				if (ipArr[replacementPos] != ipArr[i]) {
					if (i > replacementPos) {
						ipArr[replacementPos++] = ipArr[i];
					} else if (i < replacementPos) {
						replacementPos++;
					}
				}
				/*
				 * if(replacementPos == -1 && ipArr[i] == ipArr[i+1]){
				 * 
				 * replacementPos = i+1; }else if(replacementPos != -1 && i !=
				 * replacementPos && ipArr[replacementPos] != ipArr[i] ){
				 * ipArr[replacementPos++] = ipArr[i]; }
				 */
			}
			opArr = new int[replacementPos];
			System.arraycopy(ipArr, 0, opArr, 0,
					replacementPos == -1 ? ipArr.length : replacementPos);
		}

		return opArr;
	}

	public static void rotate(ArrayList<ArrayList<Integer>> a) {
		int maxRows = a.size();
		if (maxRows == 0) {
			return;
		}
		int outerLimitMax = maxRows / 2;
		maxRows--;
		for (int i = 0; i < outerLimitMax; i++) {
			for (int j = i; j < maxRows - i; j++) {

				int tmp1 = a.get(i).get(j);
				int tmp2 = a.get(j).get(maxRows - i);
				a.get(j).set(maxRows - i, tmp1);
				System.out.println("replacint " + tmp1 + " with " + tmp2);

				int tmp3 = a.get(maxRows - i).get(maxRows - j);
				a.get(maxRows - i).set(maxRows - j, tmp2);

				int tmp4 = a.get(maxRows - j).get(i);
				a.get(maxRows - j).set(i, tmp3);

				a.get(i).set(j, tmp4);
			}
			System.out.println("array at this point --> ");
			System.out.println(a);
		}

	}

	public ArrayList<ArrayList<Integer>> prettyPrint(int a) {

		int n = a;
		int outerLimit = n / 2;

		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < n; i++) {
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for (int j = 0; j < n; j++) {
				tmp.add(-1);
			}
			// java.util.Collections.fill(tmp,-1);
			res.add(tmp);
		}

		for (int i = 0; i < outerLimit; i++) {
			for (int j = i; j < outerLimit - i; j++) {
				res.get(i).set(j, n - i);
				res.get(j).set(n - i, n - i);
				res.get(n - i).set(n - j, n - i);
				res.get(n - j).set(i, n - i);
			}
		}

		return res;
	}

	public static int sqrt(int a) {
		double low = 0, high = a, epsilon = 0.00001, mid = (low + high) / 2, prev = 0, diff1 = (mid * mid)
				- a;

		// 2nd term is toto make sure that upper bound is chosen
		while (Math.abs(diff1) > epsilon || diff1 < 0.0) {
			if (diff1 > 0.0) {

				System.out.println("Got high");
				high = mid;
			} else {
				low = mid;
			}
			mid = (low + high) / 2;
			diff1 = (mid * mid) - a;

			System.out.println("value of diff1 --> " + diff1);

		}
		System.out.println("value of mid --> " + mid);

		return (int) Math.floor(mid);

	}

	public static ArrayList<Integer> searchRange1(final List<Integer> a, int b) {
		int start = 0, end = a.size() - 1, mid = -1, startPos = -1, endPos = -1;
		ArrayList<Integer> res = new ArrayList<>();

		while (start < end) {
			mid = (start + end) / 2;
			// System.out.println(" start "+start +" mid "+mid+" end "+end);
			if (a.get(start) == b) {
				startPos = start;
				// endPos = start;
				start++;
				System.out.println("Matching at start !!");
				break;
			}/*
			 * else if(a.get(end) == b){ startPos = end; endPos = end; start =
			 * end+1; break;
			 * 
			 * }else if(a.get(mid) == b){ startPos = mid; endPos = mid; start =
			 * mid+1; System.out.println("Matching at mid !!"); break;
			 * 
			 * }
			 */

			if (a.get(mid) < b) {
				start = mid + 1;
			} else {
				end = mid;
			}
		}
		if (startPos == -1 && start < a.size() && a.get(start) == b) {
			startPos = start;
			endPos = start;
			start++;
			System.out.println("Matching at start !!");
		}
		System.out.println("Found startPos --> " + startPos);
		System.out.println("Found end --> " + end);
		if (startPos != -1) {
			end = a.size() - 1;

			// reverse search
			while (start < end) {
				System.out.println("Endpos :-  start " + start + " mid " + mid
						+ " end " + end);
				mid = (start + end) / 2;

				/*
				 * if(a.get(start) == b){ endPos = start; start++; break; }else
				 */if (a.get(end) == b) {
					System.out.println(" end matched " + end);
					endPos = end;
					start = end + 1;
					if (start < a.size()) {
						end = a.size() - 1;
					}
					continue;

				}/*
				 * else if(a.get(mid) == b){ endPos = mid; start = mid+1; break;
				 * 
				 * }
				 */

				if (a.get(mid) < b) {
					start = mid + 1;
				} else {
					end = mid;
				}
			}

			if (end < a.size() && a.get(end) == b) {
				endPos = end;
				System.out.println("Matching at end !!");
			}

			if (endPos == -1 && startPos != -1) {
				endPos = startPos;
			}

			System.out.println("Found endPos --> " + endPos);
		}

		res.add(startPos);
		res.add(endPos);
		return res;
	}

	/**
	 * 
	 * 1) Calculate the medians m1 and m2 of the input arrays ar1[] and ar2[]
	 * respectively. 2) If m1 and m2 both are equal then we are done. return m1
	 * (or m2) 3) If m1 is greater than m2, then median is present in one of the
	 * below two subarrays. a) From first element of ar1 to m1
	 * (ar1[0...|_n/2_|]) b) From m2 to last element of ar2 (ar2[|_n/2_|...n-1])
	 * 4) If m2 is greater than m1, then median is present in one of the below
	 * two subarrays. a) From m1 to last element of ar1 (ar1[|_n/2_|...n-1]) b)
	 * From first element of ar2 to m2 (ar2[0...|_n/2_|]) 5) Repeat the above
	 * process until size of both the subarrays becomes 2. 6) If size of the two
	 * arrays is 2 then use below formula to get the median. Median =
	 * (max(ar1[0], ar2[0]) + min(ar1[1], ar2[1]))/2
	 */

	public static double findMedianSortedArrays1(final List<Integer> a,
			final List<Integer> b) {

		int aMin = 0, aMax = a.size() - 1, bMin = 0, bMax = b.size() - 1;

		double m1 = -1.0, m2 = -1.0;

		while ((aMax - aMin) > 1 || (bMax - bMin) > 1) {
			if ((aMax - aMin) > 1) {
				// m1Idx = (aMax+aMin)%2 == 0?(aMax+aMin)/2:((aMax+aMin)/2 +
				// (aMax+aMin)/2 +1))/2;
				m1 = (aMax + aMin) % 2 == 0 ? a.get((aMax + aMin) / 2)
						: (a.get((aMax + aMin) / 2) + a
								.get((aMax + aMin) / 2 + 1)) / 2.0;
			}

			if ((bMax - bMin) > 1) {

				System.out.println("calculating m2 "
						+ (b.get((bMax + bMin) / 2) + b
								.get(((bMax + bMin) / 2) + 1)) / 2.0);
				System.out.println(String
						.format("calculating m2. [ bMin = %d , bMax = %d ]",
								bMin, bMax));
				System.out
						.println("calculating m2 " + ((bMax + bMin) % 2 == 0));
				m2 = (bMax + bMin) % 2 == 0 ? b.get((bMax + bMin) / 2) : ((b
						.get((bMax + bMin) / 2) + b
						.get(((bMax + bMin) / 2) + 1)) / 2.0);
			}
			System.out.println(String.format("m1= %f , m2 = %f ", m1, m2));
			if (m1 > m2) {
				if ((aMax - aMin) > 1) {

					aMax = (aMax + aMin) / 2;
				}

				if ((bMax - bMin) > 1) {

					bMin = (bMax + bMin) / 2;
				}

				System.out.println(String.format(
						"1. [ aMin = %d , aMax = %d ]", aMin, aMax));
				System.out.println(String.format(
						"1. [ bMin = %d , bMax = %d ]", bMin, bMax));
			} else {

				if ((aMax - aMin) > 1) {

					aMin = (aMax + aMin) / 2;
				}

				if ((bMax - bMin) > 1) {

					bMax = (bMax + bMin) / 2;
				}

				System.out.println(String.format(
						"2. [ aMin = %d , aMax = %d ]", aMin, aMax));
				System.out.println(String.format(
						"2. [ bMin = %d , bMax = %d ]", bMin, bMax));
			}
		}
		System.out.println(String.format("[ %d , %d ]", a.get(aMin),
				a.get(aMax)));
		System.out.println(String.format("[ %d , %d ]", b.get(bMin),
				b.get(bMax)));

		System.out.println(String.format("[ bMin = %d , bMax = %d ]", bMin,
				bMax));

		return (Math.max(a.size() == 0 ? -1 : a.get(aMin), b.size() == 0 ? -1
				: b.get(bMin)) + Math.min(
				a.size() == 0 ? Integer.MAX_VALUE : a.get(aMax),
				b.size() == 0 ? Integer.MAX_VALUE : b.get(bMax))) / 2.0;
	}

	public static double findMedianSortedArrays(final List<Integer> a,
			final List<Integer> b) {
		int aMin = 0, aMax = a.size() - 1, bMin = 0, bMax = b.size() - 1;
		double res = -1.0;
		while (!(aMax < aMin) && !(bMax < bMin)
				&& ((aMax - aMin) > 1 || (bMax - bMin) > 1)) {
			double[] m1 = getMedian(a, aMin, aMax);
			double[] m2 = getMedian(b, bMin, bMax);

			System.out.println("m1 --> " + Arrays.toString(m1));
			System.out.println("m2 --> " + Arrays.toString(m2));

			if (m1[2] == m2[2]) {
				return m1[2];
			} else if (m1[2] < m2[2]) {
				// if(aMin != m1[0])
				// aMin = (int)m1[0];
				// else
				// aMin = (int)m1[1];
				aMin = (int) m1[1];
				bMax = (int) m2[1];
			} else {
				aMax = (int) m1[1];

				// if(bMin != m2[0])
				// bMin = (int)m2[0];
				// else
				// bMin = (int)m2[1];
				bMin = (int) m2[1];
			}
		}

		if ((aMax < aMin)) {
			return getMedian(b, 0, b.size() - 1)[2];
		} else if ((bMax < bMin)) {
			return getMedian(a, 0, a.size() - 1)[2];
		} else {
			System.out.println(String.format("a = [ %d , %d ]", a.get(aMin),
					a.get(aMax)));
			System.out.println(String.format("b = [ %d , %d ]", b.get(bMin),
					b.get(bMax)));
			res = (Math.max(a.get(aMin), b.get(bMin)) + Math.min(a.get(aMax),
					b.get(bMax))) / 2.0;
		}

		return res;
	}

	private static double[] getMedian(List<Integer> as, int min, int max) {
		double avgIdx = (min + max) / 2.0;
		int flr = (int) Math.floor(avgIdx);
		int clng = (int) Math.ceil(avgIdx);
		return new double[] { flr, clng, (as.get(flr) + as.get(clng)) / 2.0 };
		// return flr == clng? new double[]{ flr,as.get(flr)}: new double[]
		// {clng,(as.get(flr)+as.get(clng))/2.0};

	}
	
	public static double findMedianSortedArrays2(final List<Integer> xs,
			final List<Integer> ys) {
		double xLo = 0 , xHi = xs.size()-1 ;
		double yLo = 0 , yHi = xs.size()-1 ;
		double res = 0;
		
		while(xLo<xHi && yLo<yHi) {
			double xMed = xHi%2==0?xs.get((int)xHi/2):(xs.get((int)xHi/2)+xs.get((int)(xHi/2)+1))/2;
			double  yMed = xHi%2==0?ys.get((int)yHi/2):(ys.get((int)yHi/2)+ys.get((int)(yHi/2)+1))/2;
			if(xMed == yMed) {
				res = xMed;
				break;
			}else if(xMed < yMed) {
				xLo = xHi/2;
				yHi = yHi/2;
			}else {
				xHi = xHi/2;
				yLo = yHi/2;
				
			}
		}
		return res;
		
			
		
		
	}

	// ((a % p)*(b % p)) % p = ab % p
	public static int pow(int x, int n, int d) {
		// System.out.println("x to the pow n "+(x^n));
		long quotient = 0, i = n;
		long res = 1L;
		long remainder = 0L;
		// BigInteger res = BigInteger.valueOf(1L);
		// BigInteger remainder = new BigInteger("0");
		/*
		 * while(x > d){ x=x-d; quotient++; }
		 */
		if (x < 0) {
			// remainder = BigInteger.valueOf(x+d);
			remainder = x + d;
		} else if (x > d) {
			// remainder = BigInteger.valueOf(x%d);
			remainder = x - d;
		} else {
			remainder = x;
		}
		// res = remainder ;

		while (n > 0) {
			if (n % 2 != 0) {
				res = (res * remainder) % d;
				// res = res.multiply(remainder).mod(BigInteger.valueOf(new
				// Long(d)));
			}
			remainder = (remainder * remainder) % d;
			// remainder =
			// remainder.multiply(remainder).mod(BigInteger.valueOf(d)) ;
			n /= 2;
			// System.out.println("n --> "+n+" remainder --> "+res+" res --> "+res);

		}

		// return res.mod(BigInteger.valueOf(new Long(d))).intValue();
		// return (int)res%d;
		return (int) res % d;
	}

	
	
	//binary search in a rotated sorted array
	public static int search(final List<Integer> a, int b) {
		int low=0 ,high=a.size()-1,mid=(low+high)/2, res=-1;
		while(low<=high){
			
			mid=(high+low)/2;

			if(a.get(mid) == b){
				res=mid;
				break;
			}
			
			if(high == low){
				break;
			}
			
			if(a.get(low)<a.get(mid)){
				//first half sorted
				if(b<=a.get(mid) && b>= a.get(low) )
				  high = mid-1;
				else
					low = mid+1;
					
			}else if(  a.get(mid) < a.get(high)){
				//second half sorted
				if(b >= a.get(mid) && b<= a.get(high) )
					low = mid+1;
				else
					high = mid-1;
			
			}else{
				System.out.println("should not come here low--> "+low+" high --> "+high);
			}
		}
		return res;
	}
	//binary search in a rotated sorted array
	public static int search1(final List<Integer> a, int b) {

		int pivot = findPivot(a);
		int res = -1;
		if (pivot > -1) {

			if ((a.get(0) <= b && b <= a.get(pivot - 1))
					|| (a.get(pivot - 1) <= b && b <= a.get(0))) {
				res = binarySearch(a, 0, pivot - 1, b);
			} else if ((a.get(pivot)) <= b && b <= a.get(a.size() - 1)
					|| (a.get(a.size() - 1) <= b && b <= a.get(pivot))) {
				res = binarySearch(a, pivot, a.size() - 1, b);
			}

		} else {
			res = binarySearch(a, 0, a.size(), b);
		}
		return res;
	}

	// [4 5 6 7 0 1 2] and target = 4
	/*
	 * public static int findPivot(List<Integer> xs){ int
	 * piv=-1,prev=Integer.MIN_VALUE; boolean isFirst=true, isAsc=false,
	 * isDesc=false; if(xs != null && xs.size()>1){ prev = xs.get(0); for(int
	 * i=1;i<xs.size();i++){ int curr = xs.get(i); if(i == 1){ if(curr>prev){
	 * isAsc=true; }else{ isDesc=true; } isFirst=false; }else{ if(curr>prev &&
	 * isDesc){ piv = i; break; }
	 * 
	 * if(curr<prev && isAsc){ piv = i; break; }
	 * 
	 * } prev = curr; } } return piv;
	 * 
	 * }
	 */

	public static int findPivot(List<Integer> xs) {
		int res = -1, start = 0, end = xs.size() - 1;
		// boolean isAsc=start==end || xs.get(start)<xs.get(start+1);

		while (start < end) {
			System.out.println("In findPivot start --> " + start + " end--> "
					+ end);
			if (((end - start) == 1) && xs.get(end) < xs.get(start)) {
				res = end;
				break;
			} else {

				int mid = (start + end) / 2;
				if (xs.get(mid) < xs.get(end)) {
					end = mid;
				} else {
					start = mid;
				}
			}

		}
		return res;
	}

	// public static int binarySearch(final List<Integer> a, int start , int
	// end, int q){
	// int res = -1;
	// System.out.println("start --> "+ start+" end "+end+
	// " (start < end) "+(start < end));
	// if(start < end){
	// if(a.get(start) == q){
	// res = start;
	//
	// }else{
	// int mid = (start+end)/2;
	// if (start != mid) {
	// if (q < a.get(mid)) {
	// res = binarySearch(a, start, mid, q);
	// } else {
	// res = binarySearch(a, mid, end, q);
	// }
	// }
	// }
	// }
	//
	// return res;
	// }

	public static int binarySearch(final List<Integer> a, int start, int end,
			int q) {
		int res = -1, myStart = start, myEnd = end;

		while (myStart < myEnd) {
			System.out.println("start --> " + myStart + " end " + myEnd
					+ " (start < end) " + (myStart < myEnd));
			if (a.get(myStart) == q) {
				res = myStart;
				break;
			} else {
				int mid = (myStart + myEnd) / 2;
				if (myStart != mid) {
					if (q < a.get(mid)) {
						myEnd = mid;
					} else {
						myStart = mid;
					}
				} else {
					break;
				}
			}
		}

		return res;
	}

	public static String reverseWords(String a) {
       String[] splitA = a.split(" ");
       boolean isEmpty = true;
       StringBuilder res = new StringBuilder();
       int numOfWords = splitA.length;
       for(int i=numOfWords-1;i>=0;i--){
    	   if(!" ".equals(splitA[i])){
    		   if(!isEmpty){
    			   res.append(" ");
    		   }
    		   res.append(splitA[i]);
    		   
    		   isEmpty = false;
    	   }
       }
       return res.toString();
	}
	
	public static enum RomanNumerals{
		M(1000,false) ,D(500,true) ,C(100,false) ,L(50,true) ,X(10,false) ,V(5,true) ,I(1,false) ;
		
		static{
          M.previousVal = C;
          D.previousVal = C;
          C.previousVal = X;
          L.previousVal = X;
          X.previousVal = I;
          V.previousVal = I;
		}
		
		
		private RomanNumerals(int val,boolean isFiver){
			this.intValue = val;
			this.isFiver = isFiver;
		}
		final private int intValue ;
		final private boolean isFiver;
		private RomanNumerals previousVal;
		
		void updateState(IntValFromRoman ctx) {
			if(ctx.currState == null || (!this.isFiver && ctx.currState == this) || (ctx.currState.ordinal() < this.ordinal()) ){
				//System.out.println("Adding value "+this.intValue+" "+this.name());
				ctx.totalVal += this.intValue;
			}else if(ctx.currState == this.previousVal){
				//System.out.println("Adding value1 "+(this.intValue - this.previousVal.intValue));
				ctx.totalVal += (this.intValue - this.previousVal.intValue);
				ctx.totalVal -=   this.previousVal.intValue;
			}else{
				throw new RuntimeException("transition not allowed");
			}
			ctx.currState=this;
			
		}
		
	}
	
	static class IntValFromRoman{
		int  totalVal=0;
		RomanNumerals currState;
		
		public void consumeSymbol(String sym){
			
				 RomanNumerals.valueOf(sym).updateState(this);;
			
		}
		
		
	}
	public static int romanToInt(String a) {
		
		String[] splitA = a.split("");
		int len = splitA.length;
		IntValFromRoman ctx = new IntValFromRoman();
		for(int i=1;i<len;i++){
			//System.out.println("value to consume "+splitA[i]+" "+Arrays.asList(splitA));
			ctx.consumeSymbol(splitA[i]);
		}
		
		return ctx.totalVal;
	}
	
	public static int atoi(final String a) {
		
		HashMap<String,Integer> strToInt = new HashMap<String,Integer>(){{put("0",0);put("1",1);put("2",2);put("3",3);put("4",4);put("5",5);put("6",6);put("7",7);put("8",8);put("9",9);}};
		List<String> words = Arrays.asList(a.split(" "));
		String strNum = null;
		int res = 0, mul = 1, decimalPlace = 0;
		for(String word:words){
			if(!" ".equals(word)){
				strNum = word;
				break;
			}
		}
		if(strNum != null){
			String[] digits = strNum.split("");
			int len = digits.length;
			System.out.println("digits "+Arrays.asList(digits));
			for(int i=len-1;i >= 0;i--){
				if(strToInt.containsKey(digits[i])){
					try {
						if(res != Integer.MAX_VALUE)
						res = Math.addExact(res,(int)(strToInt.get(digits[i])*Math.pow(10, decimalPlace++)));
					} catch (ArithmeticException e) {
						res = Integer.MAX_VALUE;
					}
					//res += strToInt.get(digits[i])*Math.pow(10, decimalPlace++);
				}else if("-".equals(digits[i]) && i==0){
					mul = -1;
				}else if("+".equals(digits[i]) && i==0){
					mul = 1;
				}else{
					System.out.println("reset ");
					res = 0;
					decimalPlace=0;
				}
				
				System.out.println("res --> "+res);
			}
			
		}
		return mul==-1 && res==Integer.MAX_VALUE?Integer.MIN_VALUE:res*mul;
		
		
	}
	
	 public int kthsmallest(final List<Integer> A, int B) {
	
		 java.util.PriorityQueue<Integer> maxHeap = new java.util.PriorityQueue<Integer>(new java.util.Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2-o1;
			}
			 
		 });
		 
		 for(Integer curr:A){
			 if(maxHeap.size() < B){
				 maxHeap.offer(curr);
			 }else{
				 if(curr < maxHeap.peek() ){
					 maxHeap.poll();
					 maxHeap.offer(curr);
				 }
			 }
		 }
		  
		 return maxHeap.poll();
		 
	    }
	 
	 public ArrayList<Integer> nextGreater(ArrayList<Integer> A) {
		 
		 Integer[] ret = new Integer[A.size()];
         java.util.Arrays.fill(ret, -1);
		 ArrayDeque<Integer[]> stk = new ArrayDeque<Integer[]>();
		 
		 stk.offerFirst(new Integer[]{A.get(0),0});
		 for(int i=1;i<A.size();i++){
			 int next = A.get(i);
			 
			 
				 while(!stk.isEmpty() && next > stk.peekFirst()[0]) {
					 ret[stk.peekFirst()[1]] = next;
					 stk.pollFirst();
				 }
				 
			 
			 stk.offerFirst(new Integer[]{next,i});
		 }
//		 for(int j=ret.size();j<A.size();j++){
//			 ret.add(-1);
//		 }
		return new ArrayList<Integer>(Arrays.asList(ret)); 
		 
	    }
	
	//intersection of sorted arrays - multiple pointers problems
	public static ArrayList<Integer> intersect(final List<Integer> A, final List<Integer> B) {
		int loopSize =  A.size()+B.size() , aPtr = 0, bPtr = 0;
		ArrayList<Integer> res = new ArrayList<Integer>();
		//System.out.println("A.size() --> "+A.size()+" B.size() "+B.size());
		for(int i=0;i < loopSize && (aPtr<A.size() && bPtr<B.size()); i++){
			//System.out.println(i+". A --> "+A.get(aPtr)+" B --> "+B.get(bPtr));
			if(A.get(aPtr).equals(B.get(bPtr)) ){
				res.add(A.get(aPtr));
				
					aPtr++;
					bPtr++;
			   	
			}else if( A.get(aPtr) < B.get(bPtr)){
					aPtr++;
				
			}else{
					bPtr++;
			}
		}
		
		return res;
    }
	
	//removing duplicates in a sorted array - multiple pointers problem
	public int removeDuplicates(ArrayList<Integer> a) {
		
		int prev=0,curr=1, n = 1 ;
		
		while(curr < a.size()){
			n++;
			if(a.get(prev).equals(a.get(curr))){
				//a.remove(curr);
				curr++;
				//a.add(curr, a.remove(curr+1));
			}else{
				if(curr-prev>1){
					a.set(prev+1,a.get(curr));
				}
				prev++;
				curr++;
				
			}
		}
		for(int i=a.size()-1; i > prev;i--){
			a.remove(i);
		}
		//System.out.println("Number of comparisons --> "+n);
		return a.size();
	}
	
	/**
	 * A : [ 1, 4, 5, 8, 10 ]
	 * B : [ 6, 9, 15 ] 
	 * C : [ 2, 3, 6, 6 ]
	 * 
	 * We get the minimum difference for a=5, b=6, c=6 as | max(a,b,c) - min(a,b,c) | = |6-5| = 1.
	 */
	
	 public int solve(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
		 
		 int res = Integer.MAX_VALUE, minIdx = -1 , maxIdx = -1, prevMinIdx = -1, prevMinArrIdx = -1 , minArrIdx=-1;
		 List<ArrayList<Integer>> arrs = Arrays.asList(A,B,C); 
		 int[][] arrCntr = new int[][]{new int[]{A.size()-1,0}, new int[]{B.size()-1,0},new int[]{C.size()-1,0}};
		 
		 do{
			 prevMinIdx = minIdx;
			 minIdx = getMinIdx( A, B,  C, arrCntr[0][1], arrCntr[1][1], arrCntr[2][1]);
			 maxIdx = getMaxIdx( A, B,  C, arrCntr[0][1], arrCntr[1][1], arrCntr[2][1]);
			 int currDiff = ( arrs.get(maxIdx).get(arrCntr[maxIdx][1]) - arrs.get(minIdx).get(arrCntr[minIdx][1]));
			 res = currDiff < res ? currDiff:res;
			 
			 prevMinArrIdx = arrCntr[minIdx][1];
			 arrCntr[minIdx][1] = arrCntr[minIdx][1] < arrCntr[minIdx][0] ?arrCntr[minIdx][1]+1:arrCntr[minIdx][1]; 
			 
//			 System.out.println(prevMinArrIdx);
//			 System.out.println(arrCntr[minIdx][1]);
//			 System.out.println(prevMinIdx);
//			 System.out.println(minIdx);
//			 System.out.println("___________________________");
		 }while(!( (prevMinIdx == minIdx) && (prevMinArrIdx ==arrCntr[minIdx][1])));
		 
		 return res;
	    }
	 
	 private int getMinIdx(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C, int aIdx, int bIdx, int cIdx){
		 int abMinIdx = A.get(aIdx) < B.get(bIdx) ? 0:1;
		 int abMin = A.get(aIdx) < B.get(bIdx) ? A.get(aIdx):B.get(bIdx);
		 return C.get(cIdx) < abMin? 2: abMinIdx ;
	 }
	 
	 private int getMaxIdx(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C, int aIdx, int bIdx, int cIdx){
		 int abMinIdx = A.get(aIdx) > B.get(bIdx) ? 0:1;
		 int abMin = A.get(aIdx) > B.get(bIdx) ? A.get(aIdx):B.get(bIdx);
		 return C.get(cIdx) > abMin? 2: abMinIdx ;
	 }
	 
	
	 
	 /*
	  * Find i, j, k such that :
     * max(abs(A[i] - B[j]), abs(B[j] - C[k]), abs(C[k] - A[i])) is minimized.
     * Return the minimum max(abs(A[i] - B[j]), abs(B[j] - C[k]), abs(C[k] - A[i]))
	  */
	 public int minimize(final List<Integer> A, final List<Integer> B, final List<Integer> C) {
		 int currMaxIdx = 0, prevMaxIdx = -1, prevArrIdx = -1,  res = Integer.MAX_VALUE;
		 
		 int[][] arrIdx = new int[][]{ new int[]{A.size()-1, 0}, new int[]{B.size()-1,0}, new int[]{C.size()-1,0}};
		 
		 do {
			int diffA = Math.abs(A.get(arrIdx[0][1]) - B.get(arrIdx[1][1]));
			int diffB = Math.abs(B.get(arrIdx[1][1]) - C.get(arrIdx[2][1]));
			int diffC = Math.abs(C.get(arrIdx[2][1]) - A.get(arrIdx[0][1]));
			int currMax = Math.max(diffA, diffB) == diffA ? (Math.max(diffA,
					diffC) == diffA ? diffA : diffC)
					: (Math.max(diffB, diffC) == diffB ? diffB : diffC);
			
			prevMaxIdx = currMaxIdx;
			int currMaxDiffIdx = Math.max(diffA, diffB) == diffA ? (Math.max(diffA,
					diffC) == diffA ? 0 : 2)
					: (Math.max(diffB, diffC) == diffB ? 1 : 2);
			switch(currMaxDiffIdx){
			case 0:
				currMaxIdx = A.get(arrIdx[0][1])<B.get(arrIdx[1][1])?0:1;
				break;
			case 1:
				currMaxIdx = B.get(arrIdx[1][1])<C.get(arrIdx[2][1])?1:2;
				break;
			case 2:
				currMaxIdx = C.get(arrIdx[2][1])<A.get(arrIdx[0][1])?2:0;
				break;
			}
			prevArrIdx = arrIdx[currMaxIdx][1];
			arrIdx[currMaxIdx][1] = (arrIdx[currMaxIdx][1] < arrIdx[currMaxIdx][0]) ? arrIdx[currMaxIdx][1] + 1
					: arrIdx[currMaxIdx][1];
			//System.out.println(currMax);
			res = currMax < res ? currMax : res;
		} while (!(currMaxIdx == prevMaxIdx && prevArrIdx == arrIdx[currMaxIdx][1]) );
		return res;
	    }
	 
	 
	 public int longestConsecutive(final List<Integer> A) {
	    
		 java.util.HashSet<Integer> hsh = new java.util.HashSet<Integer>(A);
		 int res = -1;
		 for(Integer curr:A){
			 if(!hsh.contains(curr-1)){
				 int j = curr;
				 for(;hsh.contains(j);j++);
				 if((j-curr)>res){
					 res = (j-curr);
				 }
			 }
		 }
		 
		 return res;
		
	}
	 
	 /*
	  * iven an array of integers, find two numbers such that they add up to a specific target number.

The function twoSum should return indices of the two numbers such that they add up to the target, where index1 < index2. Please note that your returned answers (both index1 and index2 ) are not zero-based. 
Put both these numbers in order in an array and return the array from your function ( Looking at the function signature will make things clearer ). Note that, if no pair exists, return empty list.

If multiple solutions exist, output the one where index2 is minimum. If there are multiple solutions with the minimum index2, choose the one with minimum index1 out of them.
	  */
	 public ArrayList<Integer> twoSum(final List<Integer> xs, int tgt) {
		 HashMap<Integer,ArrayList<Integer>> int2Idxs = new HashMap<Integer,ArrayList<Integer>>();
		 int idx1 = -1 ,idx2 = -1;
		 //Collections
		 
		 for(int i=0;i<xs.size();i++){
			 if(int2Idxs.containsKey(xs.get(i))){
				 int2Idxs.get(xs.get(i)).add(i);
			 }else{
				 int2Idxs.put(xs.get(i), new ArrayList<Integer>(Arrays.asList(i)));
			 }
		 }
		 
		 for(int i=0;i<xs.size() ;i++){
			 if(int2Idxs.containsKey(tgt-xs.get(i)) && i < int2Idxs.get(tgt-xs.get(i)).get(0) && (idx2==-1 || int2Idxs.get(tgt-xs.get(i)).get(0) < idx2)){
				 idx1 = i;
				 idx2 = int2Idxs.get(tgt-xs.get(i)).get(0);
				// break;
				 
			 }else  if(int2Idxs.containsKey(tgt-xs.get(i)) && i == int2Idxs.get(tgt-xs.get(i)).get(0) && int2Idxs.get(tgt-xs.get(i)).size() > 1 && (idx2==-1 || int2Idxs.get(tgt-xs.get(i)).get(1) < idx2)){
				 idx1 = i;
				 idx2 = int2Idxs.get(tgt-xs.get(i)).get(1);
				// break;
				 
			 }
		 }
		 
		 return idx1==-1? new ArrayList<Integer>():new ArrayList<Integer>(Arrays.asList(idx1+1,idx2+1));
		 
		 
	    }
	 
		public ArrayList<Integer> repeatedNumber(final List<Integer> a) {
		    long n = a.size(), repeat=-1 , absent = -1, s1= 0, s1_2=0, s = (n*(n+1))/2, s_2 = (n*(n+1)*(2*n+1))/6 ;
		    
		    //java.util.BitSet bits = new  java.util.BitSet(n);
		  
		    for(int i=0;i<n;i++){
		        
		        s1 += a.get(i);
		        //s1_2 += a.get(i)*a.get(i);
		        s_2 -= a.get(i)*a.get(i);
		      
		    }
		    
		    long s_MINUS_s1 = s - s1; //a-r
		    //long s_2_MINUS_s1_2 = s_2 - s1_2;//a^2 - r^2
		    long s_2_MINUS_s1_2 = s_2 ;
		    
		    absent = ((s_2_MINUS_s1_2/s_MINUS_s1) + s_MINUS_s1)/2;
		    
		    repeat = absent - s_MINUS_s1;
		    //absent = bits.nextClearBit(0)+1;
		    
		    //System.out.println("absent --> "+absent);
		    
		    //repeat = s1-s+absent;
		    ArrayList<Integer> res = new ArrayList<Integer>();
		    res.add((int)repeat);
		    res.add((int)absent);
		    return res;
		    
		}
	 
	 /*
	  * Given a string s, partition s such that every string of the partition is a palindrome.

Return all possible palindrome partitioning of s.

For example, given s = "aab",
Return

  [
    ["a","a","b"]
    ["aa","b"],
  ]
	  */
	 
	   //palindromic decompositions
		public ArrayList<ArrayList<String>> partition(String a) {
			System.out.println("whole string --> "+a);
			ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
			for(int i=0;i<a.length();i++){
				String prefix = a.substring(0, i+1);
				if(isPalindrome(prefix)){
					System.out.println("palindromic prefix -- "+prefix);
					ArrayList<ArrayList<String>> suffixMatches = partition(a.substring(i+1));
					if(suffixMatches.isEmpty()){
						res.add(new ArrayList<String>(Arrays.asList(prefix)));
					}else{
						
						for(ArrayList<String> suffixMatchLst:suffixMatches){
							suffixMatchLst.add(0, prefix);
							res.add(suffixMatchLst);
						}
					}
				}
			}
			return res;
		}
		
		private boolean isPalindrome(String str){
			boolean isPalindrome = true;
			for(int i=0,j=str.length()-1;i<j;i++,j--){
				if(str.charAt(i) != str.charAt(j) ){
					isPalindrome = false;
					break;
				}
			}
			return isPalindrome;
		}
		
		//Longest palindromic substring 
		//i/p:"aaaabaaa" o/p: "aaabaaa"
		public String longestPalindrome(String str) {
			if(str.isEmpty()){
				return "";
			}
			String res = "";
			int maxLength = Integer.MIN_VALUE;
			int n = str.length();
			for(int i=0;i<n;i++){
			   //	int l=i-1,r=i+1;
			   	int l=i,r=i+1;
			   	
			   	for(;r<n && str.charAt(l) == str.charAt(r);r++);
			   	l--;
			   	
			   	for(;l>=0 && r<n && str.charAt(l) == str.charAt(r);l--,r++);
			   	
			   	l++;
			   	r--;
			   	
			   	
			   if(r-l > maxLength){
				   maxLength = r-l;
				   res = str.substring(l,r+1);
			   }
			}
			return res;
	    }
		
//		private ArrayList<String> palidromicPartition(String a , int startIdx, int endIdx){
//			a.su
//			for(int i=startIdx)
//		}
	 
	 
	 //list and stacks
	 static class ListNode {
		      public int val;
		      public ListNode next;
		      ListNode(int x) { val = x; next = null; }
		  }
	 
	 /*
	  *  L: L0 → L1 → … → Ln-1 → Ln,
reorder it to:

    L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
	  */
	 public ListNode reorderList(ListNode A) {
		 ListNode slow = null , fast = A, prevSlow = null;
		 ListNode resHead = new ListNode(-1);
		 resHead.next = A;
		 int halfLen = 0;
		 //1. get the midpoint
		 while(fast != null && fast.next != null){
			 fast = fast.next.next;
			 //prevSlow = slow;
			 //slow = slow.next;
			 slow = (slow==null)?A:slow.next;
			 halfLen++;
		 }
		 //2. bisect the list into 2 sublists
//		 if(prevSlow != null)
//		   prevSlow.next = null;
		 //3. reverse the 2nd half
		 ListNode secondHPrev = null;
		 ListNode secondHCurr = (slow != null)?slow.next:null;
		 
		 if(slow != null){
			 slow.next = null;
		 }
		 
		 while (secondHCurr != null) {
			ListNode tmp = secondHCurr.next;
			secondHCurr.next = secondHPrev;
			secondHPrev = secondHCurr;
			secondHCurr = tmp;
		}
		 
		 //merge
		 ListNode prevA = null;
		 while(A != null && secondHPrev != null){
			 ListNode tmp = A.next;
			 System.out.println("secondHPrev --> "+secondHPrev.val);
			 A.next = secondHPrev;
			 secondHPrev = secondHPrev.next;
			 if(tmp != null)
			 A.next.next = tmp;
			 prevA = A;
			 A = tmp;
		 }
		 System.out.println(" lastNode of A --> "+((prevA != null)?prevA.val:null));
		 System.out.println(" resHead.next  --> "+resHead.next);
		 System.out.println(" resHead.next.next  --> "+resHead.next.next);
		 
		 return resHead.next;
	    }
	 
		public ListNode detectCycle(ListNode a) {
			 ListNode slow = null , fast = a, res = null;
			 boolean hasCycle = true;
			 //1. get the midpoint
			 while((fast != null && fast.next != null) && (fast != slow)){
				 fast = fast.next.next;
				 slow = (slow==null)?a:slow.next;
				 System.out.println("fast --> "+fast.val+" slow--> "+slow.val);
			 }
			 
			 
			 if(fast == slow){
				 //has cycle
				 //find the cycle length
				 System.out.println("Has cycle");
				 int cycleLen = 1;
				 for(ListNode curr = slow.next; curr != slow; curr=curr.next,cycleLen++);
				 
				 System.out.println("Cycle length is --> "+cycleLen);
				 
				 //now find the starting of the cycle
				 ListNode cycleSpotterSlow = a;
				 ListNode cycleSpotterFast = a;
				 for(int i=0;i<cycleLen;i++){
					 cycleSpotterFast = cycleSpotterFast.next;
				 }
				 System.out.println("Starting cycle start detection ");
				 
				 for(;cycleSpotterFast != cycleSpotterSlow; cycleSpotterFast=cycleSpotterFast.next, cycleSpotterSlow=cycleSpotterSlow.next);
				 
				 res = new ListNode(cycleSpotterSlow.val);
				 System.out.println("returning res "+res.val);
				 
			 }
			 return res;
		}
	
		//in nlogn time and constant space
		 public ListNode sortList(ListNode A) {
			 int lenA = 0;
			 for(ListNode curr=A;curr != null; lenA++,curr = curr.next);
			 
			 return mergeSortSinglyLinkedList(A,lenA);
			  
		    }
		 
		 //in-place sort
		 private ListNode mergeSortSinglyLinkedList(ListNode hd , long len){
			 System.out.println("Merge sort called with listNode --> "+((hd!=null)?hd.val:null)+" with length "+len);
			 if(len == 1){
                 hd.next = null;				 
				 return hd;
			 }
			 ListNode rightHd = hd;
			 for(int i=0;i < len/2;i++,rightHd = rightHd.next);
			 
			ListNode sortedLeftList =  mergeSortSinglyLinkedList(hd, len/2 );
			ListNode sortedRightList =  mergeSortSinglyLinkedList(rightHd, Math.round(len/2.0)  );
			 
			 return mergeSinglyLinkedLists(sortedLeftList ,sortedRightList);
		 }
		 
		 private ListNode mergeSinglyLinkedLists(ListNode lt , ListNode rt){
			 ListNode resHead = null, resTail = null;
			 
			 System.out.println("merging lists:: -->  "+listFromListNode(lt)+" ---  "+listFromListNode(rt));
			 while((lt != null) && (rt != null) ){
				 if(lt.val < rt.val){
					 if(resHead == null){
						 resHead = lt;
						 resTail = lt;
					 }
					 
                     ListNode currHd = lt;
					 ListNode currTail = rt;
					 
					 lt = lt.next;
					 
					 currHd.next = currTail;
					 
					 resTail.next = currHd;
					 resTail = resTail.next;
//					 }
				 }else{
					 if(resHead == null){
						 resHead = rt;
						 resTail = rt;
					 }
					 
                     ListNode currHd = rt;
					 ListNode currTail = lt;
					 
					 rt = rt.next;
					 
					 currHd.next = currTail;
					 
					 resTail.next = currHd;
					 resTail = resTail.next;
				 }
			 }
			 
			 if(lt != null){
				 resTail.next = lt;
			 }else if(rt != null){
				 resTail.next = rt;
			 }
			 System.out.println("lt --> "+lt+" rt --> "+rt);
			 System.out.println("merged list --> "+listFromListNode(resHead));
			 return resHead;
			 
		 }
		 
		 
		 public ArrayList<Integer> slidingMaximum(final List<Integer> A, int B) {
			 MyQueueMax<Integer> window = new MyQueueMax<Integer>(B);
			 ArrayList<Integer> res = new ArrayList<Integer>();
			 int sz = A.size();
			 for(int i=0;i<sz;i++){
				 
				 if(i >= B){
					 window.removeFront();
				 }
				 window.insertRear(A.get(i));
				 if(i>=(B-1)){
					 
					 res.add(window.getMax());
				 }
				 
			 }
			 if(sz<B){
				 res.add(window.getMax()); 
			 }
			 return res;
		    }
		 
		 private static  class MyQueueMax<K extends Comparable> extends MyDequeue<K>{
			 
			 private MyDequeue<K> aux;

			public MyQueueMax(int capacity) {
				super(capacity);
				aux = new MyDequeue<K>(capacity);
			}
			
			public K getMax(){
				return aux.getFront();
			}

			@Override
			public K removeRear() {
				throw new RuntimeException();
			}

			@Override
			public boolean insertFront(K e) {
				throw new RuntimeException();
			}

			@Override
			public boolean insertRear(K e) {
				boolean isInserted = super.insertRear(e);;
				if(isInserted){
					
					while(!aux.isEmpty() && aux.getRear().compareTo(e) < 0 ){
						aux.removeRear();
						
					}
					aux.insertRear(e);
					
                  return true;					
				}else{
					return false;
				}
			}

			@Override
			public K removeFront() {
				K e = super.removeFront();
				if(e != null){
					if(aux.getFront().equals(e)){
						aux.removeFront();
					}
					return e;
				}else{
					return null;
				}
			}
			
		 }
		 
		 private static class MyDequeue<K>{
			 
			 private Object[] arr;
			 private int f = 0;
			 private int r =0;
			 private int capacity;
			 
			 public MyDequeue(int capacity){
				 this.arr = new Object[capacity+1];
				 this.capacity = capacity+1;
			 }
			 
			 public boolean isEmpty(){
				 return f==r;
			 }
			 
			 public K getRear(){
				 return ((K[])arr)[r==0?capacity-1:r-1];
			 }
			 
			 public K getFront(){
				 return ((K[])arr)[f];
			 }
			
			 
			 public boolean insertRear(K e){
				 int newIdx = (r+1)%capacity;
				 if (newIdx != f) {
					((K[]) arr)[r] = e;
					r = newIdx;
					return true;
				}else{
					return false;
				}
				 
			 }
			 
			 public K removeRear(){
					 if ( r != f) {
						 K e = ((K[]) arr)[r];
						 r = r==0?capacity-1:r-1;		 
						((K[]) arr)[r] = null;
						return e;
					}else{
						return null;
					}
							 
						 }
			 
			 public boolean insertFront(K e){
				 int newIdx = f==0?capacity-1:f-1;
				 if (r != newIdx) {
					 f = newIdx;
					((K[]) arr)[f] = e;
					return true;
				}else{
					return false;
				}
				 
			 }
			 
			 public K removeFront(){
							 
							 if (f != r) {
								K e =  ((K[]) arr)[f] ;
								((K[]) arr)[f] = null;
								f = (f+1)%capacity;
								return e;
							}else{
								return null;
							}
							 
						 }
		 }
		 
		 private static class MyMinStack {
			 
			 private static class StackNode{
				 public int val;
				 public StackNode next;
				 
				 public StackNode(int val){
					 this.val = val;
				 }
			 }
			 
			 /*private int[] arr;
			 private int capacity;
			 private int top=-1;
			 
			 private int[] mins;
			 private int topMins=-1;*/
			 
			 private StackNode top = new StackNode(-1);
			 private StackNode topMins = new StackNode(-1);
			 
			 private boolean isEmpty(){
				 return top.next == null;
			 }
			 
			
			 private void insertTop(StackNode aTop , int x){
				 StackNode prevTop = aTop.next;
		        	aTop.next = new StackNode(x);
		        	aTop.next.next = prevTop;
			 }
			 
			 public void push(int x) {
			        	//arr[++top]=x;
			        	insertTop(top,x);
			        	
			        	if(topMins.next == null || x<topMins.next.val){
			        		//mins[++topMins] = x;
			        		insertTop(topMins,x);
			        	}
			    }

			    public void pop() {
			        if(!isEmpty()){
			          //int popped = arr[top--];
			        	
			        	int popped = top.next.val;
			        	top.next = top.next.next;
			          
			          if(popped == topMins.next.val){
			        	  //topMins--;
			        	  topMins.next = topMins.next.next;
			          }
			        }
			    }

			    public int top() {
			    	if(!isEmpty()){
			    		return top.next.val;
			    	}else{
			    		return -1;
			    	}
			        
			    }

			    public int getMin() {
			        if(!isEmpty()){
			        	//return mins[topMins];
			        	return topMins.next.val;
			        }else{
			        	return -1;
			        }
			    }
			 
			 
		 }
		 
		 
		 public int evalRPN(ArrayList<String> A) {
			 java.util.LinkedList<Integer> evalStack = new java.util.LinkedList<Integer>();
			 int res = -1, exprSz = A.size() , exprIdx = 0;
			 
			 while(exprIdx < exprSz){
				 String currElem = A.get(exprIdx);
				 Integer e2 = null;
				 Integer e1 = null;
				 switch(currElem){
				 case "+":
					 e2 = evalStack.poll();
					 e1 = evalStack.poll();
					 evalStack.push(e1+e2);
					 break;
				 case "-":
					 e2 = evalStack.poll();
					 e1 = evalStack.poll();
					 evalStack.push(e1-e2);
					 break;
				 case "*":
					 e2 = evalStack.poll();
					 e1 = evalStack.poll();
					 evalStack.push(e1*e2);
					 break;
				 case "/":
					 e2 = evalStack.poll();
					 e1 = evalStack.poll();
					 evalStack.push(e1/e2);
					 break;
			     default:
						evalStack.push(Integer.parseInt(currElem));
				 }
				 exprIdx++;
			 }
			 
			 return evalStack.pop();
		    }
		 
		 public int braces(String A) {
			List<String> xs =  Arrays.asList(A.split(""));
			int xIdx = 0 , xSz = xs.size();
			int res = 0;
			java.util.LinkedList<Integer> exprLen = new java.util.LinkedList<Integer>();
			
			while(xIdx < xSz && res != 1){
				String currElem = xs.get(xIdx).trim();
				switch(currElem){
				case "(":
					exprLen.push(0);
					break;
				case ")":
					Integer currExprLen = exprLen.poll();
					if(currExprLen == 1){
						res = 1;
					}else{
						if(exprLen.peek() != null)
						exprLen.push(exprLen.poll()+1);
					}
					break;
				case "":
					break;
				default:
					if(exprLen.peek() != null)
					exprLen.push(exprLen.poll()+1);
						
				}
				xIdx++;
			}
			return res;
		    }
		
		 
			public ListNode mergeKLists(ArrayList<ListNode> a) {
				
				java.util.PriorityQueue<ListNode> hp = new java.util.PriorityQueue<ListNode>(new java.util.Comparator<ListNode>(){

					@Override
					public int compare(ListNode o1, ListNode o2) {
						return o1.val-o2.val;
					}
					
				});
				
				for(ListNode k:a){
					hp.offer(k);
				}
				ListNode resHd = null;
				ListNode resTl = null;
				
				while(hp.peek() != null){
					ListNode curr = hp.poll();
					if(resHd == null){
						resHd = curr;
					}
					if(resTl != null){
						resTl.next = curr;
						resTl = resTl.next;
					}else{
						resTl = curr;
					}
					
					if(curr.next != null)
					  hp.offer(curr.next);
					
				
				}
				
				return resHd;
			}
			
			// generate all nCk combinations where individual sets are sorted
			public ArrayList<ArrayList<Integer>> combine(int n ,int k) {
				return combineHelper(1,n,k);
			}
			 private ArrayList<ArrayList<Integer>> combineHelper(int n , int N, int k) {
				 if(n > N ){
	                    return new ArrayList<ArrayList<Integer>>();
	                }
				 if(k == 1){
					 ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
					 for(int i=n;i<=N;i++){
						 res.add(new ArrayList<Integer>(Arrays.asList(i)));
					 }
					 return res;
				 }
				 ArrayList<ArrayList<Integer>> withNs = combineHelper(n+1,N,k-1);
				 
				 for(ArrayList<Integer> r:withNs ){
					 r.add(0,n);
				 }
				 //ArrayList<ArrayList<Integer>> withoutNs = combineHelper(n+1,N,k);
				  //withoutNs.addAll(withNs);
				  withNs.addAll(combineHelper(n+1,N,k));
				  return withNs;
			    }
			 
			 //Given xs, get a single nCk with equal probability
			 public List<Integer> singleCombination(ArrayList<Integer> xs,  int k){
				 ArrayList<Integer> res = new ArrayList<Integer>();
				Random rndGen = new Random();
				int n = xs.size();
				BitSet selectedIdxs = new BitSet(xs.size()); 
				selectedIdxs.clear();
				
				//O(kn)
				 while(res.size() < k){
				    int selectIdx =-1;
				    for(selectIdx=rndGen.nextInt(n);selectedIdxs.get(selectIdx);selectIdx=rndGen.nextInt(n));//O(k)
				    	selectedIdxs.set(selectIdx);
				    res.add(xs.get(selectIdx));
				    //xs.remove(selectIdx);//O(n)
				    n--;
				 }
				 
				 return res;
			 }
			 
			 //reservoir sampling - Algo R
			 public static List<Integer> singleCombination2(ArrayList<Integer> xs,  int k){
               int n = xs.size();
               Integer[] res = new Integer[k];
               Random rndGen = new Random();
               for(int i=0;i<k;i++)
            	   res[i]=xs.get(i);
               
               for(int j=k;j<n;j++){
            	 int selectIdx = rndGen.nextInt(j);   
            	 if(selectIdx < k){
            		 res[selectIdx] = xs.get(j);
            	 }
               }
               return Arrays.asList(res);
			 }
			 
			 //return first indexOf substring in the given string 
			 public int strStr(final String str, final String substr) {
				 int i=0;
				 
				 for(;i<str.length() ;i++){
					 if(str.charAt(i) == substr.charAt(0)){
						 int j = 1;
						 for(;j<substr.length() && (i+j) < str.length() && str.charAt(i+j) == substr.charAt(j);j++);
						 if(j==substr.length()){
							 return i;
						 }
					 }
				 }
				 
				 //return subIdx==substr.length()?(i-substr.length()):-1;
				 return -1;
			    }
			 
			 
			 public String longestCommonPrefix(ArrayList<String> strs) {
				 String prefix = "";
				 
				 if(strs.isEmpty()){
					 return prefix;
				 }
				 prefix = strs.get(0);
				 
				 for(int i=1;i<strs.size();i++){
					 String s = strs.get(i);
					 int sn = s.length(), pn = prefix.length(), cn = (pn<=sn)?pn:sn;
					 int j=0;
					 for(;j<cn && s.charAt(j) == prefix.charAt(j) ;j++);
					 prefix = prefix.substring(0, j);
					 
				 }
				 return prefix;
			    }
			 
			 public ArrayList<ArrayList<Integer>> diagonal(ArrayList<ArrayList<Integer>> sqrMatrix) {
				 
				 int n = sqrMatrix.size();
				 ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
				 
				 for(int x=0,y=0; x<n ; y=(x==0)?y+1:y, x=(y==n)?x+1:x){
					 
					 ArrayList<Integer> resRow = new ArrayList<Integer>();
					 res.add(resRow);
					 System.out.println("End points "+x+" "+y);
					 for(int xi=x,yi=(y==n)?n-1:y;xi<n && yi>=0;xi++,yi--){
						 System.out.println("Adding elem --. "+sqrMatrix.get(xi).get(yi));
						 resRow.add(sqrMatrix.get(xi).get(yi));
					 }
				 }
				 
				 return res;
			    }
			 
			 public int maximumGap(final List<Integer> xs) {
				 
				 int max = Integer.MIN_VALUE , min = Integer.MAX_VALUE, bucketSize = 0, maxGap = 0, n = xs.size()-1;

				 if(xs.size() < 2){
					 return maxGap;
				 }
				 Integer[][] buckets = new Integer[n][2];
				 //Arrays.fill(buckets, -1);
				 for(int i=0;i<n;i++){
					 buckets[i][0] = -1;
					 buckets[i][1] = -1;
				 }
				 
				 for(Integer x:xs){
					 if(x>max){
						 max=x;
					 }
					 if(x<min){
						 min = x;
					 }
				 }
				 
				 if(max == min){
					 return maxGap;
				 }
				 
				 if(n==1){
					 return max-min;
				 }
				 bucketSize = ((max-min)/n)+1;
				// bucketSize =new Long( Math.round( ((max-min)*1.0/n*1.0))).intValue();
				 
				 //populate bucket
				 for(int i=0;i<xs.size();i++){
					Integer x = xs.get(i);
					if(x == min || x == max){
						continue;
					}
					int b = (x-min)/bucketSize;
					//b = (b==0)?0:b-1;
					if(buckets[b][0] == -1){
						buckets[b][0] = x;
						buckets[b][1] = x;
					}else if(x < buckets[b][0]){
						buckets[b][0] = x;
					}else if(x > buckets[b][1]){
						buckets[b][1] = x;
					}
				 }
				 
				 for(int bCnt=1, prev=0;bCnt<n;prev=bCnt,bCnt++){
					 
					 for(;bCnt<n && buckets[bCnt][0] == -1;bCnt++);
					 int currGap = ((bCnt<n)?buckets[bCnt][0]:max) - ((buckets[prev][1] == -1)?min:buckets[prev][1]);
					 if(currGap > maxGap){
						 maxGap=currGap;
					 }
					 if(bCnt == n-1){
						 currGap = max - buckets[bCnt][1];
						 if(currGap > maxGap){
							 maxGap = currGap;
						 }
					 }
				 }
				 
//				 		 
				 return maxGap;
				 
			    }
			 
			 
			 public ArrayList<String> fullJustify(ArrayList<String> words, int numWordsPerLine) {
				 
				 int i=0,n=words.size();
				 ArrayList<String> res = new ArrayList<String>();
				 
				 while(i<n){
					 int currLineWordCount = i, currLineWordCharsSum = 0;
					 for(;currLineWordCount<n ;currLineWordCount++ ){
						 if(currLineWordCharsSum + words.get(currLineWordCount).length() <= numWordsPerLine){
							 currLineWordCharsSum = currLineWordCharsSum+ words.get(currLineWordCount).length()+1;
						 }else{
							 //currLineWordCount--;
							 currLineWordCharsSum--;
							 break;
						 }
					 }
					 
					 if(currLineWordCount == n){
						 //last line
						 StringBuilder currLine = new StringBuilder();
						 
						 for(int j=i;j<currLineWordCount;j++){
							 currLine.append(words.get(j));
							 if(j<currLineWordCount-1)
							 currLine.append(" ");
						 }
						 int spacesToAddAtTheEnd = numWordsPerLine - currLine.length();
						 while(spacesToAddAtTheEnd-->0){
							 currLine.append(" ");
						 }
						 String currLineStr = currLine.toString();
						 System.out.println("CurrLine length1 --> "+currLineStr.length());
						 res.add(currLineStr);
						 
					 }else{
						 int totalSpaces = numWordsPerLine - currLineWordCharsSum ;
						 int numOfBreaks = currLineWordCount-i-1;
						 int equalShareOfSpaces = numOfBreaks==0?0: totalSpaces/numOfBreaks;
						 
						 int balanceSpaces = totalSpaces - (numOfBreaks*equalShareOfSpaces);
						 StringBuilder minWordGap = new StringBuilder();
						 for(int j=0;j<equalShareOfSpaces;j++){
							 minWordGap.append(" ");
						 }
						 String minWordGapStr = minWordGap.toString();
						 
						 StringBuilder currLine = new StringBuilder();
						 
						 for(int j=i;j<currLineWordCount;j++){
							 
							 if(currLine.length() ==0){
								 
								 currLine.append(words.get(j));
							 }else{
								 currLine.append(" ");
								 currLine.append(minWordGapStr);
								 if(balanceSpaces-- >0){
									 currLine.append(" ");
								 }
								 currLine.append(words.get(j));
							 }
						 }
						 while(balanceSpaces-- >0){
							 currLine.append(" ");
						 }
						 String currLineStr = currLine.toString();
						 System.out.println("CurrLine length --> "+currLineStr.length());
						 res.add(currLineStr);
						 
					 }
					 i=currLineWordCount;
					 
				 }
				 
				 return res;
				 
			    }
		 
		 //DP
		
		 
		 
		 
		 public static long countInversions(final ArrayList<Integer> as){
				
				if(as == null || as.size() == 1)
					return 0;
				ArrayList<Integer> unsortedLeftArr = new ArrayList<Integer>(as.subList(0,as.size()/2 ));
				ArrayList<Integer> unsortedRightArr = new ArrayList<Integer>(as.subList(as.size()/2 ,(as.size()/2)+(as.size()%2==0?as.size()/2:(as.size()+1)/2)));
				
				
				return countInversions(unsortedLeftArr) + countInversions(unsortedRightArr) + countCrossInversions(unsortedLeftArr, unsortedRightArr);
			}
			
			private static long countCrossInversions(final  ArrayList<Integer> leftArr, final ArrayList<Integer> rightArr){
//				int[] sortedLeftArr = sort(leftArr);
//				int[] sortedRightArr = sort(rightArr);
				Collections.sort(leftArr);
				Collections.sort(rightArr);
				ArrayList<Integer> sortedLeftArr = leftArr;
				ArrayList<Integer> sortedRightArr = rightArr;
				int mergedArrLength = leftArr.size()+rightArr.size();
				int numOfInversions = 0;

				for(int i = 0,left=0, right = 0;  (right < sortedRightArr.size());i++){
					if((left < sortedLeftArr.size() && sortedLeftArr.get(left) <= sortedRightArr.get(right))){
						left++;
					}else{
						for(int curr=left;curr <sortedLeftArr.size();curr++)
						 System.out.println("Inversion --> (  "+sortedLeftArr.get(curr)+" , "+sortedRightArr.get(right)+"  )");
						right++;
		                
						//actual 1 
						numOfInversions += (sortedLeftArr.size() - left) ;
					}
				}
				return numOfInversions;
			}
			
			//gasup problem
			public int canCompleteCircuit(final List<Integer> gas, final List<Integer> cost) {
				Integer totalGas = gas.stream().reduce(0, (x,y)->x+y);
				Integer totalCost = cost.stream().reduce(0, (x,y)->x+y);
				
				int remainingGas = gas.get(0);
				int minRemainingGas = gas.get(0);
				int minCityIndex = 0;
				
				if(totalGas < totalCost){
					return -1;
				}else{
					for(int i=1;i<gas.size();i++){
						remainingGas = remainingGas + (gas.get(i-1) - cost.get(i-1));
						if(remainingGas < minRemainingGas){
							minRemainingGas = remainingGas;
							minCityIndex = i;
						}
					}
				}
				
				return minCityIndex;
		    }
			
			public int majorityElement(final List<Integer> xs) {
				int majorityElem = -1, cnt=0;
				
				for(Integer x:xs){
					if(cnt==0){
						majorityElem = x;
						cnt++;
					}else{
						if(x == majorityElem){
							cnt++;
						}else{
							cnt--;
						}
					}
				}
				
				return majorityElem;
				
		    }
			
			
//			 public int majorityElement(final List<Integer> A) {
//				 if(A.size() == 1){
//					 return A.get(0);
//				 }else {
//					 int leftMajority
//				 }
//			    }
//			 private int[] majorityElementsUtil(final List<Integer> A){
//				 if(A.size() == 1){
//					 return new int[]{A.get(0)};
//				 }else if(A.size() == 2) {
//					 int leftMajority
//				 }
//			 }
			
			
			//longest path in a tree (not binary)
			//[-1, 0, 0, 0, 3] 
			public int solve(ArrayList<Integer> treeArr) {
				int rootIdx = 0 , d1 = 0 , d2=0;
				List<Integer>[] adjList = new List[treeArr.size()];
				for(;treeArr.get(rootIdx) != -1;rootIdx++);
				for(int i=0;i<treeArr.size();i++) {
					if(treeArr.get(i) == -1) {
						rootIdx = i;
						continue;
					}
					if(adjList[treeArr.get(i)] == null) {
						adjList[treeArr.get(i)] = new ArrayList<Integer>();
					}
					adjList[treeArr.get(i)].add(i);
				}
				
                //int[] heightAnd
				return getHeightAndDiameterOfMultiTree(adjList, rootIdx)[1];
		    }
			
		
			//[height, diameter]
			private Integer[] getHeightAndDiameterOfMultiTree(final List<Integer>[] treeArr,int root1){
				Integer[][] resList = new Integer[treeArr.length][2];
				
				
				
			BiFunction<Integer, List<Integer>,Runnable> processNodeCreator = (idx,chldrn) -> 
			() -> {
				int diameter = 0;
			
				int[] top2ChildHeights = new int[] {0,0};
				List<Integer[]> chldResList = chldrn.stream().map(x->resList[x]).collect(Collectors.toList());
				if (chldResList != null) {
					for (Integer[] currResArr : chldResList) {
						//int[] currResArr = getHeightAndDiameterOfMultiTree(treeArr, i);
						System.out.println(" curr MaxDepth  " + idx + " -- " + currResArr[0] + " --- " + currResArr[1]);
						if (currResArr[1] > diameter) {
							diameter = currResArr[1];
						}

						if (currResArr[0] > top2ChildHeights[1]) {
							if (currResArr[0] > top2ChildHeights[0]) {
								int tmp = top2ChildHeights[0];
								top2ChildHeights[0] = currResArr[0];
								top2ChildHeights[1] = tmp;
							} else {

								top2ChildHeights[1] = currResArr[0];
							}

						}
					} 
				}
				resList[idx] = new Integer[]{top2ChildHeights[0]+1, top2ChildHeights[0]+top2ChildHeights[1] > diameter?top2ChildHeights[0]+top2ChildHeights[1]:diameter};
			};
			
			Stack<Runnable> s2 = new Stack<>();
			Stack<Supplier<List<Integer>>> s1 =  new Stack<>();
			
			Function<Integer,Supplier<List<Integer>>> fetchChildrenCreatorFn = (x) -> { return ()->{List<Integer> t = new ArrayList<>();t.add(x);if(treeArr[x] != null)t.addAll(treeArr[x]); return t;};};
			
			s1.push(fetchChildrenCreatorFn.apply(root1));
			
			while(!s1.isEmpty()) {
				Supplier<List<Integer>> parentSup = s1.pop();
				List<Integer> selfAndChildren = parentSup.get();
				selfAndChildren.subList(1, selfAndChildren.size()).stream().map(fetchChildrenCreatorFn).forEach(sup->s1.push(sup));;
				s2.push(processNodeCreator.apply(selfAndChildren.get(0),selfAndChildren.subList(1,selfAndChildren.size())));
			}
			
			while(!s2.isEmpty()) {
				Runnable chldNodeProcess = s2.pop();
				chldNodeProcess.run();
			}
				return resList[root1];
			}
			
			static int deletionDistance(String str1, String str2) {
			    // your code goes here
			    int i1=0,i2=0, res=0;
			    while(i1<str1.length()-1 || i2<str2.length()-1 ){
			      if(str1.charAt(i1) != str2.charAt(i2)){
			        if(i1<str1.length()-1 && i2<str2.length()-1){
			          res += 2;
			        }else{
			          res += 1;
			        }
			        
			      }else{
			    	  System.out.println("matching "+str1.charAt(i1) );
			      }
			      System.out.println("matching1 "+str1.charAt(i1) );
			      System.out.println("matching2 "+str2.charAt(i2) );
			      if(i1<str1.length()-1){
			        i1++;
			      }
			      if(i2<str2.length()-1){
			        i2++;
			      }
			    }
			    
			    return res;
			  }
			
		public String longestSubstringWithoutRepeat(String s){
			int[] charFreqs = new int[128];
			Arrays.fill(charFreqs, -1);
			int begin = 0,end=0, d = Integer.MIN_VALUE;
			String res  = "";
			while(end < s.length()){
				System.out.println("begin --> "+begin+" end --> "+end);
				for(;end < s.length() && charFreqs[s.charAt(end)]==-1;end++)
					charFreqs[s.charAt(end)]=end;
				if((end-begin)>d){
					d = end-begin;
					res = s.substring(begin,end);
				}
				if(end < s.length()){
					//duplicate detected
					
					for(;begin<=charFreqs[s.charAt(end)];begin++){
						charFreqs[s.charAt(begin)]=-1;
					}
				}
			}
			return res;
		}
		
		public String minWindowInString(String s, String t){
			int[] charFreqs = new int[128];
			for(int i=0;i<t.length();i++)
				charFreqs[t.charAt(i)]++;
			
			int begin = 0,end=0, d = Integer.MAX_VALUE,counter = t.length(), ucntr = 0 , head = 0 ;
			while(end < s.length()){
                      if(charFreqs[s.charAt(end++)]-->0){
                    	  counter--;
                    	 // charFreqs[s.charAt(end)]++;
                      }
					
					while(begin < s.length() && counter == 0){
						if(   (end-begin)<d){
       						d = end-begin;
       						head = begin;
       						//res = s.substring(begin,end);
       					}
						System.out.println("1begin --> "+(begin)+" end --> "+end+" counter "+counter);
                       if(charFreqs[s.charAt(begin++)]++ == 0){
                    	   System.out.println("begin --> "+(begin)+" end --> "+end+" counter "+counter);
                    	   counter++;
                       }
					}
					System.out.println("New iteratiion");
			}
			System.out.println("head --> "+head+" d --< "+d);
			return d==Integer.MAX_VALUE || d<0?"":s.substring(head, head+d);
		}
		
		
		 public ArrayList<String> prettyJSON(String inpStr) {
			 String[] inpWords = inpStr.split(",");
			 StringBuilder indents = new StringBuilder();
			 ArrayList<String> res = new ArrayList<String>();
			 String indentStr = "";
			 int indentTop = -1;
			 for(int i=0;i<inpWords.length;i++){
				 if(("]".equals(inpWords[i]) || "}".equals(inpWords[i])) && indentTop >= 0){
					 indents.deleteCharAt(indentTop--);
					 indentStr = indents.toString();
				 }
				 res.add(indentStr+inpWords[i]);
				 if("[".equals(inpWords[i]) || "{".equals(inpWords[i])){
					 indents.append("\t");
					 indentTop++;
					 indentStr = indents.toString();
				 }else if(("]".equals(inpWords[i]) || "}".equals(inpWords[i])) && indentTop >= 0){
					 indents.deleteCharAt(indentTop--);
					 indentStr = indents.toString();
				 }
			 }
			 return res;
		    }
		
		
		 
		  public int jump1(ArrayList<Integer> xs) {
			  int[] minJumps = new int[xs.size()];
			  minJumps[xs.size()-1] = 0;
			  for(int i=minJumps.length-2;i>=0;i--){

					int tmpMin = Integer.MAX_VALUE;
					int rangeMax = (i + xs.get(i) > xs.size()-1?xs.size()-1:i + xs.get(i));
					for (int j = i + 1; j <= rangeMax; j++) {
						if (minJumps[j] < tmpMin) {
							tmpMin = minJumps[j];
						}
					}
					if (tmpMin !=  Integer.MAX_VALUE) {
						minJumps[i] = tmpMin + 1;
					} else {
						minJumps[i] = Integer.MAX_VALUE;
					}
				
			  
			  }
			  return minJumps[0]==Integer.MAX_VALUE? -1:minJumps[0];
		    }
		  
		  
		  public int jump(ArrayList<Integer> xs) {
			  int jumpCount = 0;
			  int maxReachable = 0;
			  int boundary = 0;
			  for(int i=0;i<xs.size()-1;i++){
				  maxReachable = Math.max(maxReachable, i+xs.get(i));
				  if(i==boundary){
					  boundary = maxReachable;
					  jumpCount++;
				  }
			  }
			  
			  return boundary>=xs.size()-1? jumpCount:-1;
		  }
		  
		  public int climbStairs(int n) {
			  
			  if(n == 0){
				  return 0;
			  }else if(n == 1){
				  return 1;
			  }else if(n==2){
				  return 2;
			  }
			  
			  
			  return climbStairs(n-1) + climbStairs(n-2);
			  
		    }
		  
		  // first increasing , then decreasing subsequence - bitonic subsequence
		  public int longestSubsequenceLength1(final List<Integer> xs) {
			  if(xs.size() == 0){
    	  	      return 0;
    	  	  }
			  int[] l = new int[xs.size()];
			  int res=1;
			  l[0] = 1;
			  for(int i=1;i<l.length;i++){
				  int maxLen=Integer.MIN_VALUE;
				  int maxIdx=Integer.MIN_VALUE;
				  for(int j=i-1;j>=0;j--){
					  if(xs.get(i) < xs.get(j)){
						  maxLen = l[j]+1;
						  break;
					  }else if(xs.get(i) == xs.get(j)){
                        maxLen = l[j];
					  }
					  else{
						if(xs.get(j) > maxIdx){
							maxLen = l[j]+1;
							maxIdx = xs.get(j);
						}
					  }
				  }
				  l[i] = maxLen;
				  if(l[i] > res){
					  res = l[i];
				  }
			  }
			  return res;
		    }
		  
		  
		  
		  
		  public ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> xs) {
		      ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
			   permuteFrom(res , 0, xs.toArray(new Integer[0]));
			   return res;
			  
		    }
		  
		  private void permuteFrom(ArrayList<ArrayList<Integer>> res , int startingPos , Integer[]  xs) {
			  
			  if( startingPos == xs.length-1){
				  res.add(new ArrayList<Integer>(Arrays.asList(xs)));
			  }
			  
			  for(int i = startingPos;i<xs.length;i++){
				  swapInArr(xs,startingPos,i);
				  permuteFrom(res , startingPos+1 ,  xs);
				  swapInArr(xs,startingPos,i);
			  }
			  
		  }
		  
		  private void swapInArr(Integer[] xs,int i , int j){
			  Integer tmp  = xs[j];
			  xs[j]  = xs[i];
			  xs[i] = tmp;
			  
		  }
		  
		 
		  public ArrayList<Integer> grayCode(int n) {
			  ArrayList<Integer> res = null ;
			  if(n==1){
				  res = new ArrayList<Integer>();
				  res.add(0);
				  res.add(1);
				  return res;
			  }
			  res = grayCode(n-1);
			  int currMSBSetVal = (int)Math.pow(2,n-1);
			  int tmpSz = res.size();
			  for(int j=tmpSz-1;j>=0;j--){
				  res.add(res.get(j) + currMSBSetVal);
			  }
			  
			  return res;
		  }
		  
		  public int searchInsert(ArrayList<Integer> a, int b) {
			  int lo = 0 , hi = a.size()-1 , res = -1 ;
			  while(lo < hi) {
				  int mid = (lo+hi)/2 ;
				  if(a.get(mid) == b) {
					  res = mid;
					  break;
				  }else if(a.get(mid) < b) {
					  lo = mid+1;
				  }else {
					  hi = mid-1;
				  }
				  
			  }
			  if(res == -1) {
				  if(a.get(lo) < b) {
					  res = lo+1;
				  }else {
					  
					  res = lo;
				  }
			  }
			  return res;
		    }
		  
		  
		  
	// tests

	// public static ArrayList<Integer> createIntegerArray

	@Test
	public void testRotatedSearch() {
		assertEquals(-1, search(Arrays.asList(0, 1, 2, 4, 5, 6, 7), 8));
		assertEquals(3, search(Arrays.asList(0, 1, 2, 4, 5, 6, 7), 4));
		assertEquals(0, search(Arrays.asList(4, 5, 6, 7, 0, 1, 2), 4));
		assertEquals(4, search(Arrays.asList(4, 5, 6, 7, 0, 1, 2), 0));

		// assertEquals(5, search(Arrays.asList(4 ,2,1,0,7,6,5), 6));

		assertEquals(-1, search(Arrays.asList(4, 5, 6, 7, 0, 1, 2), 8));
		assertEquals(1, search(Arrays.asList(4, 5), 5));
		assertEquals(0, search(Arrays.asList(4), 4));
		assertEquals(-1, search(Collections.<Integer> emptyList(), 8));
	}

	@Test
	public void testPowerFunction() {
		assertEquals(19, pow(-1, 1, 20));
		assertEquals(2, pow(2, 3, 3));
		assertEquals(0, pow(0, 0, 1));
		assertEquals(1, pow(-1, 2, 20));
		assertEquals(6, pow(2, 5, 13));
		assertEquals(20805472, pow(71045970, 41535484, 64735492));

	}

	@Test
	public void testRemoveDuplicatesFromArray() {
		System.out.println(Arrays.toString(removeDuplicatesFromArray(new int[] {
				2, 3, 5, 5, 7, 11, 11, 11, 13 })));
		Assert.assertArrayEquals(new int[] { 2, 3, 5, 7, 11, 13 },
				removeDuplicatesFromArray(new int[] { 2, 3, 5, 5, 7, 11, 11,
						11, 13 }));
	}

	@Test
	public void testPhoneNumberToMnemonic() {
		List<String> res = phoneNumberToMnemonic("2276696");
		System.out.println(res);
		Assert.assertTrue(res.contains("ACRONYM"));
	}

	// 〈1, 11, 21, 1211, 111221, 312211,13112221,1113213211〉.
	@Test
	public void testNthLookAndTellSeriesTerm() {
		Assert.assertEquals("11", getNthLookAndSaySeriesTerm(2));
		Assert.assertEquals("21", getNthLookAndSaySeriesTerm(3));
		Assert.assertEquals("1211", getNthLookAndSaySeriesTerm(4));
		Assert.assertEquals("1113213211", getNthLookAndSaySeriesTerm(8));
	}

	@Test
	public void testRotate2DArray() {
		ArrayList<ArrayList<Integer>> req1 = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)),
						new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8)),
						new ArrayList<Integer>(Arrays.asList(9, 10, 11, 12)),
						new ArrayList<Integer>(Arrays.asList(13, 14, 15, 16))));

		ArrayList<ArrayList<Integer>> res1 = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(13, 9, 5, 1)),
						new ArrayList<Integer>(Arrays.asList(14, 10, 6, 2)),
						new ArrayList<Integer>(Arrays.asList(15, 11, 7, 3)),
						new ArrayList<Integer>(Arrays.asList(16, 12, 8, 4))));
		rotate(req1);
		Assert.assertEquals(res1, req1);
	}

	@Test
	public void testPrettyPrint2DArray() {
		ArrayList<ArrayList<Integer>> req1 = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(4, 4, 4, 4)),
						new ArrayList<Integer>(Arrays.asList(4, 3, 3, 4)),
						new ArrayList<Integer>(Arrays.asList(4, 3, 3, 4)),
						new ArrayList<Integer>(Arrays.asList(4, 4, 4, 4))));

		ArrayList<ArrayList<Integer>> res1 = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(13, 9, 5, 1)),
						new ArrayList<Integer>(Arrays.asList(14, 10, 6, 2)),
						new ArrayList<Integer>(Arrays.asList(15, 11, 7, 3)),
						new ArrayList<Integer>(Arrays.asList(16, 12, 8, 4))));
		rotate(req1);
		System.out.println("Array in the test class " + req1);
		Assert.assertEquals(res1, req1);
	}

	@Test
	public void testSqrt() {
		assertEquals(3, sqrt(9));
		assertEquals(1, sqrt(3));
		assertEquals(3, sqrt(11));
		assertEquals(25, sqrt(625));
		assertEquals(29929, sqrt(895745041));
		assertEquals(46340, sqrt(Integer.MAX_VALUE));
		assertEquals(13003, sqrt(169078009));
		assertEquals(1, sqrt(1));
	}

	@Test
	public void testSearchRange() {
		assertEquals(new ArrayList<Integer>(Arrays.asList(3, 4)),
				searchRange1(Arrays.asList(5, 7, 7, 8, 8, 10), 8));
		assertEquals(new ArrayList<Integer>(Arrays.asList(1, 2)),
				searchRange1(Arrays.asList(5, 7, 7, 8, 8, 10), 7));
		assertEquals(new ArrayList<Integer>(Arrays.asList(-1, -1)),
				searchRange1(Arrays.asList(5, 7, 7, 8, 8, 10), 6));
		assertEquals(new ArrayList<Integer>(Arrays.asList(1, 3)),
				searchRange1(Arrays.asList(5, 7, 7, 7, 8, 10), 7));
		assertEquals(new ArrayList<Integer>(Arrays.asList(5, 5)),
				searchRange1(Arrays.asList(5, 7, 7, 7, 8, 10), 10));
		assertEquals(new ArrayList<Integer>(Arrays.asList(0, 2)),
				searchRange1(Arrays.asList(7, 7, 7), 7));
		assertEquals(
				new ArrayList<Integer>(Arrays.asList(98, 140)),
				searchRange1(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
						1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
						1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
						1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
						2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
						2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3,
						3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
						3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
						3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
						4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
						4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
						4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
						5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
						5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
						5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
						6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
						6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
						6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
						7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
						7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8,
						8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
						8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
						8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
						10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
						10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
						10, 10, 10, 10, 10, 10), 3));
	}

	@Test
	public void testFindMedianSortedArray() {
		assertEquals(
				16,
				findMedianSortedArrays1(Arrays.asList(1, 12, 15, 26, 38),
						Arrays.asList(2, 13, 17, 30, 45)), 0);
		assertEquals(
				3.0,
				findMedianSortedArrays1(Arrays.asList(1, 4, 7),
						Arrays.asList(2, 3)), 0);
		assertEquals(
				20,
				findMedianSortedArrays1(new ArrayList<Integer>(),
						Arrays.asList(20)), 0);
		assertEquals(
				3,
				findMedianSortedArrays1(
						Arrays.asList(-40, -25, 5, 10, 14, 28, 29, 48),
						Arrays.asList(-48, -31, -15, -6, 1, 8)), 0);
		assertEquals(
				-20.0,
				findMedianSortedArrays1(
						Arrays.asList(-50, -41, -40, -19, 5, 21, 28),
						Arrays.asList(-50, -21, -10)), 0);
		assertEquals(
				11.5,
				findMedianSortedArrays1(Arrays.asList(0, 23),
						new ArrayList<Integer>()), 0);
		assertEquals(
				5.0,
				findMedianSortedArrays1(
						Arrays.asList(-50, -47, -36, -35, 0, 13, 14, 16),
						Arrays.asList(-31, 1, 9, 23, 30, 39)), 0);

	}
	
	@Test
	public  void testReverseWords(){
		assertEquals("blue is sky the", reverseWords("the sky is blue"));
	}
	
	@Test
	public void testRomanToInt(){
		assertEquals(3999,romanToInt("MMMCMXCIX"));
		assertEquals(1804,romanToInt("MDCCCIV"));
	}
	
	@Test
	public void testAToi(){
		System.out.println(Integer.MIN_VALUE);
		assertEquals(9,atoi("9 2704"));
		assertEquals(2704,atoi("2704"));
		assertEquals(-6435,atoi("-6435D56183011M11 648G1 903778065 762 75316456373673B5 334 19885 90668 8 98K X277 9846"));
		assertEquals(Integer.MAX_VALUE,atoi("21474836472147483647"));
		assertEquals(-2147483648,atoi("-54332872018247709407 4 54"));
	}
	
	@Test
	public void testIntersect(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(3,3,5)), intersect(new ArrayList<Integer>(Arrays.asList(1,2,3,3,4,5,6)), new ArrayList<Integer>(Arrays.asList(3,3,5))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(3,5)), intersect(new ArrayList<Integer>(Arrays.asList(1,2,3,3,4,5,6)), new ArrayList<Integer>(Arrays.asList(3,5))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(35, 38, 53, 67, 69, 94, 98)), intersect(new ArrayList<Integer>(Arrays.asList( 1, 3, 8, 10, 13, 13, 16, 16, 16, 18, 21, 23, 24, 31, 31, 31, 33, 35, 35, 37, 37, 38, 40, 41, 43, 47, 47, 48, 48, 52, 52, 53, 53, 55, 56, 60, 60, 61, 61, 63, 63, 64, 66, 67, 67, 68, 69, 71, 80, 80, 80, 80, 80, 80, 81, 85, 87, 87, 88, 89, 90, 94, 95, 97, 98, 98, 100, 101)), new ArrayList<Integer>(Arrays.asList(5, 7, 14, 14, 25, 28, 28, 34, 35, 38, 38, 39, 46, 53, 65, 67, 69, 70, 78, 82, 94, 94, 98))));
	}
	
	@Test
	public void testRemoveDuplicates(){
		assertEquals(2 , removeDuplicates(new ArrayList<Integer>(Arrays.asList(1,1,2))));
		assertEquals(1 , removeDuplicates(new ArrayList<Integer>(Arrays.asList(1,1,1,1))));
		assertEquals(1 , removeDuplicates(new ArrayList<Integer>(Arrays.asList(5000,5000,5000, 5000))));
		//assertEquals(0 , removeDuplicates(new ArrayList<Integer>(Arrays.asList())));
	}
	
	@Test
	public void testMinDiffTriplet(){
		assertEquals(1 , solve(new ArrayList<Integer>(Arrays.asList(1, 4, 5, 8, 10)), new ArrayList<Integer>(Arrays.asList(6, 9, 15)), new ArrayList<Integer>(Arrays.asList(2, 3, 6, 6))));
	}

	
	
	@Test
	public void testMinimize(){

		assertEquals(5 , minimize(Arrays.asList(1, 4, 10),Arrays.asList(2, 15, 20), Arrays.asList(10, 12)));
	}
	
	@Test
	public void testReorderList(){
		assertEquals(Arrays.asList(1,4,2,3), listFromListNode(reorderList(listNodeFromList(1,2,3,4))));
		assertEquals(Arrays.asList(1,5,2,4,3), listFromListNode(reorderList(listNodeFromList(1,2,3,4,5))));
		assertEquals(Arrays.asList(1), listFromListNode(reorderList(listNodeFromList(1))));
		assertEquals(Arrays.asList(1,2), listFromListNode(reorderList(listNodeFromList(1,2))));
		assertEquals(Arrays.asList(1,3,2), listFromListNode(reorderList(listNodeFromList(1,2,3))));
	}
	
	@Test
	public void testDetectCycle(){
		assertEquals(Arrays.asList(3), listFromListNode(detectCycle(listNodeWithCycleFromList(Arrays.asList(1,2,3,4),2))));
		assertEquals(null, listFromListNode(detectCycle(listNodeWithCycleFromList(Arrays.asList( 87797, 23219, 41441, 58396, 48953, 94603, 2721, 95832, 49029, 98448, 65450),-1))));
		// 87797 23219 41441 58396 48953 94603 2721 95832 49029 98448 65450
	}
	
	@Test
	public void testSortList(){
		assertEquals(Arrays.asList(1,3,4,5), listFromListNode(sortList(listNodeFromList(1,5,4,3))));
		assertEquals(Arrays.asList(1,2,3,4,5), listFromListNode(sortList(listNodeFromList(1,5,4,3,2))));
		assertEquals(Arrays.asList(2 , 3 , 5 , 6 , 8 , 11 , 16 , 19 , 20 , 22 , 23 , 24 , 25 , 26 , 27 , 30 , 31 , 33 , 37 , 38 , 42 , 45 , 49 , 50 , 51 , 55 , 56 , 61 , 62 , 63 , 64 , 66 , 67 , 68 , 69 , 72 , 73 , 74 , 77 , 78 , 84 , 85 , 87 , 92 , 95 , 98 , 99 , 100), listFromListNode(sortList(listNodeFromList(5 , 66 , 68 , 42 , 73 , 25 , 84 , 63 , 72 , 20 , 77 , 38 , 8 , 99 , 92 , 49 , 74 , 45 , 30 , 51 , 50 , 95 , 56 , 19 , 31 , 26 , 98 , 67 , 100 , 2 , 24 , 6 , 37 , 69 , 11 , 16 , 61 , 23 , 78 , 27 , 64 , 87 , 3 , 85 , 55 , 22 , 33 , 62))));
	}
	
	@Test
	public void testSlidingMaximum(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(3,3,5,5,6,7)), slidingMaximum(Arrays.asList(1, 3, -1, -3, 5, 3, 6, 7), 3));
		assertEquals(new ArrayList<Integer>(Arrays.asList(1)), slidingMaximum(Arrays.asList(1), 1));
	}
	
	@Test
	public void testMyMinStack(){
		MyMinStack x = new MyMinStack();
		x.push(5);
		x.push(2);
		x.push(3);
		x.push(1);
		x.push(7);
		x.push(4);
		
		assertEquals(1,x.getMin());
		x.pop(); //4
		assertEquals(1,x.getMin());
		x.pop();//7
		x.pop();//1
		assertEquals(3,x.top());
		assertEquals(2,x.getMin());
		x.pop();//3
		assertEquals(2,x.getMin());
		assertEquals(2,x.top());
		x.pop();//2
		assertEquals(5,x.getMin());
		x.pop();//5
		assertEquals(-1,x.getMin());
		assertEquals(-1,x.top());
		
		x.push(Integer.MAX_VALUE);
		x.push(Integer.MIN_VALUE);
		x.push(4);
		
		assertEquals(Integer.MIN_VALUE,x.getMin());
		assertEquals(4,x.top());
		
		x.pop();
		x.pop();
		assertEquals(Integer.MAX_VALUE,x.getMin());
	}

	@Test
	public void testMergeKLists(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(1 , 3 , 4 , 8 , 9 , 10 , 11 , 13 , 20)), listFromListNode(mergeKLists(new ArrayList<ListNode>(Arrays.asList(listNodeFromList(1,10,20) , listNodeFromList(4,11,13), listNodeFromList(3,8,9))))));
	}
	
	//stack
	@Test
	public void testEvalRPN(){
		assertEquals(9, evalRPN(new ArrayList<String>(Arrays.asList("2", "1", "+", "3", "*"))));
		assertEquals(6, evalRPN(new ArrayList<String>(Arrays.asList("4", "13", "5", "/", "+"))));
	}
	
	@Test
	public void testBraces(){
		assertEquals(1, braces("((a + b))"));
		assertEquals(0, braces("(a + (a + b))"));
		assertEquals(0, braces("a + b"));
	}
	
	
	
	@Test
	public void testMyLRUCache(){
		MyLRUCache lru = new MyLRUCache(2);
		lru. set(1, 10);
        lru.set(5, 12);
        
        assertEquals(12,lru.get(5));
        assertEquals(10,lru.get(1));
        assertEquals(-1,lru.get(50));
        
        lru.set(6, 14);
        
        assertEquals(14,lru.get(6));
        assertEquals(-1,lru.get(5));
        
        assertEquals( new ArrayList<Integer>(Arrays.asList(-1 ,-1 ,-1 ,-1 ,-1 ,-1 ,-1 ,-1 ,-1 ,12 ,-1 ,-1 ,4 ,14 ,12, 5 ,12, 6 ,11 ,-1 ,-1 ,12 ,6 ,-1, 6 ,11 ,11, 5, 12, 12, 12, 10, 11, 10, 11, 4, 4 ,-1, 11 ,10 ,10 ,5 ,-1 ,-1 ,5 )),
              prepareCacheForTest("95 11 S 1 1 G 11 G 11 S 3 10 G 10 S 3 12 S 1 15 S 4 12 G 15 S 8 6 S 5 3 G 2 G 12 G 10 S 11 5 G 7 S 5 1 S 15 5 G 2 S 13 8 G 3 S 14 2 S 12 11 S 7 10 S 5 4 G 9 G 2 S 13 5 S 10 14 S 9 11 G 5 S 13 11 S 8 12 G 10 S 5 12 G 8 G 11 G 8 S 9 11 S 10 6 S 7 12 S 1 7 G 10 G 9 G 15 G 15 G 3 S 15 4 G 10 G 14 G 10 G 12 G 12 S 14 7 G 11 S 9 10 S 6 12 S 14 11 G 3 S 7 5 S 1 14 S 2 8 S 11 12 S 8 4 G 3 S 13 15 S 1 4 S 5 3 G 3 G 9 G 14 G 9 S 13 10 G 14 S 3 9 G 8 S 3 5 S 6 4 S 10 3 S 11 13 G 8 G 4 S 2 11 G 2 G 9 S 15 1 G 9 S 7 8 S 4 3 G 3 G 1 S 8 4 G 13 S 1 2 G 3"));
	}
	
	
	
	@Test
	public void testKthsmallest(){
       assertEquals(2,kthsmallest(Arrays.asList(2,1,4,3,2), 3));		
       //assertEquals(2,kthsmallest(Arrays.asList(2,1,4,3,2), 3));		
	}
	
	@Test
	public void testNextGreater(){
		
		assertEquals(new ArrayList<Integer>(Arrays.asList(5, 10, 10, -1)),nextGreater(new ArrayList<Integer>(Arrays.asList(4, 5, 2, 10))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(-1, -1, -1)),nextGreater(new ArrayList<Integer>(Arrays.asList(3,2,1))));
		assertEquals(new ArrayList<Integer>(Arrays.asList(-1, -1, -1)),nextGreater(new ArrayList<Integer>(Arrays.asList(39, 27, 11, 4, 24, 32, 32, 1))));
	}
	
	@Test
	public void testLongestConsecutive(){
		assertEquals(4,longestConsecutive(Arrays.asList(100, 4, 200, 1, 3, 2)));
	}
	
	
	@Test
	public void testPalidromicDecomposition(){
		assertEquals(new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("a","a","b")), new ArrayList<String>(Arrays.asList("aa","b"))))
				, partition("aab"));
//		assertEquals(new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("020","44","5","1881")), new ArrayList<String>(Arrays.asList("020","44","5","1","88","1"))))
//				, partition("0204451881"));
	}
	
	@Test
	public void testCountInversions(){
		assertEquals(45,countInversions(new ArrayList<Integer>(Arrays.asList(10,9,8,7,6,5,4,3,2,1))));
		System.out.println("\n -------- \n");
		assertEquals(3,countInversions(new ArrayList<Integer>(Arrays.asList(2, 4, 1, 3, 5))));
		assertEquals(2,countInversions(new ArrayList<Integer>(Arrays.asList(2, 4, 1, 4, 5))));
		System.out.println("\n -------- \n");
		assertEquals(290,countInversions(new ArrayList<Integer>(Arrays.asList(84, 2, 37, 3, 67, 82, 19, 97, 91, 63, 27, 6, 13, 90, 63, 89, 100, 60, 47, 96, 54, 26, 64, 50, 71, 16, 6, 40, 84, 93, 67, 85, 16, 22, 60 ))));
	}
	
	@Test
	public void testLongestPathInAMultiTree(){
		
		assertEquals(3, solve(new ArrayList<Integer>(Arrays.asList(-1, 0, 0, 0, 3))));
		System.out.println("\n -------- \n");
		assertEquals(11, solve(new ArrayList<Integer>(Arrays.asList( -1, 0, 0, 2, 1, 2, 4, 4, 2, 5, 5, 1, 1, 2, 4, 13, 7, 0, 2, 9, 2, 16, 18, 0, 13, 13, 22, 10, 8, 3, 26, 14, 24, 0, 26, 0, 8, 15, 6, 22, 20, 30, 1, 2, 10, 0, 39, 3, 8, 40, 9, 12, 42, 37, 39, 47, 52, 24, 29, 48, 15, 18, 50, 46, 43, 55, 26, 1, 6, 28, 59, 51, 56, 4, 53, 30, 5, 54, 18, 29, 3, 65, 30, 16, 9, 22, 14, 30, 32, 62, 0, 6, 44, 18, 37, 14, 80, 93, 2, 95))));
		System.out.println("\n -------- \n");
		assertEquals(16, solve(new ArrayList<Integer>(Arrays.asList( -1, 0, 1, 1, 3, 0, 4, 0, 2, 8, 9, 0, 4, 6, 12, 14, 7, 9, 6, 4, 14, 13, 1, 9, 16, 17, 17, 0, 21, 10, 13, 14, 25, 28, 27, 0, 35, 20, 34, 23, 37, 3, 6, 25, 30, 22, 15, 37, 8, 6, 11, 22, 50, 12, 4, 2, 54, 23, 18, 52, 34, 49, 61, 8, 15, 63, 31, 51, 48, 41, 26, 37, 30, 15, 59, 12, 0, 40, 37, 73, 32, 19, 70, 29, 8, 21, 83, 33, 7, 13, 12, 82, 43, 86, 38, 31, 1, 84, 62, 83))));
		assertEquals(89, solve(new ArrayList<Integer>(Arrays.asList(  -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 8, 37, 11, 14, 27, 90, 5, 11, 86, 91))));
	}
	
	@Test
	public void testDeletionDistance(){
	  assertEquals(3,deletionDistance("dog", "frog"));	
	}
	
	@Test
	public void teststrStr(){
		assertEquals(-1,strStr("b",	 "baba"));
		assertEquals(48,strStr("bbaabbbbbaabbaabbbbbbabbbabaabbbabbabbbbababbbabbabaaababbbaabaaaba",	 "babaaa"));
		assertEquals(23,strStr("aaaaabbabbaaaababbbbaaabbbaababaababbaabaabaaabbabab","bbbaababaa"));
		assertEquals(13,strStr("aabaaaababaabbbabbabbbaabababaaaaaababaaabbabbabbabbaaaabbbbbbaabbabbbbbabababbaaabbaabbbababbb","bba"));
		
	}
	
	@Test
	public void testLongestSubstringWithoutRepeat(){
		assertEquals("beacd",longestSubstringWithoutRepeat("xabeacdede"));
		assertEquals("abcde",longestSubstringWithoutRepeat("abcde"));
		assertEquals("abc",longestSubstringWithoutRepeat("abcabcbb"));
	}
	
	@Test
	public void testMinWindowInString(){
		assertEquals("BANC",minWindowInString("ADOBECODEBANC", "ABC"));
		assertEquals("",minWindowInString("w", "o"));
		assertEquals("AA",minWindowInString("AAAAAA", "AA"));
		assertEquals("",minWindowInString("rGusddQS6UvK9GzxPSJDMSyoTOpkLK18ZfxKF64HwZ0", "o8athbAkGyGg7B79xJzPZAXmnqw1dWlUMmA3LehdRaXl2S7HVrgmpUvj9m2RtnZggXG9B"));
		assertEquals("",minWindowInString("0mJdGXwLm9AOZ5xA8u92KDYqGJroQ537hoRXjQoUCMOpDYwxoNjexJGWQJAIxSFF3ZbIe27oFxUTJxtv8mORwpuRZn30MDj3kXRW2Ix3lslj7kwmGZPXAKhBW4q5T2BzsqbL0ZETFqRdxVm8GCGfqshvpWzsRvprUcF9vw3VlftqTRzKRzr4zYB2P6C7lg3I7EhGMPukUj8XGGZTXqPqnCqes"
				                           , "rsm2ty04PYPEOPYO5"));
	}
	
	@Test
	public void testGasupProblem(){
		assertEquals(1,canCompleteCircuit(Arrays.asList(1,2), Arrays.asList(2,1) ));
		assertEquals(0,canCompleteCircuit(Arrays.asList(0), Arrays.asList(0) ));
		assertEquals(31,canCompleteCircuit(Arrays.asList( 204, 918, 18, 17, 35, 739, 913, 14, 76, 555, 333, 535, 653, 667, 52, 987, 422, 553, 599, 765, 494, 298, 16, 285, 272, 485, 989, 627, 422, 399, 258, 959, 475, 983, 535, 699, 663, 152, 606, 406, 173, 671, 559, 594, 531, 824, 898, 884, 491, 193, 315, 652, 799, 979, 890, 916, 331, 77, 650, 996, 367, 86, 767, 542, 858, 796, 264, 64, 513, 955, 669, 694, 382, 711, 710, 962, 854, 784, 299, 606, 655, 517, 376, 764, 998, 244, 896, 725, 218, 663, 965, 660, 803, 881, 482, 505, 336, 279 )
											, Arrays.asList( 273, 790, 131, 367, 914, 140, 727, 41, 628, 594, 725, 289, 205, 496, 290, 743, 363, 412, 644, 232, 173, 8, 787, 673, 798, 938, 510, 832, 495, 866, 628, 184, 654, 296, 734, 587, 142, 350, 870, 583, 825, 511, 184, 770, 173, 486, 41, 681, 82, 532, 570, 71, 934, 56, 524, 432, 307, 796, 622, 640, 705, 498, 109, 519, 616, 875, 895, 244, 688, 283, 49, 946, 313, 717, 819, 427, 845, 514, 809, 422, 233, 753, 176, 35, 76, 968, 836, 876, 551, 398, 12, 151, 910, 606, 932, 580, 795, 187) ));
		assertEquals(3,canCompleteCircuit(Arrays.asList(50,20,5,30,25,10,10)
				, Arrays.asList(45,30,10,20,30,10,5) ));
		assertEquals(0,canCompleteCircuit(Arrays.asList(383, 521, 491, 907, 871, 705)
				, Arrays.asList(96, 197, 592, 67, 77, 641) ));
	}
	
	
	@Test
	public void testDungeonsAndKnight(){
		//assertEquals(7,min);
	}
	
	@Test
	public void testJump(){
		assertEquals(2, jump(new ArrayList<Integer>(Arrays.asList(2,3,1,1,4))));
		assertEquals(3, jump(new ArrayList<Integer>(Arrays.asList(33, 21, 50, 0, 0, 46, 34, 3, 0, 12, 33, 0, 31, 37, 15, 17, 34, 18, 0, 4, 12, 41, 18, 35, 37, 34, 0, 47, 0, 39, 32, 49, 5, 41, 46, 26, 0, 2, 49, 35, 4, 19, 2, 27, 23, 49, 19, 38, 0, 33, 47, 1, 21, 36, 18, 33, 0, 1, 0, 39, 0, 22, 0, 9, 36, 45, 31, 4, 14, 48, 2, 33, 0, 39, 0, 37, 48, 44, 0, 11, 24, 16, 10, 23, 22, 41, 32, 14, 22, 16, 23, 38, 42, 16, 15, 0, 39, 23, 0, 42, 15, 25, 0, 41, 2, 48, 28 ))));
	}
	
	
	@Test
	public void testMajorityElement(){
		assertEquals(2,majorityElement(Arrays.asList(2,1,2)));
	}
	
	
	
	
	@Test
	public void testMaximumGap(){
		assertEquals(5, maximumGap(Arrays.asList(1,10,5)));
		assertEquals(6, maximumGap(Arrays.asList(5,2,1,13,15,7)));
	assertEquals(0, maximumGap(Arrays.asList(1,1)));
		assertEquals(1, maximumGap(Arrays.asList(1,1,2)));
		assertEquals(2835941, maximumGap(Arrays.asList(46158044, 9306314, 51157916, 93803496, 20512678, 55668109, 488932, 24018019, 91386538, 68676911, 92581441, 66802896, 10401330, 57053542, 42836847, 24523157, 50084224, 16223673, 18392448, 61771874, 75040277, 30393366, 1248593, 71015899, 20545868, 75781058, 2819173, 37183571, 94307760, 88949450, 9352766, 26990547, 4035684, 57106547, 62393125, 74101466, 87693129, 84620455, 98589753, 8374427, 59030017, 69501866, 47507712, 84139250, 97401195, 32307123, 41600232, 52669409, 61249959, 88263327, 3194185, 10842291, 37741683, 14638221, 61808847, 86673222, 12380549, 39609235, 98726824, 81436765, 48701855, 42166094, 88595721, 11566537, 63715832, 21604701, 83321269, 34496410, 48653819, 77422556, 51748960, 83040347, 12893783, 57429375, 13500426, 49447417, 50826659, 22709813, 33096541, 55283208, 31924546, 54079534, 38900717, 94495657, 6472104, 47947703, 50659890, 33719501, 57117161, 20478224, 77975153, 52822862, 13155282, 6481416, 67356400, 36491447, 4084060, 5884644, 91621319, 43488994, 71554661, 41611278, 28547265, 26692589, 82826028, 72214268, 98604736, 60193708, 95417547, 73177938, 50713342, 6283439, 79043764, 52027740, 17648022, 33730552, 42851318, 13232185, 95479426, 70580777, 24710823, 48306195, 31248704, 24224431, 99173104, 31216940, 66551773, 94516629, 67345352, 62715266, 8776225, 18603704, 7611906)));
	}
	
	
	@Test
	public void testLongestPalindromicSubstring(){
		assertEquals("aaabaaa",longestPalindrome("aaaabaaa"));
		assertEquals("a",longestPalindrome("a"));
		assertEquals("aa",longestPalindrome("aa"));
		assertEquals("aaa",longestPalindrome("aaa"));
		assertEquals("aaaa",longestPalindrome("aaaa"));
		assertEquals("abba",longestPalindrome("abba"));
		assertEquals("aacbbbbcaa",longestPalindrome("cacaccbabcabbbaacbbbbcaaaccaacbabcaccbccaacccaacbbaaabbbabcaaabc"));
	}
	
	@Test
	public void testFullJustifyText(){
		assertEquals(new ArrayList<String>(Arrays.asList( "This    is    an",
				   "example  of text",
				   "justification.  ")), fullJustify(new ArrayList<String>(Arrays.asList("This", "is", "an", "example", "of", "text", "justification.")), 16));
		assertEquals(new ArrayList<String>(Arrays.asList( "What   must   be",
				  "acknowledgment  ",
				  "shall be        ")), fullJustify(new ArrayList<String>(Arrays.asList("What","must","be","acknowledgment","shall","be")), 16));
		assertEquals(new ArrayList<String>(Arrays.asList( "Science  is  what we",
				  "understand      well",
				  "enough to explain to",
				  "a  computer.  Art is",
				  "everything  else  we",
				  "do                  ")), fullJustify(new ArrayList<String>(Arrays.asList("Science","is","what","we","understand","well","enough","to","explain",
				         "to","a","computer.","Art","is","everything","else","we","do")), 20));
		assertEquals(new ArrayList<String>(Arrays.asList( "lkgyyrqh qrdqusnh zyu w ukzxyykeh zmfqafqle e ajq kagjss mihiqla qekf ipxbpz opsndtyu x ec cbd zz yzgx qbzaffgf i atstkrdph jgx qiy jeythmm qgqvyz dfagnfpwat sigxajhjt zx hwmcgss")), fullJustify(new ArrayList<String>(Arrays.asList("lkgyyrqh", "qrdqusnh", "zyu", "w", "ukzxyykeh", "zmfqafqle", "e", "ajq", "kagjss", "mihiqla", "qekf", "ipxbpz", "opsndtyu", "x", "ec", "cbd", "zz", "yzgx", "qbzaffgf", "i", "atstkrdph", "jgx", "qiy", "jeythmm", "qgqvyz", "dfagnfpwat", "sigxajhjt", "zx", "hwmcgss")), 178));
	}
	
	@Test
	public void testLongestCommonPrefix(){
		assertEquals("a" , longestCommonPrefix(new ArrayList<String>(Arrays.asList("a"))));
		assertEquals("a" , longestCommonPrefix(new ArrayList<String>(Arrays.asList("abcdefgh","aefghijk","abcefgh"))));
		assertEquals("abc" , longestCommonPrefix(new ArrayList<String>(Arrays.asList("abcdefgh","abcefgh"))));
		assertEquals("" , longestCommonPrefix(new ArrayList<String>(Arrays.asList("abcdefgh","xbcefgh"))));
	}
	
	
	@Test
	public void testAntiDiagonals(){
		assertEquals(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1)),new ArrayList<Integer>(Arrays.asList(2,4)),new ArrayList<Integer>(Arrays.asList(3,5,7)),new ArrayList<Integer>(Arrays.asList(6,8)),new ArrayList<Integer>(Arrays.asList(9))))
				,diagonal(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1,2,3)), new ArrayList<Integer>(Arrays.asList(4,5,6)),new ArrayList<Integer>(Arrays.asList(7,8,9))))));
		assertEquals(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1)),new ArrayList<Integer>(Arrays.asList(2,3)),new ArrayList<Integer>(Arrays.asList(4))))
				,diagonal(new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1,2)), new ArrayList<Integer>(Arrays.asList(3,4))))));
	}
	
	@Test
	public void testGrayCode(){
		assertEquals(new ArrayList<Integer>(Arrays.asList(0,1,3,2)),grayCode(2));
	}
	
	/*
	 * [], 5 → 2
[], 2 → 1
[1,3,5,6], 7 → 4
[1,3,5,6], 0 → 0
	 */
	
	@Test
	public void testSearchInsert() {
		assertEquals(2,searchInsert(new ArrayList<Integer>(Arrays.asList(1,3,5,6)), 5));
		assertEquals(1,searchInsert(new ArrayList<Integer>(Arrays.asList(1,3,5,6)), 2));
		assertEquals(4,searchInsert(new ArrayList<Integer>(Arrays.asList(1,3,5,6)), 7));
		assertEquals(0,searchInsert(new ArrayList<Integer>(Arrays.asList(1,3,5,6)), 0));
		assertEquals(2,searchInsert(new ArrayList<Integer>(Arrays.asList(1,3,5,6)), 4));
		assertEquals(149,searchInsert(new ArrayList<Integer>(Arrays.asList(3, 4, 18, 19, 20, 27, 28, 31, 36, 42, 44, 71, 72, 75, 82, 86, 88, 97, 100, 103, 105, 107, 110, 116, 118, 119, 121, 122, 140, 141, 142, 155, 157, 166, 176, 184, 190, 199, 201, 210, 212, 220, 225, 234, 235, 236, 238, 244, 259, 265, 266, 280, 283, 285, 293, 299, 309, 312, 317, 335, 341, 352, 354, 360, 365, 368, 370, 379, 386, 391, 400, 405, 410, 414, 416, 428, 433, 437, 438, 445, 453, 457, 458, 472, 476, 480, 485, 489, 491, 493, 501, 502, 505, 510, 511, 520, 526, 535, 557, 574, 593, 595, 604, 605, 612, 629, 632, 633, 634, 642, 647, 653, 654, 656, 658, 686, 689, 690, 691, 709, 716, 717, 737, 738, 746, 759, 765, 775, 778, 783, 786, 787, 791, 797, 801, 806, 815, 820, 822, 823, 832, 839, 841, 847, 859, 873, 877, 880, 886, 904, 909, 911, 917, 919, 937, 946, 948, 951, 961, 971, 979, 980, 986, 993)), 902));
	}
	

	//utils
	
	private ArrayList<Integer> prepareCacheForTest(String testDesc){
		String[] testLine = testDesc.split(" ");
		ArrayList<Integer> res = new ArrayList<Integer>();
		int indx = 0;
		int ops = 0; 
		final int totalOps = Integer.parseInt(testLine[indx++]);
		MyLRUCache lru = new MyLRUCache(Integer.parseInt(testLine[indx++]));
		
		while(ops<totalOps){
			switch(testLine[indx++]) {
			case "S":
				lru.set(Integer.parseInt(testLine[indx++]),Integer.parseInt(testLine[indx++]));
				ops++;
				break;
			case "G":
				res.add(lru.get(Integer.parseInt(testLine[indx++])));
				ops++;
			}
				
		}
		return res;
		
		
	}
	
	/*public TreeNode createTree(List<Integer> nds){
		java.util.ArrayDeque ndq = new java.util.ArrayDeque();
	
		nds.get
		
		for(int i=1;i<nds.size();i++){
			TreeNode curr = new TreeNode(nds.get(i));
			ndq.poll();
			ndq.addLast(curr);
		}
	}*/
	
	
	
	public static  ArrayList<Integer> listFromListNode(ListNode k){
		ArrayList<Integer> res = null;
		
		while(k != null){
			if(res == null){
				res = new ArrayList<Integer>();
			}
			res.add(k.val);
			k = k.next;
		}
		
		return res;
	}
	
	
	
	public static ListNode listNodeFromList(Integer... k){
		
		ListNode resHead = new ListNode(k[0]);
		ListNode res = resHead;
		for(int i=1;i<k.length;i++){
			res.next = new ListNode(k[i]);
			res = res.next;
		}
		
		return resHead;
		
	}
	
public static ListNode listNodeWithCycleFromList(List<Integer> k, int cycleJoinPosition){
		
		ListNode resHead = new ListNode(k.get(0));
		ListNode res = resHead;
		ListNode cycleJoinNode = resHead;
		
		for(int i=1;i<k.size();i++){
			res.next = new ListNode(k.get(i));
			res = res.next;
			if(i==cycleJoinPosition){
				cycleJoinNode = res;
			}
		}
		
		if(cycleJoinPosition >= 0)
		res.next = cycleJoinNode;
		return resHead;
		
	}


//  public static ArrayList<ArrayList<Integer>> listFrom2DArray(int[][] arr){
//	  
//  }
	
	public static void main(String[] args) {
		System.out.println(Arrays.asList("ddd}{fdsa".split("\\{")));
		System.out.println("suman".charAt(0));
		System.out.println(-1 % 20);
		Random rndGen  = new Random();
		int numT = 0;
		int[] cntArr = new int[11]; 
		for(int j=0;j<10;j++){
			for(int i=0;i<10;i++){
				//boolean curr = rndGen.nextBoolean();
				int currInt = rndGen.nextInt(10);
				boolean curr = currInt==0;
				cntArr[currInt]++;
				if(curr)
					numT++;
				//System.out.println("Random nmbers in 10 --> "+currInt+" boolean "+curr);
			}
		}
		System.out.println("Number of Truth "+numT);
		/*for(int k=0;k<10;k++){
			System.out.println(k+" --> "+cntArr[k]);
		}*/
		
		//reservoir sampling 
		ArrayList<Integer> xs = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		int k = 5;
		int[] xsFreqs = new int[xs.size()];
		//Arrays.fill(xsFreqs, 0);
		
		for(int i = 0 ; i< 100;i++){
            List<Integer> kComb = singleCombination2(xs, k);
            //System.out.println(kComb);
            for(Integer x:kComb){
            	//System.out.println(x);
            	xsFreqs[x-1] = xsFreqs[x-1] +1;
            }
		}
		System.out.println(Arrays.toString(xsFreqs));
		System.out.println((int)"AZ".charAt(0));
		
	}
	
	
}
