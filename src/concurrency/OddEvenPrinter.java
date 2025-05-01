package concurrency;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import stream.StreamExplore;

public class OddEvenPrinter {
	
	private static ReentrantLock l1 = new ReentrantLock();
	private static ReentrantLock l2 = new ReentrantLock();
	
	private static int i1 = 0;
	private static int i2 = 0;
	
	public static void main(String[] args) throws InterruptedException {
		oddEvenThreadPrinter();
		
		//stampedLockTest();
		
		
		//writeLockConditionsTest();
		
		// testSleepDoesNotYield();
		
		//predicateTest();
		
		System.out.println(Integer.toHexString(System.identityHashCode("suman")));
		
		//jobQWorkers(); 
		
		//deadlock test
		
		//reentrancyTest();
		
		int[] sortedArray = {1, 3, 5, 8, 12, 16, 20};
        int searchValue = 9;
        
        System.out.println(Arrays.binarySearch(sortedArray, 9));
        
        Stream.of(1,2,3,4,5).takeWhile((x)->x<3).forEach(System.out::println);;
        
       //System.out.println(Stream.of(1,2,3,4,5).takeWhile((x)->x<3).collect(Collectors.toList())); 
		
       StreamExplore.exploreStream();
       
//       numStrm.forEach((x)->{
//    	   System.out.println("In the terminal op1 "+x);
//       });
     
//     System.out.println(IntStream
//    		    .iterate(1, i -> i+1)
//    		    .parallel()
//    		    .peek(i -> { if(i != 1) LockSupport.parkNanos(1_000_000_000); })
//    		    .flatMap(n -> IntStream.iterate(n, i -> i+n))
//    		    .limit(100_000_000)
//    		    .sum()
//    		);
       
      Object lk = new Object();
      
      Thread t1 = new Thread(()->{
    	    synchronized(lk) {
    	    	try {
    	    		System.out.println("acquired lock in t1");
					Thread.sleep(3000);
					System.out.println("t1 done");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
      });
      
      Thread t2 = new Thread(()->{
    	  System.out.println("going to acquire lock in t2");
  	    synchronized(lk) {
  	    	 System.out.println("acquired lock in t2");
  	    	try {
  	    		System.out.println("acquired lock in t2 -- 1");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					System.out.println("t2 interrupted");
					e.printStackTrace();
				}
  	    }
    });
      
      t1.start();
      t2.start();
      
      Thread.sleep(500);
      
      System.out.println("Goint to interrupt t2");
      t2.interrupt();
      Function<Void,Boolean> f1 = (v)->{
    	  System.out.println("f1 called");
    	  return true; 
      };
      System.out.println(true| f1.apply(null));
      
      "abc".chars().forEach(c->System.out.println(String.format("%c", c)));
      
      t1.join();
      
      ArrayDeque<Integer> t = new ArrayDeque<>();
      
      
			
		}

	private static void reentrancyTest() {
		t1();
		t1_old();
	}
	
	private static void t1() {
		try {
			l1.lock();
			System.out.println(Thread.currentThread()+ " In method t1 " + i1);
			if (i1++ < 5) {
				t2();
			} 
		} finally {
			l1.unlock();

		}
			}
	private static void t2() {
		try {
			l2.lock();
			System.out.println(Thread.currentThread()+ " In method t2 " + i2);
			if (i2++ < 5) {
				t1();
			} 
		} finally {
			l2.unlock();

		}
	}
	
	private static Object ol1 = new Object();
	private static Object ol2 = new Object();
	
	private static int oi1 = 0;
	private static int oi2 = 0;
	
	private static void t1_old() {
			synchronized(ol1) {
				System.out.println(Thread.currentThread()+ " In method t1_old " + oi1);
				if (oi1++ < 5) {
					t2_old();
				} 
			}
		}
	
	
	private static void t2_old() {
		synchronized(ol2) {
			System.out.println(Thread.currentThread()+ " In method t2_old " + oi2);
			if (oi2++ < 5) {
				t1_old();
			} 
		}
	}


	private static void jobQWorkers() {
		ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5, true);
		try(ExecutorService es = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS, workQueue)) {
			;
			Function<Integer, Runnable> taskFactory = (n) -> () -> {
				System.out.println("Executing task " + n);
				
				//System.out.println("Exiting task " + n);
			};
			for (int i = 0; i < 16; i +=2) {

				es.submit(taskFactory.apply(i));
				es.submit(taskFactory.apply(i+1));
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						

			} 
		}
	}
		
		
		//es.awaitTermination(100, TimeUnit.MILLISECONDS);
	
		
		
		
		
	
	
