package fb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class MovingAverage {
	private int l;
	private int r;
	private double sum = 0.0;
	private int[] xs;
	private int n;

	public MovingAverage(int size) {
		l = -size - 1;
		r = -1;
		xs = new int[size];
		n = size;
	}

	public double next(int val) {
		sum += val;
		
		l = (l + 1);
		if (l >= n) {
			l = l % n;
		}
		if (l >= 0) {
			sum -= xs[l];
		}
		r = (r + 1) % n;
		xs[r] = val;
		if (l >= 0) {
			return sum / n;
		} else {
			return sum / (r + 1.0);
		}
	}

}

public class MovingAverageTest {
	@Test
	public void testMovingAvg() {
		MovingAverage m = new MovingAverage(3);
		assertEquals(1.0, m.next(1), 0.0);
		assertEquals(5.5, m.next(10), 0.0);
	}

	@Test
	public void testMovingAvg1() {
		MovingAverage m = new MovingAverage(1);
		assertEquals(4.0, m.next(4), 0.0);
		assertEquals(0.0, m.next(0), 0.0);
	}

}
