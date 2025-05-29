package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.TreeMap;

import org.junit.Test;

class StockPrice {
	
	private TreeMap<Integer,Integer> tsPrice = new TreeMap<>();
	private TreeMap<Integer,Integer> valuesRefCountMap = new TreeMap<>();

    public StockPrice() {
        
    }
    
    /**
     * 
     * @param timestamp
     * @param price
     * 
     * Here stock price might have equal values at various timestamps.
     * The correction that comes at a later point in time might correct(update) 1 timestamp's value.
     * But just based that the entire value can't be removed from the values set as there might be same value at other timestamps
     * and those equal values still should be considered for calculations 
     */
    public void update(int timestamp, int price) {
    	
    	if(tsPrice.containsKey(timestamp)) {
           Integer newRefCount = valuesRefCountMap.computeIfPresent(tsPrice.get(timestamp), (k,v)->v-1);
    		
    		if(newRefCount != null && newRefCount ==0) {
    			valuesRefCountMap.remove(tsPrice.get(timestamp));
    		}
    	}
    		
    		
        tsPrice.put(timestamp, price);
        valuesRefCountMap.compute(price, (k,v)->v==null?1:v+1);

    }
    
    public int current() {
       return tsPrice.get( tsPrice.lastKey());
    }
    
    public int maximum() {
        return valuesRefCountMap.lastKey();
    }
    
    public int minimum() {
        return valuesRefCountMap.firstKey();
    }
    
  
    
    public static void main(String[] args) {
	}
}

public class StockPriceTest{
	  @Test
	    public void testStockPrice() {
	    	StockPrice stonk = new StockPrice();
	    	stonk.update(1, 10);
	    	stonk.update(2,5);
	    	stonk.update(1, 3);
	    	
	    	assertEquals(5 , stonk.maximum());
	    	
	    }
}
