package concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiceGame {
    private static final int NUM_PLAYERS = 5;
    private static final AtomicBoolean gameEnded = new AtomicBoolean(false);
    private static final Random random = new Random();
    
    // Lock and condition variables for turn management
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition[] playerConditions = new Condition[NUM_PLAYERS];
    private static int currentTurn = 0;
    
    static {
        // Initialize condition variables for each player
        for (int i = 0; i < NUM_PLAYERS; i++) {
            playerConditions[i] = lock.newCondition();
        }
    }
    
    static class Player implements Runnable {
        private final int playerId;
        private final CountDownLatch startSignal;
        
        public Player(int id, CountDownLatch startSignal) {
            this.playerId = id;
            this.startSignal = startSignal;
        }
        
        @Override
        public void run() {
            try {
                startSignal.await();
                
                System.out.println("Starting Player "+playerId);
                while (!gameEnded.get()) {
                    lock.lock();
                    try {
                    	 System.out.println("Entering critical section Player "+playerId);
                        // Wait while it's not this player's turn
                        while (currentTurn != playerId && !gameEnded.get()) {
                            playerConditions[playerId].await();
                        }
                        System.out.println("Going to roll  section Player "+playerId);
                        // Check if game ended while waiting
                        if (gameEnded.get()) {
                            break;
                        }
                        
                        // Roll the dice
                        int roll = random.nextInt(6) + 1;
                        System.out.println("Player " + playerId + " rolled: " + roll);
                        
                        // Check if player won
                        if (roll == 6) {
                            System.out.println("Player " + playerId + " wins!");
                            gameEnded.set(true);
                            // Signal all players to end game
                            signalAllPlayers();
                        } else {
                            // Select next player's turn (can implement arbitrary rules here)
                            selectNextTurn(playerId);
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Example of arbitrary turn selection rules
    private static void selectNextTurn(int playerId) {
        // Rule 1: Random next player
//        int nextPlayer = random.nextInt(NUM_PLAYERS);
//        
//        // Rule 2: Skip next player if random number is even
//        if (random.nextInt(100) % 2 == 0) {
//            nextPlayer = (nextPlayer + 1) % NUM_PLAYERS;
//        }
    	
    	int nextPlayer =(playerId+1)%NUM_PLAYERS;
        
        currentTurn = nextPlayer;
        playerConditions[nextPlayer].signal();
    }
    
    private static void signalAllPlayers() {
        for (Condition condition : playerConditions) {
            condition.signal();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        Thread[] players = new Thread[NUM_PLAYERS];
        
        // Create and start player threads
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players[i] = new Thread(new Player(i, startSignal));
            players[i].start();
        }
        
        System.out.println("Game starting...");
        // Signal all players to start
        startSignal.countDown();
        
        // Wait for all player threads to finish
        for (Thread player : players) {
            player.join();
        }
        
        System.out.println("Game over!");
    }
}
