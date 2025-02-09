package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrentLimiting {
	
	static private int busyCnt=1;
	
	
	private static class ScrapingTask implements Runnable{
		
		public final int taskId;
		public final String hostName;
		private final Semaphore s;
		private Random rnd;
		

		public ScrapingTask(int taskId, String hostName, Semaphore s, Random rnd) {
			super();
			this.taskId = taskId;
			this.hostName = hostName;
			this.s = s;
			this.rnd = rnd;
		}



		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			System.out.println("Attempting to start taskID -- "+taskId+" for host -- "+hostName);
			
			try {
				s.acquire();
				//busy
				System.out.println("Processing  taskID -- "+taskId+" for host -- "+hostName);
				
				Thread.sleep(rnd.nextLong(2000));
				
				System.out.println("Processed  taskID -- "+taskId+" for host -- "+hostName);
				
				s.release();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}
	
	public static void main(String[] args) {
		
		try( ExecutorService srv = Executors.newFixedThreadPool(10)){
			Random rnd = new Random();
			
			// siteA tasks
			
			submitTasksFor(srv,rnd ,  "siteA" , 5, 2);
			submitTasksFor(srv, rnd , "siteB" , 8, 8);
			
			
		}
		
	}

	private static void submitTasksFor(ExecutorService srv, Random rnd,  String hostName, int totalTasks, int concLimit) {
		
		Semaphore s1 = new Semaphore(concLimit, true);
		
		for (int i = 0; i < totalTasks; i++) {
			srv.submit(new ScrapingTask(i, hostName, s1, rnd));
		}
	}

}
