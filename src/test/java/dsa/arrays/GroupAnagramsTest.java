package dsa.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class GroupAnagramsTest {

    private final GroupAnagrams solution = new GroupAnagrams();

    /** Order of groups and of words inside a group doesn't matter, so sort both before comparing. */
    private List<List<String>> normalize(List<List<String>> groups) {
        List<List<String>> out = new ArrayList<>();
        for (List<String> g : groups) {
            List<String> copy = new ArrayList<>(g);
            copy.sort(null);
            out.add(copy);
        }
        out.sort(Comparator.comparing(Object::toString));
        return out;
    }

    @Test
    void example1() {
        List<List<String>> expected = List.of(
                List.of("ate", "eat", "tea"),
                List.of("nat", "tan"),
                List.of("bat"));
        assertEquals(normalize(expected),
                normalize(solution.groupAnagrams(new String[] {"eat", "tea", "tan", "ate", "nat", "bat"})));
    }

    @Test
    void emptyString() {
        assertEquals(List.of(List.of("")), solution.groupAnagrams(new String[] {""}));
    }

    @Test
    void singleWord() {
        assertEquals(List.of(List.of("a")), solution.groupAnagrams(new String[] {"a"}));
    }

    @Test
    void noAnagrams() {
        assertEquals(
                normalize(List.of(List.of("abc"), List.of("def"))),
                normalize(solution.groupAnagrams(new String[] {"abc", "def"})));
    }
}
