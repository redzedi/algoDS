package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

public class RankTeamByVotes {
	
	//https://leetcode.com/problems/rank-teams-by-votes/description/?envType=company&envId=atlassian&favoriteSlug=atlassian-six-months
	
	private static class MaxVoteCntAndSecuringCandidatesTuple{
		public int voteCnt = -1;
		public  ArrayList<Integer> securingCandidates = new ArrayList<>();
		@Override
		public String toString() {
			return "MaxVoteCntAndSecuringCandidatesTuple [voteCnt=" + voteCnt + ", securingCandidates="
					+ securingCandidates + "]";
		}
		
		
	}
	
	 public String rankTeams(String[] votes) {
		 
		 if(votes.length == 1) {
			 return votes[0];
		 }
	     int numOfCandidates =  votes[0].length();  
	     
	     //HashMap<Character,Integer> candidateIndex = new HashMap<>();
	     HashMap<Integer,Character> candidateReverseIndex = new HashMap<>();
	     
	     for (int i = 0; i < votes[0].length(); i++) {
			//candidateIndex.put(votes[0].charAt(i), i);
			candidateReverseIndex.put(i, votes[0].charAt(i));
		}
	     
//		 int[][] candidateByPositionVoteGrid = new int[numOfCandidates][numOfCandidates];
//		 
//		 for(int i=0;i<candidateByPositionVoteGrid.length;i++) {
//			 Arrays.fill(candidateByPositionVoteGrid[i], 0);
//		 }
		 HashMap<Character,Integer[]> candidateByPositionVoteGridMap = new HashMap<>();
		 
		 for (int i = 0; i < numOfCandidates; i++) {
			Integer[] row = new Integer[numOfCandidates];
			Arrays.fill(row, 0);
			candidateByPositionVoteGridMap.put(candidateReverseIndex.get(i), row);
		}
		 
//		 MaxVoteCntAndSecuringCandidatesTuple[] maxVoteCntPerPositionToSecuringCandidates = new MaxVoteCntAndSecuringCandidatesTuple[numOfCandidates];
//		 
//		 Arrays.parallelSetAll(maxVoteCntPerPositionToSecuringCandidates, (i)->maxVoteCntPerPositionToSecuringCandidates[i]=new MaxVoteCntAndSecuringCandidatesTuple());
//		
		 for(int i=0;i<votes.length;i++) {
			 for(int position=0;position<numOfCandidates;position++) {
				 char currCandidateChar = votes[i].charAt(position);
//				Integer currCandidate = candidateIndex.get(currCandidateChar);
//				 candidateByPositionVoteGrid[currCandidate][position]++;
				 candidateByPositionVoteGridMap.get(currCandidateChar)[position]++;
				 
//				 if(maxVoteCntPerPositionToSecuringCandidates[position].voteCnt == candidateByPositionVoteGrid[currCandidate][position] ) {
//					 //maxVoteCntPerPositionToSecuringCandidates[position].voteCnt = candidateByPositionVoteGrid[currCandidate][position];
//					 if(!maxVoteCntPerPositionToSecuringCandidates[position].securingCandidates.contains(currCandidate))
//					 maxVoteCntPerPositionToSecuringCandidates[position].securingCandidates.add(currCandidate);
//				 }else if(maxVoteCntPerPositionToSecuringCandidates[position].voteCnt < candidateByPositionVoteGrid[currCandidate][position] ) {
//					 maxVoteCntPerPositionToSecuringCandidates[position].voteCnt = candidateByPositionVoteGrid[currCandidate][position];
//					 maxVoteCntPerPositionToSecuringCandidates[position].securingCandidates.clear();
//					 maxVoteCntPerPositionToSecuringCandidates[position].securingCandidates.add(currCandidate);
//				 }
			 }
		 }
		 //evaluation phase
		 
		 //return evaluateOrder1(numOfCandidates, candidateReverseIndex, candidateByPositionVoteGrid);
		 return evaluateOrder2( numOfCandidates,  candidateByPositionVoteGridMap);
	    }
	 
