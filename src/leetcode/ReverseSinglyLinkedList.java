package leetcode;

public class ReverseSinglyLinkedList {
	
	
	/**
	 * https://leetcode.com/problems/reverse-linked-list/submissions/
	 * 
	 * @param head
	 * @return
	 */
	
	public ListNode reverseList(ListNode head) {
        if(head == null){
            return null;
        }else if(head.next == null){
            return head;
        }else{
            ListNode prev = head.next;
            ListNode curr = head;
            curr.next = null;
            for(;prev!= null;){
                ListNode tmp = prev.next;
                prev.next = curr;
                curr = prev;
                prev = tmp;

            }
            return curr;
        }
        
        
    }

}
