package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.function.BinaryOperator;

import org.junit.Test;

public class BasicCalculator {

	@FunctionalInterface
	interface MyFunc1 {

		String myEval(String o1, String o2, BinaryOperator<Integer> op);

	}

	public int calculate(String s) {

		String[] exps = s.splitWithDelimiters("\\d+",0);

		ArrayDeque<String> s1 = new ArrayDeque<>();
		ArrayDeque<String> s2 = new ArrayDeque<>();

		MyFunc1 fn1 = (ss1, ss2, op) -> String.valueOf(op.apply(Integer.parseInt(ss1), Integer.parseInt(ss2)));

		for (int i = exps.length - 1; i >= 0; i--) {
			if(!exps[i].isBlank())
			s1.push(exps[i].strip());
		}

		// * /  pass

		while (!s1.isEmpty()) {

			String curr = s1.pop();

			switch (curr) {
			case "*":
				s1.push(fn1.myEval(s1.pop(), s2.pop(), Math::multiplyExact));
				break;
			case "/":
				s1.push(fn1.myEval(s2.pop(), s1.pop(), Math::divideExact));
				break;
			default:
				s2.push(curr);
//				if(!s2.isEmpty() && s2.peek().matches("\\d+") && curr.matches("\\d+") ) {
//					s2.push(s2.pop()+curr);	
//				}else {
//					s2.push(curr);
//				}
				
			}

		}

		if (s2.size() == 1) {
			return Integer.parseInt(s2.pop());
		}

		while (!s2.isEmpty()) {
			s1.push(s2.pop());
		}

		while (!s1.isEmpty()) {

			String curr = s1.pop();

			switch (curr) {
			case "+":
				s1.push(fn1.myEval(s1.pop(), s2.pop(), Math::addExact));
				break;
			case "-":
				s1.push(fn1.myEval(s2.pop(), s1.pop(), Math::subtractExact));
				break;
			default:
				s2.push(curr);
//				if(!s2.isEmpty() && s2.peek().matches("\\d+") && curr.matches("\\d+") ) {
//					s2.push(s2.pop()+curr);	
//				}else {
//					s2.push(curr);
//				}
			}

		}

		return Integer.parseInt(s2.pop());

	}
	
	
	@Test
	public void testCalculate() {
//		assertEquals(7, calculate("3+2*2"));
//		assertEquals(1, calculate(" 3/2 "));
		assertEquals(5, calculate(" 3+5 / 2 "));
		assertEquals(42, calculate("42"));
		assertEquals(1337, calculate("1337"));
		
		assertEquals(-24, calculate("1*2-3/4+5*6-7*8+9/10"));
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
