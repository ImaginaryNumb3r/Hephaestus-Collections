package collections.iterator;

import collections.iteration.Iteration;
import org.junit.Test;

import java.util.ListIterator;

import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 28.02.2019
 * Purpose:
 */
public class IteratorsTest {

    @Test
    public void testStringIterator() {
        String testString = "my string";

        ListIterator<Character> iterator = Iterators.of(testString);
        boolean allMatch = Iteration.of(iterator)
                .allMatch((Character ch, long index) -> testString.charAt((int) index) == ch);

        assertTrue(allMatch);
    }
}