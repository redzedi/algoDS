package leetcode;

public class LinkedListCycle {
	/*
	 * https://leetcode.com/problems/linked-list-cycle 
	 */
	
	 public boolean hasCycle(ListNode head) {
	        if(head==null || head.next ==null){
	            return false;
	        }
	        ListNode slowPtr = head;
	        ListNode fastPtr = head.next.next;
	        while(!(slowPtr == null || fastPtr ==null || fastPtr.next ==null || slowPtr==fastPtr)){
	            slowPtr = slowPtr.next;
	            fastPtr = fastPtr.next.next;
	        }
	        return slowPtr == fastPtr;
	        
	    }

}
