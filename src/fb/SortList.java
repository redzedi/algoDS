package fb;

import leetcode.ListNode;

public class SortList {
	
	//https://leetcode.com/problems/sort-list/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public ListNode sortList(ListNode head) {
	       
	       if(head == null){
	          return null;
	       }else if(head.next == null){
	        System.out.println(" single node list "+head.val);
	        return head;
	       }

	        //find mid point
	        ListNode fp=head, sp=head;
	        for(int i=0;fp != null;i++){
	         //    System.out.println(" fp1 --> "+fp.val);
	            if(i>0 && i%2 == 0){
	                sp = sp.next;
	             //   System.out.println(" fp --> "+fp.val+" sp--> "+sp.val);
	            }
	            fp = fp.next;
	        }
	        ListNode mp = sp.next;
	        sp.next = null;

	      //  System.out.println("hd --> "+(head!=null?head.val:null)+" hd.next --> "+head.next.val+" sp--> "+sp.val);
	        ListNode l1 = sortList(head);
	        ListNode l2 = sortList(mp);

	        // merge phase

	        if(l1 == null){
	            return l2;
	        }

	        if(l2 == null){
	            return l1;
	        }
	        // both are not null
	        ListNode merged = new ListNode(1000001);
	        ListNode mergedTl = merged;
	        while(l1 != null && l2 != null){
	            if(l1.val <= l2.val){
	                mergedTl.next = l1;
	                mergedTl = mergedTl.next;
	                l1=l1.next;
	            }else{
	                mergedTl.next = l2;
	                mergedTl = mergedTl.next;
	                l2=l2.next;
	            }
	        }

	         if(l1 != null){
	                mergedTl.next = l1;
	            }else if(l2 != null){
	                mergedTl.next = l2;
	            }

	            return merged.next;



	    }

}
