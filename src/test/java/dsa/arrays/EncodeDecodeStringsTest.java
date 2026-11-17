package dsa.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class EncodeDecodeStringsTest {

    private final EncodeDecodeStrings solution = new EncodeDecodeStrings();

    /** Glavni test: decode(encode(lista)) mora da vrati istu listu. */
    private void roundTrip(List<String> input) {
        assertEquals(input, solution.decode(solution.encode(input)));
    }

    // --- encode (prvo ovo) ---

    @Test
    void encodeExample() {
        assertEquals("3#a-b1#c", solution.encode(List.of("a-b", "c")));
    }

    @Test
    void encodeNeetCode() {
        assertEquals("4#neet4#code4#love3#you", solution.encode(List.of("neet", "code", "love", "you")));
    }

    // --- encode + decode ---

    @Test
    void example1() {
        roundTrip(List.of("neet", "code", "love", "you"));
    }

    @Test
    void commaInsideWord() {
        roundTrip(List.of("a,b", "c"));
    }

    @Test
    void hashInsideWord() {
        roundTrip(List.of("#", "1#2"));
    }

    @Test
    void longWordTwoDigitLength() {
        roundTrip(List.of("zdravodrugar", "x"));
    }

    @Test
    void emptyStringInList() {
        roundTrip(List.of("", "a", ""));
    }

    @Test
    void emptyList() {
        roundTrip(List.of());
    }
}