	 private String evaluateOrder2(final int numOfCandidates, HashMap<Character,Integer[]> candidateByPositionVoteGridMap) {
		 
	  return	 candidateByPositionVoteGridMap
		 .entrySet()
		 .stream()
		 .sorted((e1,e2)->{
			 Integer[] arr1 = e1.getValue();
			 Integer[] arr2 = e2.getValue();
			 boolean isResolved = false;
			 int res = 1;
			 for(int i=0;i<numOfCandidates && !isResolved;i++) {
				 if(arr1[i] < arr2[i]) {
					 isResolved = true;
				 }if(arr1[i] > arr2[i]) {
					 res = -1;
					 isResolved = true;
				 }
			 }
			 
			 if(!isResolved) {
				 res = e1.getKey() - e2.getKey();
			 }
			 return res;
		 })
		 .map(e -> e.getKey().toString())
		 .collect(Collectors.joining());
		 
		
	 }
	 
	 private String evaluateOrder1(int numOfCandidates, HashMap<Integer, Character> candidateReverseIndex,
				int[][] candidateByPositionVoteGrid) {
		 HashMap<Character,Integer> candidateRankVal = new HashMap<>();
		 for (int i = 0; i < candidateByPositionVoteGrid.length; i++) {
			 int currVal = 0;
			 for (int j = 0; j < candidateByPositionVoteGrid[0].length; j++) {
				 currVal += Math.pow(numOfCandidates, numOfCandidates - j-1)*candidateByPositionVoteGrid[i][j];
				
			}
			 candidateRankVal.put(candidateReverseIndex.get(i), currVal);
			
		}
		 ArrayList<Map.Entry<Character, Integer>>  xs = new ArrayList<Map.Entry<Character, Integer>>(candidateRankVal.entrySet());
		 Collections.sort( xs , (e1,e2)->e2.getValue()-e1.getValue());
		 
		 return xs.stream().map(Map.Entry::getKey).map(String::valueOf).reduce(String::concat).get();
		 
	 }

	private String evaluateOrder(int numOfCandidates, HashMap<Integer, Character> candidateReverseIndex,
			int[][] candidateByPositionVoteGrid,
			MaxVoteCntAndSecuringCandidatesTuple[] maxVoteCntPerPositionToSecuringCandidates) {
		StringBuilder res = new StringBuilder();
		 
		 for (int i = 0; i < maxVoteCntPerPositionToSecuringCandidates.length; i++) {
			 System.out.println(i+" --> "+maxVoteCntPerPositionToSecuringCandidates[i].securingCandidates);
			if(maxVoteCntPerPositionToSecuringCandidates[i].securingCandidates.size() == 1) {
				res.append(candidateReverseIndex.get(maxVoteCntPerPositionToSecuringCandidates[i].securingCandidates.get(0)));
			}else {
				System.out.println(" tie breaker not yet implemented ");
				boolean isResolved = false;
				for(int position = i+1; position < numOfCandidates && !isResolved;position++) {
					final int p = position;
					MaxVoteCntAndSecuringCandidatesTuple mergedVotes= 	maxVoteCntPerPositionToSecuringCandidates[i].securingCandidates.stream().flatMap(c->Map.of(c,candidateByPositionVoteGrid[c][p]).entrySet().stream()).reduce( new MaxVoteCntAndSecuringCandidatesTuple(), (MaxVoteCntAndSecuringCandidatesTuple a, Entry<Integer,Integer> c)-> {
						//if())
						if(a.voteCnt<c.getValue()) {
							MaxVoteCntAndSecuringCandidatesTuple a1 = new MaxVoteCntAndSecuringCandidatesTuple();
							a1.voteCnt = c.getValue();
							a1.securingCandidates.add(c.getKey());
							return a1;
						}else if(a.voteCnt==c.getValue()) {
							MaxVoteCntAndSecuringCandidatesTuple a1 = new MaxVoteCntAndSecuringCandidatesTuple();
							a1.voteCnt = c.getValue();
							a1.securingCandidates.addAll(a.securingCandidates);
							a1.securingCandidates.add(c.getKey());
							return a1;
						}else {
							return a;
						}
					}, (a1 , a2)->{
						if(a1.voteCnt > a2.voteCnt) {
							return a1;
						}else if(a1.voteCnt < a2.voteCnt) {
							return a2;
						}else {
							MaxVoteCntAndSecuringCandidatesTuple a3 = new MaxVoteCntAndSecuringCandidatesTuple();
							a3.voteCnt = a1.voteCnt;
							a3.securingCandidates.addAll(a1.securingCandidates);
							a3.securingCandidates.addAll(a2.securingCandidates);
							return a3;
						}
					});
					
					if( mergedVotes.securingCandidates.size() == 1) {
						res.append(candidateReverseIndex.get(mergedVotes.securingCandidates.get(0)));
						isResolved = true;
					}
					
				}
				
				//tieBreaker - 2
				
				if( !isResolved) {
				 res.append(	Collections.min(maxVoteCntPerPositionToSecuringCandidates[i].securingCandidates.stream().map(ci -> candidateReverseIndex.get(ci)).collect(Collectors.toList())));
					
				}
				
			}
		}
		 
		 
		 return res.toString();
	}
	 
