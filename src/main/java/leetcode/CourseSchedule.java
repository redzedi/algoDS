package leetcode;

import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CourseSchedule {

	/*
	 * https://leetcode.com/problems/course-schedule/
	 */

	public boolean canFinish(int numCourses, int[][] prerequisites) {

		HashMap<Integer, List<Integer>> adjLst = new HashMap<>();

		for (int i = 0; i < prerequisites.length; i++) {
			if (!adjLst.containsKey(prerequisites[i][0])) {
				adjLst.put(prerequisites[i][0], new ArrayList<Integer>());
			}

			adjLst.get(prerequisites[i][0]).add(prerequisites[i][1]);
		}
		
		for(Map.Entry<Integer, List<Integer>> e : adjLst.entrySet()) {
			Collections.sort(e.getValue() , (e1,e2)-> adjLst.getOrDefault(e1, Collections.<Integer>emptyList()).size()-adjLst.getOrDefault(e1, Collections.<Integer>emptyList()).size() );
		}

		boolean res = false, cycleDetected = false;
		int maxCycleLength = Integer.MIN_VALUE;

		for (int i = 0; i < numCourses && !cycleDetected; i++) {


				Deque<Integer> visiting = new ArrayDeque<Integer>();
				Deque<Integer> visited = new ArrayDeque<Integer>();

				visiting.push(i);

				while (!visiting.isEmpty() && !cycleDetected) {
					Integer curr = visiting.peek();

					
					boolean childrenToBeProcessed = false;
					if (adjLst.containsKey(curr)) {
						
						for (Integer c : adjLst.get(curr)) {

							if (visiting.contains(c)) {
								// loop detected
								cycleDetected = true;
								break;
							} else if (!visited.contains(c)) {
								childrenToBeProcessed = true;
								visiting.push(c);
								break;
							}
						}
					} 
					
					
					if(!childrenToBeProcessed) {
						visited.offer(visiting.pop());
					}
					

				}

				res = visited.size() == numCourses;
			

		}

		return !cycleDetected;
	}

	@Test
	public void testCanFinish() {

//		assertTrue(canFinish(2, new int[][] { new int[] { 1, 0 } }));
//		assertTrue(!canFinish(2, new int[][] { new int[] { 1, 0 }, new int[] { 0,1 } }));
//		assertTrue(canFinish(1, new int[][] {}));
//		assertTrue(canFinish(2, new int[][] {}));
		
		assertTrue(canFinish(8, new int[][] { new int[]{1,0},new int[]{2,6},new int[]{1,7},new int[]{6,4},new int[]{7,0},new int[]{0,5}}));
	}

}
