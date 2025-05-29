package concurrency;

public class SimpleConcurrency {
	
	private int cnt=1;
	
	public  int incrAndGet(int delta) {
		cnt += delta;
		return cnt;
	}
	
	public static void main(String[] args) {
		SimpleConcurrency s= new SimpleConcurrency();
		Object t = new Object();
		
		synchronized(s) {
			s.incrAndGet(2);	
			  synchronized(t) {
				  System.out.println("holding 2 locks"+ Integer.MAX_VALUE);
			  }
			s.notifyAll();
		}
		
	}

}
