package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
	
	record Person(String name, int age) {}
	
	public static void main(String[] args) throws ScriptException {
		localTypeInference();

		
		textBlock();
		
		//doesn't work needs some work to make it work with Graal.js
		//embeddedJSScriptEngine();
		
		instanceofPatternMatching();
		
		switchExpression();
		
		
	}


	private static void switchExpression() {
		System.out.println("String length "+getStringLength("This is a very very very long string"));
		System.out.println("String length "+getStringLength(11));
		System.out.println("String length "+getStringLength(null));
		
		var kid = new Person("kid",1);
		var adult = new Person("adult",19);
		
		System.out.println(kid+" can Vote ? -- "+canVote(kid));
		System.out.println(adult+" can Vote ? -- "+canVote(adult));
	}


	private static boolean canVote(Person kid) {
		boolean canVote = switch(kid) {
		case Person(String name, int age) when age>18 -> true;
		default -> false;
		};
		return canVote;
	}


	private static void instanceofPatternMatching() {
		Object a = "this is actually a string";
		
		if( a instanceof String s) {
			System.out.println("reversed the string "+s.toUpperCase());
		}
	}


	private static int getStringLength(Object a) {
		return switch(a) {
		case String s  -> s.length();
		case null -> -1;
		default ->0;
		};
	}


	private static void textBlock() throws ScriptException {
		String myTextBlock= """
				    <html>
				       <body>
				           <h1>Hello World!!!</h1>
				       </body>
				    </html>
				""";
	
		System.out.println("Printing the html page \n "+myTextBlock);
		
		
	}


	private static void embeddedJSScriptEngine() throws ScriptException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		List<ScriptEngineFactory> engines = new ScriptEngineManager().getEngineFactories();

		System.out.println(engines);
		for (ScriptEngineFactory f : engines) {
		    System.out.println(f.getLanguageName() + " " + f.getEngineName() + " " + f.getNames());
		}
		Object obj = engine.eval("""
		                         function hello() {
		                             print('"Hello, world"');
		                         }
		                         
		                         hello();
		                         """);
	}


	private static void localTypeInference() {
		var xs = Arrays.asList(1,2,3);
	    for(var x:xs) {
	    	System.out.println(x);
	    }

	}

}
