package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

import org.junit.Test;

 class TimeMap{
	
	
	private class TimeValue{
		public final Integer ts;
		public final String val;
		public TimeValue(Integer ts, String val) {
			super();
			this.ts = ts;
			this.val = val;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(ts);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TimeValue other = (TimeValue) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(ts, other.ts);
		}
		private TimeMap getEnclosingInstance() {
			return TimeMap.this;
		}
		
		
	}
	
	private HashMap<String,ArrayList<TimeValue>> mp;
	
	 public TimeMap() {
	        mp = new HashMap<>();        
	    }
	    
	    public void set(String key, String value, int timestamp) {
	        
	    	ArrayList<TimeValue> currTv =  mp.getOrDefault(key, new ArrayList<>());
	    	currTv.add(new TimeValue(timestamp,value));
	    	mp.put(key, currTv);
	    	
	    	
	    }
	    
	    public String get(String key, int timestamp) {
	    	
	        return !mp.containsKey(key) ? "":  getFloorValue(mp.get(key) , timestamp);
	        
	       
	    }

		private String getFloorValue(ArrayList<TimeValue> tvs, int ts) {
			int lo=0, hi=tvs.size();
			
			while(lo<hi) {
				int mid = (lo+hi)/2;
				if(tvs.get(mid).ts <= ts ) {
					lo=mid+1;
				}else {
					hi = mid;
				}
				
				
			}
			
			if(hi == 0) {
				return "";
			}
			
			return tvs.get(hi-1).val;
		}
	    
	    
	}

	/**
	 * Your TimeMap object will be instantiated and called as such:
	 * TimeMap obj = new TimeMap();
	 * obj.set(key,value,timestamp);
	 * String param_2 = obj.get(key,timestamp);
	 */

 
 public class TimeMapTest{
	 
	 @Test
	 public void testTimeMap() {
		 TimeMap timeMap = new TimeMap();
		 timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
		 
		 
		 assertEquals( "bar", timeMap.get("foo", 1));         // return "bar"
		 assertEquals( "bar" ,timeMap.get("foo", 3));         // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
		 timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
		 
		 assertEquals( "bar2",timeMap.get("foo", 4));         // return "bar2"
		 assertEquals( "bar2",timeMap.get("foo", 5));         // return "bar2"

	 }
	 
 }

