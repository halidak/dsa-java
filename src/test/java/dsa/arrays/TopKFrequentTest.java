package dsa.arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TopKFrequentTest {

    private final TopKFrequent solution = new TopKFrequent();

    /** The answer can be in any order, so sort before comparing. */
    private int[] sorted(int[] a) {
        int[] copy = a.clone();
        Arrays.sort(copy);
        return copy;
    }

    @Test
    void example1() {
        assertArrayEquals(new int[] {1, 2}, sorted(solution.topKFrequent(new int[] {1, 1, 1, 2, 2, 3}, 2)));
    }

    @Test
    void singleElement() {
        assertArrayEquals(new int[] {1}, solution.topKFrequent(new int[] {1}, 1));
    }

    @Test
    void negativesAndKEqualsDistinct() {
        assertArrayEquals(new int[] {-1, 4}, sorted(solution.topKFrequent(new int[] {4, -1, 4, -1, -1}, 2)));
    }

    @Test
    void mostFrequentIsNotFirst() {
        assertArrayEquals(new int[] {7}, solution.topKFrequent(new int[] {3, 7, 7, 5, 7, 3}, 1));
    }
}
