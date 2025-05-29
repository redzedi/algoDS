package leetcode.heap;

import java.util.PriorityQueue;

import leetcode.ListNode;

public class MergeKSortedList {
   	
public ListNode mergeKLists(ListNode[] lists) {
	
	ListNode res = new ListNode();
	ListNode tl = res;
	
	PriorityQueue<ListNode> heads = new PriorityQueue<>((l1,l2)->l1.val-l2.val);
	
	for(int i=0;i<lists.length;i++) {
		if(lists[i] != null)
		heads.add(lists[i]);
	}
	
	while(heads.size() > 1) {
		ListNode currHd = heads.poll();
		if(currHd.next != null)
		heads.add(currHd.next);
		tl.next = currHd;
		tl  = tl.next;
	}
	
	if(!heads.isEmpty()) {
		ListNode currHd = heads.poll();
		tl.next = currHd;
		tl  = tl.next;
	}
	
	
	return res.next;
        
    }
}
