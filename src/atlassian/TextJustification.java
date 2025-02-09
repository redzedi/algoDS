package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class TextJustification {
	
	//https://leetcode.com/problems/text-justification/description/?envType=company&envId=atlassian&favoriteSlug=atlassian-six-months
	
	 public List<String> fullJustify(String[] words, int maxWidth) {
	     
		 int currWordIdx = 0;
		 
		 ArrayList<String> lines = new ArrayList<String>();
		 
		 while(currWordIdx<words.length) {
			 
			 ArrayList<String> currLineWords = new ArrayList<>();
			 
			 int currLineCapacity = maxWidth;
			 
			 boolean hasSpaceInCurrentLine = true;
			 while(currWordIdx<words.length && hasSpaceInCurrentLine ) {
				 
				 if( currLineCapacity - words[currWordIdx].length()>=0) {
					 currLineWords.add(words[currWordIdx]);
					 currLineCapacity -= words[currWordIdx].length() ;
					 
					 if(currLineCapacity > 0 && currWordIdx<words.length-1) {
						 //reserve capacity for following whitespace
						 currLineCapacity--;
					 }
					 currWordIdx++;
					 
				 }else {
					 hasSpaceInCurrentLine = false;
				 }
				
			 }
			 
			 lines.add(formLineFromWords1(maxWidth , currLineWords, !(currWordIdx<words.length)));
			 
		 }
		 
		 return lines;
	    }

	
	private String formLineFromWords1(int maxWidth , ArrayList<String> currLineWords, boolean isLastLine) {
		
		StringBuilder lineStr = new StringBuilder();
		
        StringBuilder[] spaceBetweenWordsArr = new StringBuilder[currLineWords.size()-1];
		boolean isAllocationDone = spaceBetweenWordsArr.length ==0;
		int currSpaceIdx = 0;
		
		int totalWordCharCount = currLineWords.stream().collect(Collectors.summingInt(String::length));
		int totalNumOfWhiteSpacesInLine = maxWidth - totalWordCharCount;
		
		int allocatedWhiteSpacesBetweenWords = 0;
		
		int trailingSpaceCount = 0;
		
		while(!isAllocationDone) {
		  if(spaceBetweenWordsArr[currSpaceIdx] == null) {
			  spaceBetweenWordsArr[currSpaceIdx] = new StringBuilder();
		  }
		  
		  spaceBetweenWordsArr[currSpaceIdx].append(' ');
 		  
		  allocatedWhiteSpacesBetweenWords++;
		  
		  isAllocationDone = (isLastLine && allocatedWhiteSpacesBetweenWords == spaceBetweenWordsArr.length) || (allocatedWhiteSpacesBetweenWords == totalNumOfWhiteSpacesInLine);
		  
		  currSpaceIdx = (currSpaceIdx+1)%spaceBetweenWordsArr.length;
		}
		
		trailingSpaceCount = totalNumOfWhiteSpacesInLine - allocatedWhiteSpacesBetweenWords;
		
		for(int i=0,j=0;i<currLineWords.size();i++,j++) {
			lineStr.append(currLineWords.get(i));
			
			if(i<currLineWords.size()-1)
			lineStr.append(spaceBetweenWordsArr[j].toString());
		}
		
		lineStr.repeat(' ', trailingSpaceCount);
		
		
		return lineStr.toString();
	}
	 
	
	@Test
	public void testFullJustify() {
		assertEquals(Arrays.asList( "This    is    an",
				   "example  of text",
				   "justification.  "), fullJustify(new String[] {"This", "is", "an", "example", "of", "text", "justification."}, 16));
		assertEquals(Arrays.asList( "Science  is  what we","understand      well","enough to explain to","a  computer.  Art is","everything  else  we","do                  "), fullJustify(new String[] {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"}, 20));
		assertEquals(Arrays.asList("What   must   be","acknowledgment  ","shall be        "), fullJustify(new String[] {"What","must","be","acknowledgment","shall","be"}, 16));
	}
	
	@Test
	public void testStrLst() {
		assertEquals(Arrays.asList("  ","a"),Arrays.asList(" ","a"));
	}
	
	
	public static void main(String[] args) {
		System.out.println(Math.round(1.2));//1
		System.out.println(Math.round(1.5));
		System.out.println(Math.ceil(1.2)); // 2.0
		System.out.println((int)Math.round( Math.floor(1.6))); // 1.0
		
		System.out.println(new int[0]);
	}
	 

}