	 @Test
	 public void testRankTeams() {
		 assertEquals("ACB", rankTeams(new String[] {"ABC","ACB","ABC","ACB","ACB"}));
		 assertEquals("ZMNAGUEDSJYLBOPHRQICWFXTVK", rankTeams(new String[] {"ZMNAGUEDSJYLBOPHRQICWFXTVK"}));
		 assertEquals("XWYZ", rankTeams(new String[] {"WXYZ","XYZW"}));
		 assertEquals("ABC", rankTeams(new String[] {"BCA","CAB","CBA","ABC","ACB","BAC"}));
	 }
	 
	 public static void main(String[] args) {
		System.out.println(Arrays.asList( "ABC".split("")));
		System.out.println("ACB".indexOf("C"));
		System.out.println("ACB".indexOf("C"));
		MaxVoteCntAndSecuringCandidatesTuple i = new MaxVoteCntAndSecuringCandidatesTuple();
		MaxVoteCntAndSecuringCandidatesTuple a4 = Map.of(0,5,1,0,2,5,3,1).entrySet().stream().reduce(i, (MaxVoteCntAndSecuringCandidatesTuple a, Entry<Integer,Integer> c)-> {
			//if())
			if(a.voteCnt<c.getValue()) {
				MaxVoteCntAndSecuringCandidatesTuple a1 = new MaxVoteCntAndSecuringCandidatesTuple();
				a1.voteCnt = c.getValue();
				a1.securingCandidates.add(c.getKey());
				return a1;
			}else if(a.voteCnt==c.getValue()) {
				MaxVoteCntAndSecuringCandidatesTuple a1 = new MaxVoteCntAndSecuringCandidatesTuple();
				a1.voteCnt = c.getValue();
				a1.securingCandidates.addAll(a.securingCandidates);
				a1.securingCandidates.add(c.getKey());
				return a1;
			}else {
				return a;
			}
		}, (a1 , a2)->{
			if(a1.voteCnt > a2.voteCnt) {
				return a1;
			}else if(a1.voteCnt < a2.voteCnt) {
				return a2;
			}else {
				MaxVoteCntAndSecuringCandidatesTuple a3 = new MaxVoteCntAndSecuringCandidatesTuple();
				a3.voteCnt = a1.voteCnt;
				a3.securingCandidates.addAll(a1.securingCandidates);
				a3.securingCandidates.addAll(a2.securingCandidates);
				return a3;
			}
		});
		
		System.out.println(a4);
		
		Character c = 'A';
		
		int[][] xs  = new int[][] { new int[] {1,2} , new int[] {3,4} };
         Arrays.sort(xs, (r1,r2)->{
        	 int res = -1;
        	 for (int j = 0; j < r1.length; j++) {
				
			}
        	 return res;
         });
		System.out.println(Arrays.deepToString(xs));
		
		//String.valueOf
//		MaxVoteCntAndSecuringCandidatesTuple x = Map.of(0,5,1,0,2,5,3,1).entrySet().stream().reduce( new MaxVoteCntAndSecuringCandidatesTuple() , (a,c)->{
//			return a;
//		});
	}
	

}
