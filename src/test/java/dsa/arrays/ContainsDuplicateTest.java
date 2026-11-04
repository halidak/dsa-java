package dsa.arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ContainsDuplicateTest {

    private final ContainsDuplicate solution = new ContainsDuplicate();

    @Test
    void findsDuplicate() {
        assertTrue(solution.containsDuplicate(new int[] {1, 2, 3, 1}));
    }

    @Test
    void allDistinct() {
        assertFalse(solution.containsDuplicate(new int[] {1, 2, 3, 4}));
    }

    @Test
    void edgeCases() {
        assertFalse(solution.containsDuplicate(new int[] {}));
        assertFalse(solution.containsDuplicate(new int[] {7}));
        assertTrue(solution.containsDuplicate(new int[] {-1, -1}));
    }
}
