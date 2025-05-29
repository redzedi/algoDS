package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.TreeSet;

import org.junit.Test;

public class NonOverlappingIntervals {

	// https://leetcode.com/problems/non-overlapping-intervals/

	public int eraseOverlapIntervals(int[][] intervals) {
		
		//Arrays.sort(intervals, (i1,i2)-> i1[1]-i1[0]- (i2[1]-i2[0]));
		Arrays.sort(intervals, (i1,i2)-> i1[1]- i2[1]);
		int res =0;
		
		
		TreeSet<Integer[]> startPtOrderedSet = new TreeSet<>((i1,i2)->i1[0]-i2[0]);
		TreeSet<Integer[]> endPtOrderedSet = new TreeSet<>((i1,i2)->i1[1]-i2[1]);
		
		for(int i=0;i<intervals.length;i++) {
			Integer[] currInterval = new Integer[] {intervals[i][0], intervals[i][1]};
			System.out.println("startPtOrderedSet");
			startPtOrderedSet.forEach(p-> System.out.print(Arrays.asList(p)));
			System.out.println("\nendPtOrderedSet");
			endPtOrderedSet.forEach(p-> System.out.print(Arrays.asList(p)));
			System.out.println();
			
			
			if (!(startPtOrderedSet.contains(currInterval) || endPtOrderedSet.contains(
					currInterval)) 
									  && (startPtOrderedSet.headSet(currInterval).size() ==
									  endPtOrderedSet.headSet(currInterval).size())
									 
					&& !isOverlappingWithHeadOrTailSet( startPtOrderedSet,  endPtOrderedSet, currInterval ) ) {
				System.out.println("adding "+ Arrays.asList(currInterval));
				startPtOrderedSet.add(currInterval);
				endPtOrderedSet.add(currInterval);
			}else {
				System.out.println("skipping "+Arrays.asList(currInterval));
				res++;
			}
			
			//search currInterval in the current tree - if not present insert else increment the counter
			
		}
		return res;
	}
	
	private boolean isOverlappingWithHeadOrTailSet(TreeSet<Integer[]> startPtOrderedSet, TreeSet<Integer[]> endPtOrderedSet, Integer[] currInterval ) {
		return ( !startPtOrderedSet.headSet(currInterval).isEmpty() && startPtOrderedSet.headSet(currInterval).last()[1] > currInterval[0])
				|| ( !endPtOrderedSet.tailSet(currInterval).isEmpty() && endPtOrderedSet.tailSet(currInterval).first()[0] < currInterval[1]);
	}
	

	@Test
	public void testEraseOverlapIntervals() {
		assertEquals(1, eraseOverlapIntervals(new int[][] { new int[] {1,2}, new int[] {2,3}, new int[] {3,4}, new int[] {1,3}}));
		assertEquals(2, eraseOverlapIntervals(new int[][] { new int[] {1,2}, new int[] {1,2}, new int[] {1,2}}));
		assertEquals(0, eraseOverlapIntervals(new int[][] { new int[] {1,2}, new int[] {2,3}}));
		assertEquals(2, eraseOverlapIntervals(new int[][] { new int[] {1,100}, new int[] {11,22}, new int[] {1,11}, new int[] {2,12}}));
		assertEquals(0, eraseOverlapIntervals(new int[][] { new int[] {0,1}, new int[] {3,4}, new int[] {1,2}}));
		assertEquals(7, eraseOverlapIntervals(new int[][]{new int[]{-52,31},new int[]{-73,-26},new int[]{82,97},new int[]{-65,-11},new int[]{-62,-49},new int[]{95,99},new int[]{58,95},new int[]{-31,49},new int[]{66,98},new int[]{-63,2},new int[]{30,47},new int[]{-40,-26}}));

		assertEquals(19, eraseOverlapIntervals(new int[][]{new int[]{-25322,-4602},new int[]{-35630,-28832},new int[]{-33802,29009},new int[]{13393,24550},new int[]{-10655,16361},new int[]{-2835,10053},new int[]{-2290,17156},new int[]{1236,14847},new int[]{-45022,-1296},new int[]{-34574,-1993},new int[]{-14129,15626},new int[]{3010,14502},new int[]{42403,45946},new int[]{-22117,13380},new int[]{7337,33635},new int[]{-38153,27794},new int[]{47640,49108},new int[]{40578,46264},new int[]{-38497,-13790},new int[]{-7530,4977},new int[]{-29009,43543},new int[]{-49069,32526},new int[]{21409,43622},new int[]{-28569,16493},new int[]{-28301,34058}}));
	}

}
