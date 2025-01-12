package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ListNode {
	public int val;
	public ListNode next;

	public ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(next, val);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListNode other = (ListNode) obj;
		return Objects.equals(next, other.next) && val == other.val;
	}
	
	
	
	

	@Override
	public String toString() {
		return   val  + (next==null?"":", "+next) ;
	}

	public static ListNode createList(Integer... xs) {
		 ArrayList<Integer> xss = new ArrayList<>(Arrays.asList(xs));
		 Collections.reverse(xss);
		 return xss.stream().map(ListNode::new).reduce((x,y) -> new ListNode(y.val,x)).get();
	}
}
