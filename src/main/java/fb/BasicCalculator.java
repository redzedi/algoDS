package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.function.BinaryOperator;

import org.junit.Test;

public class BasicCalculator {


	
	public int calculate(String e) {

		ArrayDeque<Integer> stk = new ArrayDeque<>();
		int currNum =0;
		char op = '+';
		for(int i=0;i<e.length();i++) {
			char currC = e.charAt(i);
			
			if( Character.isWhitespace(currC)) {
				continue;
			}
			
			if(Character.isDigit(currC)) {
				currNum = currNum*10 + (currC-'0');
			}else  {
				 evalAtOp( stk ,  op ,  currNum  );
				currNum = 0 ;
				op = currC;
			}
			
			
		}
		
		// process the last  position 
		evalAtOp( stk ,  op ,  currNum );
		
		int res = 0;
		
		while(!stk.isEmpty()) {
			res += stk.pop();
		}
		
		return res;
		
	}
	
	private void evalAtOp(ArrayDeque<Integer> stk , char op , int currNum  ) {

		if(op == '+') {
			stk.push(currNum);
			
		}else if(op == '-') {
			stk.push(-1*currNum);
			
		}else if(op == '*' ) {
			stk.push(stk.pop()*currNum);
		}else if(op == '/' ) {
			stk.push(stk.pop()/currNum);
		}
		
	}
	
	
	@Test
	public void testCalculate() {
//		assertEquals(7, calculate("3+2*2"));
//		assertEquals(1, calculate(" 3/2 "));
		assertEquals(5, calculate(" 3+5 / 2 "));
		assertEquals(42, calculate("42"));
		assertEquals(1337, calculate("1337"));
		
		assertEquals(-24, calculate("1*2-3/4+5*6-7*8+9/10"));
		
		assertEquals(30 , calculate("23+21/3"));
	}

	@FunctionalInterface
	static interface MyFunc {

		Integer myEval(Integer o1, Integer o2, BinaryOperator<Integer> op);

	}

	public static void main(String[] args) {
		MyFunc fn1 = (o1, o2, fn) -> fn.apply(o1, o2);

		System.out.println(fn1.myEval(3, 2, Math::subtractExact));
		
		System.out.println(Arrays.asList("1*2-3/4+5*6-7*8+9/10".split("[-\\+/*]")));
		System.out.println(Arrays.asList("1*2-3/4+5*6-7*8+9/10".splitWithDelimiters("\\d+",0)));
		System.out.println("13".matches("\\d+"));
	}

}
