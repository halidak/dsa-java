package dsa.arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TwoSumTest {

    private final TwoSum solution = new TwoSum();

    /** The order of the two indices doesn't matter, so sort before comparing. */
    private int[] sorted(int[] a) {
        int[] copy = a.clone();
        Arrays.sort(copy);
        return copy;
    }

    @Test
    void example1() {
        assertArrayEquals(new int[] {0, 1}, sorted(solution.twoSum(new int[] {2, 7, 11, 15}, 9)));
    }

    @Test
    void example2() {
        assertArrayEquals(new int[] {1, 2}, sorted(solution.twoSum(new int[] {3, 2, 4}, 6)));
    }

    @Test
    void sameValueTwice() {
        assertArrayEquals(new int[] {0, 1}, sorted(solution.twoSum(new int[] {3, 3}, 6)));
    }

    @Test
    void negativeNumbers() {
        assertArrayEquals(new int[] {0, 2}, sorted(solution.twoSum(new int[] {-3, 4, 3, 90}, 0)));
    }
}
