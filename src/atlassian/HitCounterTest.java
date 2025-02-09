package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.Test;

class HitCounter {

	private TreeMap<Integer,Integer> tsHitCountMap;
	
    public HitCounter() {
    	tsHitCountMap = new TreeMap<>();
    }
    
    public void hit(int timestamp) {
        tsHitCountMap.compute(timestamp, (ts,cnt)->cnt==null?1:++cnt);
    }
    
    public int getHits(int ts) {
        return tsHitCountMap.subMap(ts>=300?ts-300:0, false, ts, true).values().stream().collect(Collectors.summingInt(c->c));
    }
    
}



public class HitCounterTest{
	
	@Test
	public void testHitCounter1() {
		
		HitCounter hitCounter = new HitCounter();
		hitCounter.hit(1);       // hit at timestamp 1.
		hitCounter.hit(2);       // hit at timestamp 2.
		hitCounter.hit(3);       // hit at timestamp 3.
		
		assertEquals(3,hitCounter.getHits(4));   // get hits at timestamp 4, return 3.
		
		hitCounter.hit(300);     // hit at timestamp 300.
		
		assertEquals(4,hitCounter.getHits(300)); // get hits at timestamp 300, return 4.
		
		assertEquals(3,hitCounter.getHits(301)); // get hits at timestamp 301, return 3.
		
	}
	
	@Test
	public void testHitCounter2() {
		
		HitCounter hitCounter = new HitCounter();
		
		hitCounter.hit(1);       // hit at timestamp 1.
		hitCounter.hit(1);       // hit at timestamp 2.
		hitCounter.hit(1);       // hit at timestamp 3.
		hitCounter.hit(300);       // hit at timestamp 3.
		
		assertEquals(4,hitCounter.getHits(300));   // get hits at timestamp 4, return 3.
			
	}
	
	
}
