package atlassian;

import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import org.junit.Test;


//https://leetcode.com/problems/word-search/description/?envType=company&envId=atlassian&favoriteSlug=atlassian-six-months

public class WordSearch {
   	private static class Point{
		private final int x;
		private final int y;
		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}
		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
		
		
		
		
	}
	
	public boolean exist(char[][] board, String word) {
		boolean found = false;
		for (int i = 0; !found && i < board.length; i++) {
			for (int j = 0; !found && j < board[i].length; j++) {
				
				if(board[i][j] == word.charAt(0)) {
					//Arrays.
					ArrayDeque<ArrayList<Point>> st = new ArrayDeque<>();
					ArrayList<Point> initialPath = new ArrayList<>();
					initialPath.add( new Point(i,j));
				    st.push(initialPath);
				    
				    while(!st.isEmpty()) {
				    	ArrayList<Point> currPath = st.pop();
				    	if(currPath.size() == word.length() ) {
				    		found = true;
				    		break;
				    	}
				    	Point currPoint = currPath.getLast();
				    	// add right
				    	if(currPoint.y < board[i].length-1) {
				    		Point candidatePoint = new Point(currPoint.x, currPoint.y+1);
				    		addCandidatePathIfApplicable(board, word, st, currPath, candidatePoint);
				    	}
				    	
				    	// add left
				    	if(currPoint.y > 0) {
				    		Point candidatePoint = new Point(currPoint.x, currPoint.y-1);
				    		addCandidatePathIfApplicable(board, word, st, currPath, candidatePoint);
				    	}
				    	// add down
				    	if(currPoint.x < board.length-1) {
				    		Point candidatePoint = new Point(currPoint.x+1, currPoint.y);
				    		addCandidatePathIfApplicable(board, word, st, currPath, candidatePoint);
				    	}
				    	// add up
				    	if(currPoint.x > 0) {
				    		Point candidatePoint = new Point(currPoint.x-1, currPoint.y);
				    		addCandidatePathIfApplicable(board, word, st, currPath, candidatePoint);
				    	}
				    }
					
						
					}
				
				}
				
			}
		
		
		return found;
	}

	private void addCandidatePathIfApplicable(char[][] board, String word, ArrayDeque<ArrayList<Point>> st,
			ArrayList<Point> currPath, Point candidatePoint) {
		if((word.charAt(currPath.size()) == board[candidatePoint.x][candidatePoint.y]) && (! currPath.contains(candidatePoint))) {
			ArrayList<Point> candidatePath = new ArrayList<>(currPath);
			//candidatePath.addAll(currPath);
			candidatePath.add(candidatePoint);
			//System.out.println("adding candidatePath "+candidatePath);
			st.push(candidatePath);
		}
		
	}
	
	
	@Test
	public void testWordSearch() {
		assertTrue( exist(new char[][] { new char[]{'A','B','C','E'},new char[]{'S','F','C','S'},new char[]{'A','D','E','E'}}, "ABCCED") );
		assertTrue( !exist(new char[][] { new char[]{'A','A','A','A','A','A'},new char[]{'A','A','A','A','A','A'},new char[]{'A','A','A','A','A','A'},new char[]{'A','A','A','A','A','A'},new char[]{'A','A','A','A','A','A'},new char[]{'A','A','A','A','A','A'}}, "AAAAAAAAAAAAAAa") );
		
		
	}
	
}