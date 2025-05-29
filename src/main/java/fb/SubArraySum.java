package fb;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class SubArraySum {

	public int subarraySum(int[] nums, int k) {
		int cnt = 0;

		for (int i = 0; i < nums.length; i++) {

			int currSum = 0;

			for (int j = i; j < nums.length; j++) {
				currSum += nums[j];
				if (currSum == k) {
					cnt++;
				}
			}

		}

		return cnt;
	}

	@Test
	public void testSubArraySum() {
		assertEquals(2, subarraySum(new int[] { 1, 1, 1 }, 2));
		assertEquals(2, subarraySum(new int[] { 1, 2, 3 }, 3));
		assertEquals(0, subarraySum(new int[] { 1 }, 0));
	}

}
