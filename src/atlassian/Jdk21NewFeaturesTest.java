package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

import org.junit.Test;

public class Jdk21NewFeaturesTest {
	
	
	  class  MyLRUCache<K,V> extends LinkedHashMap<K, V>{
		  
		  private final int fixedSize;
		  

		public MyLRUCache(int sz) {
			super(sz, 0.75f,true);
			this.fixedSize = sz;
		}

		@Override
		protected boolean removeEldestEntry(Entry<K, V> eldest) {
			
			System.out.println(" remove the eldest entry "+eldest+" current size -->"+size());
			
			
			return size()>this.fixedSize;
		}
		
	}
	
	@Test
	public void testLRU1() {
		
		MyLRUCache<String,String> cache = new MyLRUCache<>(5);
		
		cache.put("k1", "v1");
		cache.put("k2", "v2");
		cache.put("k3", "v3");
		cache.put("k4", "v4");
		cache.put("k5", "v5");
		
		
		assertEquals("v1", cache.get("k1"));
		assertEquals("v2", cache.get("k2"));
		assertEquals("v3", cache.get("k3"));
		assertEquals("v4", cache.get("k4"));
		
		cache.put("k6", "v6");
		
		assertEquals(null, cache.get("k5"));
		assertEquals("v6", cache.get("k6"));
		assertEquals(5, cache.size());
		
	}
	
	
	@Test
	public void testVirtualThreads() throws InterruptedException {
		//
		
		//ExecutorService extSvc =   Executors.newFixedThreadPool(100)
		try(ExecutorService extSvc =Executors.newVirtualThreadPerTaskExecutor()){
			
			//ExecutorService extSvc =   Executors.newVirtualThreadPerTaskExecutor();
			RandomGenerator rndGen = RandomGeneratorFactory.getDefault().create();;
			IntStream.range(0, 10000).forEach((i)->{
				extSvc.submit(()->{
					int jj =i;
					try {
						Thread.sleep(rndGen.nextInt(1)*1000 );
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(i+" thread running "+Thread.currentThread());
				});
			});
		}
		
		
		//extSvc.awaitTermination(5, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) {
		
	}

}