	private static void predicateTest() {
		
		Predicate<Void> pv = (o)->{
		    System.out.println(" within the predicate function with arg "+o);	
			return true;
		};
		
		System.out.println("trying the predicate funciton "+pv.test(null));
		
	}

	private static void testSleepDoesNotYield() throws InterruptedException {
		ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
		Condition wCond =  rw.writeLock().newCondition();
		
		Runnable writeTask = ()->{
			rw.writeLock().lock();
			    try {
			    	System.out.println("acquired write lock -- awaiting");
			    	Thread.currentThread().sleep(3000);
			    	System.out.println("acquired write lock -- waking up");
					wCond.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    System.out.println("In write lock action!!");
			rw.writeLock().unlock();
		};
		
		Runnable readTask = ()->{
			System.out.println("acquired read lock -- going to acquire lock");
			rw.readLock().lock();
			System.out.println("acquired read lock -- awaiting");
			    System.out.println("In read lock action!!");
			rw.readLock().unlock();
		};
		
		Thread t1 = new Thread(writeTask);
		Thread t2 = new Thread(readTask);
		
		t1.start();
		
		//Thread.sleep(500);
		
		t2.start();
		
		t2.join();
		
		System.out.println(" finished the read task");
		
		
		rw.writeLock().lock();
	    wCond.signal();
     	rw.writeLock().unlock();
		
		
		t1.join();
	}

	private static void writeLockConditionsTest() throws InterruptedException {
		ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
		
		Condition c = rw.writeLock().newCondition();
		
		Runnable testTask = ()->{
		    rw.writeLock().lock();
		      
		    System.out.println(Thread.currentThread()+" Acquired Write lock -- going to sleep !!");
		       try {
		    	   c.await();
		    	   
				//Thread.sleep(2000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       
		       System.out.println(Thread.currentThread()+"In write lock after sleep !!");
		      c.signal();
		    rw.writeLock().unlock();
		};
		ArrayList<Thread> ts = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
		   Thread t = new Thread(testTask);
		   ts.add(t);
		   t.start();
		   //Thread.sleep(50);
		   System.out.println("Added task "+i);
		}
		System.out.println("Going to start the signalling ");
		rw.writeLock().lock();
		   c.signal();
		rw.writeLock().unlock();
		for(Thread t : ts) {
			t.join();
		}
	}

	private static void stampedLockTest() {
		StampedLock rw = new StampedLock();
		Random rnd = new Random();
		
		CyclicBarrier cb = new CyclicBarrier(100);
		
		Runnable rwLockTask = ()->{
			
			  try {
				  
				  cb.await();
					long readLockStamp = rw.readLock();
					 System.out.println(Thread.currentThread()+"acquired read lock");
				Thread.sleep(rnd.nextLong(1000));
				  System.out.println(Thread.currentThread()+"trying to acquire the write lock");
				  long writeStamp = rw.tryConvertToWriteLock(readLockStamp);
				  if(writeStamp != 0L) {
					  
					  System.out.println(Thread.currentThread()+"acquired write lock");
					  
					  rw.unlockWrite(writeStamp);
				  }else {
					  rw.unlockRead(readLockStamp);
				  }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		
		 
		  for (int i = 0; i < 100; i++) {
			
			  new Thread(rwLockTask).start();
			  System.out.println("added task "+i);
		}
	}

	private static void oddEvenThreadPrinter() throws InterruptedException {
		final int LIMIT = 100;
		ReentrantLock l = new ReentrantLock();
		Condition e = l.newCondition();
		Condition o = l.newCondition();
		
		
		Runnable evenPrinter = ()->{
			int i=0;
			l.lock();
			while(i<LIMIT) {
				
				try {
					e.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(Thread.currentThread()+" -- i "+i);
				i +=2;
				o.signal();
			}
			l.unlock();
			System.out.println("Finishing evenPointer");
		};
		
		Runnable oddPrinter = ()->{
			int i=1;
			l.lock();
			while(i<LIMIT) {
				
				try {
					o.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(Thread.currentThread()+" -- i "+i);
				i +=2;
				e.signal();
			}
			l.unlock();
			System.out.println("Finishing oddPointer");
		};
		
		Thread t1 = new Thread(evenPrinter);
		Thread t2 = new Thread(oddPrinter);
		
		t1.start();
		t2.start();
		
		l.lock();
		e.signal();
		l.unlock();
		
		t1.join();
		t2.join();
	}

}
