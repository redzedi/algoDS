package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

class SnakeGame {
	
	private final int boardWidth;
	private final int boardHeight;
	private final int[][] food;
	private int nextFoodIndex;
	private int score;
	
	private LinkedList<AbstractMap.SimpleEntry<Integer,Integer>> snake;
	

    public SnakeGame(int width, int height, int[][] food) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.food = food;
        score = 0;
        nextFoodIndex = 0;
        snake = new LinkedList<>();
        snake.add(new SimpleEntry<Integer,Integer>(0,0));
    }
    
    public int move(String direction) {
    	
    	if(score == -1 ) {
    		throw new RuntimeException("Game Finished already");
    	}
    	//calculate new head position
    	
    	SimpleEntry<Integer,Integer> newHead = null;
    	
    	switch(direction) {
    	
    	case "L":
    		newHead = new SimpleEntry<Integer,Integer>(snake.getFirst().getKey() , snake.getFirst().getValue()-1);
    		break;
    	case "R":
    		newHead =  new SimpleEntry<Integer,Integer>( snake.getFirst().getKey() ,snake.getFirst().getValue()+1);
    		break;
    	case "U":
    		newHead =  new SimpleEntry<Integer,Integer>( snake.getFirst().getKey()-1 , snake.getFirst().getValue());
    		break;
    	case "D":
    		newHead =  new SimpleEntry<Integer,Integer>( snake.getFirst().getKey()+1 , snake.getFirst().getValue());
    		break;
    		default:
    			throw new RuntimeException("Illegal Move");
    	}
    	
    	//evaluate score and length
    	
    	// is the move legal
    	if( (newHead.getKey()<0 || newHead.getKey()>=boardHeight) 
    			||  (newHead.getValue()<0 || newHead.getValue()>=boardWidth)
    			|| ( snake.contains(newHead) && !snake.getLast().equals(newHead))){
    				score = -1;
    			}
    	
    	if(score == -1) {
    		return score;
    	}
    	 //was there food at the new head pos
    	boolean wasFoodFoundInMove = false;
    	
    	 if( nextFoodIndex<food.length && newHead.getKey() == food[nextFoodIndex][0] && newHead.getValue() == food[nextFoodIndex][1] ) {
    		 score++;
    		 nextFoodIndex++;
    		 wasFoodFoundInMove = true;
    	 }
    	
    	//calculate new tail positon and update board
//    	 System.arraycopy(newHead, 0, currHeadPos, 0, newHead.length);
//    	 board[currHeadPos[0]][currHeadPos[1]]="S";
    	 
    	 if(wasFoodFoundInMove) {
    		 // as the snake grows the tail does not move
    		 snake.addFirst(newHead);
    	 }else {
    		
    		 //snake moves
    		 
    		 if(snake.size() == 1) {
    			 snake.removeFirst();
    			 snake.add(newHead);
    		 }else {
    			 snake.addFirst(newHead);
    			 snake.removeLast();
    		 }
    	
    	 }
    	  
    	return score;
    }
}


public class SnakeGameTest2 {
	
	@Test
	public void testSnakeGame1() {
		
		SnakeGame g = new SnakeGame( 3, 2, new int[][] { new int[] {1, 2}, new int[] {0, 1}});
		
		assertEquals(0,g.move("R"));
		assertEquals(0,g.move("D"));
		assertEquals(1,g.move("R"));
		assertEquals(1,g.move("U"));
		
		assertEquals(2,g.move("L"));
		
		assertEquals(-1,g.move("U"));
	}

	@Test
	public void testSnakeGame2() {
		SnakeGame g = new SnakeGame(3,3, new int[][]{new int[] {2,0},new int[] {0,0},new int[] {0,2},new int[] {0,1},new int[] {2,2}});
	//0,1,1,1,1,2,2,2,2,3,4,4,4	
		assertEquals(0, g.move("D"));
		assertEquals(1, g.move("D"));
		assertEquals(1, g.move("R"));
		assertEquals(1, g.move("U"));
		assertEquals(1, g.move("U"));
		assertEquals(2, g.move("L"));
		assertEquals(2, g.move("D"));
		assertEquals(2, g.move("R"));
		assertEquals(2, g.move("R"));
		assertEquals(3, g.move("U"));
		assertEquals(4, g.move("L"));
		
		assertEquals(4, g.move("L"));
		assertEquals(4, g.move("D"));
		
	}
	
	@Test
	public void testSnakeGame3() {
		
		SnakeGame g = new SnakeGame( 2, 2, new int[][] { new int[] {1, 1}, new int[] {0, 0}});
		
		assertEquals(0,g.move("R"));
		assertEquals(1,g.move("D"));
		assertEquals(1,g.move("L"));
		assertEquals(2,g.move("U"));
		
		
		assertEquals(2,g.move("R"));
	}
	
	@Test
	public void testSnakeGame4() {
		
		SnakeGame g = new SnakeGame( 3,3, new int[][] { new int[] {2,0}, new int[] {0, 0}, new int[] {0, 2},new int[] {0, 1},new int[] {2,2}, new int[] {0, 1}});
		
		assertEquals(0,g.move("D"));
		assertEquals(1,g.move("D"));
		assertEquals(1,g.move("R"));
		assertEquals(1,g.move("U"));
		assertEquals(1,g.move("U"));
		assertEquals(2,g.move("L"));
		assertEquals(2,g.move("D"));
		assertEquals(2,g.move("R"));
		assertEquals(2,g.move("R"));
		assertEquals(3,g.move("U"));
		assertEquals(4,g.move("L"));
		assertEquals(4,g.move("L"));
		assertEquals(4,g.move("D"));
		assertEquals(4,g.move("R"));
		assertEquals(-1,g.move("U"));
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println(Arrays.equals(new int[] {1,2}, new int[] {2,2}));
		
	}
	
}
