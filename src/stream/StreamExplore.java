package stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


class MyStream implements Iterable<Integer>{
	private int[] xs;

	public MyStream(int[] xs) {
		super();
		this.xs = xs;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			
			private int idx = 0;

			@Override
			public boolean hasNext() {
				return idx<xs.length;
			}

			@Override
			public Integer next() {
				return xs[idx++];
			}
			
		};
	}
	
	public Spliterator<Integer> spliterator() {
		
		
		return new MySplitIterator(xs,0, xs.length);
	}
	
	private class MySplitIterator implements Spliterator<Integer>{
		
		private int[] a;
		private int currIdx;
		private int fence ;
	
		public MySplitIterator(int[] arr , int currIdx, int fence) {
			super();
			this.a=arr;
			this.currIdx = currIdx;
			this.fence = fence;
			
			System.out.println("Created new MySpliterator with "+currIdx+" fence="+fence);
		}

		@Override
		public boolean tryAdvance(Consumer<? super Integer> action) {
			if(currIdx < fence) {
				action.accept( a[currIdx++]);
				return true;
			}
			return false;
		}

		@Override
		public Spliterator<Integer> trySplit() {
			if(estimateSize()>2) {
				int mid = (currIdx+fence)/2;
				int currFence = fence;
				fence = mid;
				return new MySplitIterator(a,mid,currFence);
			}
			return null;
		}

		@Override
		public long estimateSize() {
			return fence-currIdx;
		}

		@Override
		public int characteristics() {
			return SIZED|SUBSIZED|IMMUTABLE|ORDERED;
		}
		
	
		
	}
	
	
}

 class FJTask extends RecursiveAction{
	 
	 private Spliterator<Integer> itr;
     private Consumer<Integer> act;
	 
	 
	public FJTask(Spliterator<Integer> itr, Consumer<Integer> act) {
		super();
		this.itr = itr;
		this.act = act;
	}



	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		
		Spliterator<Integer> curr = null;
		while( (curr =  itr.trySplit()) !=null) {
			new FJTask(curr,act).fork();
			
			
		}
		
		//invokeAll(ts);
		itr.forEachRemaining(act);
		//while(itr.tryAdvance(act));
		
	}
	 
 }
 
  class TestDefaultParallelStream<T>{
	 
	 private Spliterator<T> itr;

	public TestDefaultParallelStream(Spliterator<T> itr) {
		super();
		this.itr = itr;
	}
	
	public <U> TestDefaultParallelStream<U> myMap(Function<T,U> mpr){
		 return new TestDefaultParallelStream<U>(adaptItr(itr, mpr));
	}
	
	
	public void myForEach(Consumer<T> act) {
		
		MyAction fjt  = new MyAction(itr , act);
		
		fjt.invoke();
		
		 
	 }
	
	private static <T,U> Spliterator<U> adaptItr(Spliterator<T> itr1 , Function<T,U> mpr){
		return new Spliterator<U>() {

			@Override
			public boolean tryAdvance(Consumer<? super U> action) {
				return itr1.tryAdvance(x-> action.accept( mpr.apply(x)));
			}

			@Override
			public Spliterator<U> trySplit() {
				Spliterator<T> curr = itr1.trySplit();
				if(curr != null) {
					return adaptItr(curr,mpr);
				}else {
					return null;
				}
			}

			@Override
			public long estimateSize() {
				return itr1.estimateSize();
			}

			@Override
			public int characteristics() {
				return itr1.characteristics();
			}
			
		};
	}
	
	
	 class MyAction extends RecursiveAction {
		 
		 private static final long serialVersionUID = 1L;
		private Spliterator<T> itr;
		 private Consumer<T> act;
		 
		public MyAction(Spliterator<T> itr, Consumer<T> act) {
			super();
			this.itr = itr;
			this.act = act;
		}
    
		@Override
			protected void compute() {
				Spliterator<T> curr = null;
				ArrayList<MyAction> as  = new ArrayList<>();
				while((curr = itr.trySplit()) != null) {
					
					as.add(new MyAction(curr,act));
				}
				invokeAll(as);
				itr.forEachRemaining(act);
				
			}
			 
		 }
	 
 }


public class StreamExplore {
	
	public static void main(String[] args) {
		
		//exploreStream();
		// exploreSpliterator();
		
		int[] baseArr = new int[] {1,2,3,4,5,6,7,8,9,10};
		MyStream t = new MyStream(baseArr);
		
		 Spliterator<Integer> it2 = t.spliterator();
		 
		 try(ForkJoinPool fw =  new ForkJoinPool(2)){
			 fw.invoke(new FJTask(it2, (i)->{
				 System.out.println(Thread.currentThread()+" printing -- "+i);
			 }));
		 }
		 
		 System.out.println("----- My Stream with parallel forEach---");
		 
		 TestDefaultParallelStream<Integer> ts = new TestDefaultParallelStream<>(t.spliterator());
		 ts.myMap(x->2*x).myForEach(System.out::println);
		 
		
	}


