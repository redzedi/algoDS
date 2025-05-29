package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.HashSet;

import org.junit.Test;

public class MinRemoveToMakeValidParentheses {
	
	private enum P{O , C};
	
	record StackElem( P paren , Integer i) {}

	public String minRemoveToMakeValid(String s) {
		
		

		ArrayDeque<StackElem> stk = new ArrayDeque<>();
		HashSet<Integer> toRemove = new HashSet<>();
		StringBuilder res = new StringBuilder();
		
		
		for (int i = 0; i < s.length(); i++) {
		  if('(' == s.charAt(i) ) {
			 stk.push(new StackElem(P.O , i));
		  }if(')' == s.charAt(i) ) {
			  if(stk.isEmpty()) {
				  toRemove.add(i);
			  }else {
				  stk.pop();
			  }
		  }
		}
		
		while(!stk.isEmpty()) {
			toRemove.add(stk.pop().i());
		}
		
		for (int i = 0; i < s.length(); i++) {
			if(!toRemove.contains(i)) {
				res.append(s.charAt(i));
			}
		}
		
		
		return res.toString();
	}
	
	@Test
	public void testMinRemoveToMakeValid() {
		assertEquals("lee(t(c)o)de", minRemoveToMakeValid("lee(t(c)o)de)"));
		assertEquals("ab(c)d", minRemoveToMakeValid("a)b(c)d"));
		assertEquals("", minRemoveToMakeValid("))(("));
	}

}
