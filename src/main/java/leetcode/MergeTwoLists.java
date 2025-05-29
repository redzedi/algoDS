package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import static leetcode.ListNode.*;

public class MergeTwoLists {
	
	//https://leetcode.com/problems/merge-two-sorted-lists/description/
	
	 public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		 
		 ListNode res = null;
		 ListNode resList = null;
	     
		 while(list1 !=null && list2!=null) {
			 ListNode smallerNode = list1.val < list2.val ? list1:list2;
				 if(resList == null) {
					 res = smallerNode;
					 resList = smallerNode;
				 }else {
					 resList.next = smallerNode;
					 resList = resList.next;
				 }
				if(list1.val < list2.val) {
					list1 = list1.next;
				}else {
					list2 = list2.next;
				}
		 }
		 
		 if(list1 != null) {
			 ListNode smallerNode = list1;
			 if(resList == null) {
				 res = smallerNode;
				 resList = smallerNode;
			 }else {
				 resList.next = smallerNode;
			 }
		 }else if(list2 != null) {
			 ListNode smallerNode = list2;
			 if(resList == null) {
				 res = smallerNode;
				 resList = smallerNode;
			 }else {
				 resList.next = smallerNode;
			 }
		 }
		 
		 return res;
		 
	    }
	 
	 @Test
	 public void testMerge2Lists() {
		 System.out.println(createList(1,2,5));
		 System.out.println(createList(1,3,4));
		 ListNode actualList = mergeTwoLists( createList(1,2,4), createList(1,3,4) );
		 ListNode expectedList = createList( 1,1,2,3,4,4);
		 System.out.println("actual list --> "+actualList);
		 System.out.println("expected list --> "+expectedList);
		 assertEquals( expectedList, actualList);
	 }
	 
	

}