	private static void exploreSpliterator() {
		int[] baseArr = new int[] {1,2,3,4,5,6,7,8,9,10};
		MyStream t = new MyStream(baseArr);
		
		baseArr[0]=100;
		for(Integer i:t) {
			System.out.println(i);
		}
		
		for(Integer i:t) {
			System.out.println(i);
		}
		
	  Spliterator<Integer> it= 	Spliterators.spliteratorUnknownSize(t.iterator(),Characteristics.CONCURRENT.ordinal());
	  
	  System.out.println("--- SplitIterator ---");
	  
	  StreamSupport.stream(it, true).map(x->2*x).forEachOrdered(System.out::println);
	  
	  Spliterator<Integer> it2 = t.spliterator();
	  
	  System.out.println("--- MySplitIterator 1 ---");
	 while( it2.tryAdvance(System.out::println));
	 
	 System.out.println("--- MySplitIterator 2 ---");
	 
	 StreamSupport.stream(t.spliterator(), true).map(x->2*x).forEach((i)->{
		 System.out.println(Thread.currentThread()+" MySplitIterator printing "+i);
	 });
	}
	

	public static void exploreStream() {
		Stream<Integer> numStrm=  Stream.of(1,2,3,4,5).filter((x)->{
		    	System.out.println("** in filter processing -- "+x);
		    	return x%2==0;
		    });
		   
		   System.out.println("None Match --> "+numStrm.noneMatch(x->x>10));
		   
		   //System.out.println(" any even from the stream "+numStrm.limit(2).findAny().get());
		   
		   
		   
		   IntStream.range(1, 1000).parallel().filter((x)->{
		   	System.out.println("**11 in filter processing -- "+x);
		   	return x%2==0;
		   }).takeWhile(x->x<200).forEachOrdered(System.out::println);
		   
		   System.out.println(" factorial 11");
		   System.out.println(IntStream.range(1, 11).parallel().peek(x-> System.out.println(Thread.currentThread()+" processing x "+x)). reduce(1, (x1,x2)->{
			   System.out.println(Thread.currentThread()+" processing x1 --> "+x1+" , x2 --> "+x2);
			   return Math.multiplyExact(x1, x2);
		   }));
		   ;
		   
		 
		   //fibonacci 1
		   long t1 = System.nanoTime();
		  List<Long> fs =  Stream.iterate(List.of(1L, 1L), (xs)-> List.of(xs.get(1), xs.get(0)+xs.get(1))).flatMap((xss)->Stream.of(xss.get(0),xss.get(1))). limit(1000).collect(Collectors.toList());
		   long timeTaken = System.nanoTime() - t1;
		  System.out.println(fs);
		  System.out.println("Time taken --> "+timeTaken);
		  
		  
		  long t2 = System.nanoTime();
		  List<Long> fs1 =  Stream.iterate(new Long[] {1L,1L}, (xs)-> new Long[] {xs[1], xs[0]+xs[1]}).map((xss)->xss[0]). limit(1000).collect(Collectors.toList());
		  long timeTaken1 = System.nanoTime() - t2;
		 System.out.println(fs1);
		 System.out.println("Time taken --> "+timeTaken1);
		 
		 
		 System.out.println(Stream.of(1,2,3,4,5).reduce("", (s,i)->{
			 																 	System.out.println(Thread.currentThread()+" in reduce accumulator "+i+" -- "+s);	
			 																	return s+","+i;}, 
				                                                        (s,s1)->{
				                                                        	System.out.println(Thread.currentThread()+" in reduce combiner "+s+" -- "+s1);
				                                                        	return s.concat(s1);
				                                                        }));
		 
		 //infinite stream
		 Stream.of(1,2,3,4,5).reduce(Stream.empty()	, (s,i)-> Stream.concat(s, Stream.of(" ".repeat(i)+i)), Stream::concat).forEach(System.out::println);
		 
		System.out.println(Stream.of(1,2,3,4,5).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString()); 
		 
		
		//Collector<Integer,Integer[],Integer> mySummingInt = Collector.of(()-> new Integer[] {0}, (acc,i)->{acc[0]+=i;}, (a1,a2)-> {a1[0] += a2[0];} , a->a[0]);
		
		//collector factory
		Collector<Integer,?,Integer> mySummingInt = Collector.of(()->new Integer[]{0}, (a,i)->{a[0]=a[0]+i;}, (a,b)->{a[0]=a[0]+ b[0]; return a;}, a->a[0]);
		
		Collector<Integer,Map<String,List<Integer>>,Map<String,List<Integer>>> myGroupingByOddEven = 
				
				Collector.of(()-> Map.of("even", new ArrayList<Integer>(), "odd", new ArrayList<Integer>()), (mp, i)->{ mp.get(i%2==0?"even":"odd").add(i);}, (m1,m2)->{m1.putAll(m2); return m1;});
				
			
	 System.out.println(Stream.of(1,2,3,4,5).collect(myGroupingByOddEven));	
	 System.out.println(Stream.of(1,2,3,4,5).collect(mySummingInt));	
		 
		   ;
	}

}
