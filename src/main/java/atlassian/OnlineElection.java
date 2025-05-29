package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.junit.Test;

//https://leetcode.com/problems/online-election/description/

class TopVotedCandidate {

	TreeMap<Integer,Integer> timMaxVoteCntMap = new TreeMap<>();

    public TopVotedCandidate(int[] persons, int[] times) {
    	
    	HashMap<Integer,Integer> freqMap = new HashMap<>();
    	TreeMap<Integer,LinkedList<Integer>> revFreqMap = new TreeMap<>();
    	
    	for (int i = 0; i < persons.length; i++) {
    		Integer newVCnt = freqMap.compute(persons[i], (p,v)->v==null?1:v+1);
    		final int finI = i;
    		
    			
    			revFreqMap.computeIfPresent(newVCnt-1, (oldVcnt, personsWithVotes )->{
    				personsWithVotes.remove(Integer.valueOf(persons[finI]));
    				return personsWithVotes;
    			});
    		
    			revFreqMap.compute(newVCnt, (vCnt , personsWithVote)->{
    				LinkedList<Integer> res ;
    				if(personsWithVote == null) {
    					res = new LinkedList<>();
    				}else {
    					res = personsWithVote;
    					
    				}
    				res.add(persons[finI]);
    				return res;
    			});
    			
    			this.timMaxVoteCntMap.put(times[i], revFreqMap.lastEntry().getValue().getLast());
		}
        
    }
    
    public int q(int t) {
        return this.timMaxVoteCntMap.get(timMaxVoteCntMap.floorKey(t));
    }
}


public class OnlineElection {
	
	
	@Test
	public void testTopVotedCandidate1() {
		TopVotedCandidate topVotedCandidate = new TopVotedCandidate(new int[] {0, 1, 1, 0, 0, 1, 0}, new int[] {0, 5, 10, 15, 20, 25, 30});
		assertEquals(0,topVotedCandidate.q(3)); // return 0, At time 3, the votes are [0], and 0 is leading.
		assertEquals(1,topVotedCandidate.q(12)); // return 1, At time 12, the votes are [0,1,1], and 1 is leading.
		assertEquals(1,topVotedCandidate.q(25)); // return 1, At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent vote.)
		assertEquals(0,topVotedCandidate.q(15)); // return 0
		assertEquals(0,topVotedCandidate.q(24)); // return 0
		assertEquals(1,topVotedCandidate.q(8)); // return 1
	}
	
	public static void main(String[] args) {
		//System.out.println(  Stream.of(0,1,1,0,0,1,0).red  );
	}

}
