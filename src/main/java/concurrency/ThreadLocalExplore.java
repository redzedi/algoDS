package concurrency;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalExplore {
	
	private static AtomicInteger ctr = new AtomicInteger(5);
	public static ThreadLocal<Integer> cache = new ThreadLocal<>() {

		@Override
		protected Integer initialValue() {
			// TODO Auto-generated method stub
			return ctr.incrementAndGet();
		}
	    	
	};
	
	static class Holder {
		private int n;
		
		public Holder(int n) {
			this.n = n;
		}
		
		public void assertSanity() {
			System.out.println("called assertSanity");
			if(n!=n)
				throw new AssertionError();
		}
	}
	
	static Holder x;
	public static void main(String[] args) throws InterruptedException {
		Runnable task = ()->{
			try {
				System.out.println(Thread.currentThread()+ ":: Printing from global cache -- "+cache.get());	
			}finally {
				cache.remove();
			}
			
		};
		
		try(ThreadPoolExecutor svx = new ThreadPoolExecutor(1,1,1,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5))){
			 for (int i = 0; i < 5; i++) {
		    	 svx.submit(task);
				
			}
		     
		     svx.awaitTermination(10, TimeUnit.MILLISECONDS);
		}
		
		
	    new Thread(()->{
	        int c=0;
	    	try {
	    	Thread.sleep(1L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        while(c++<1000_000_000) {
//	        
					x = new Holder(42);
				
	        }
	        System.out.println("Writer thread done");
	    }).start();;
	    
	    new Thread(()->{
	        int c=0;
	        while(c++<1000_000) {
//	        	try {
					if(x != null) {
						x.assertSanity();
					}
//					Thread.sleep(1L);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	        }
	        System.out.println("Reader thread done");
	    }).start();;
		
		
	}

}
