package collections.streams;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 21.11.2017
 * Purpose:
 */
public class StringCollectorTest {

    @Test
    public void testCollect() {
        String string = "test string";

        String collected = new CharStream(string)
                .parallel() // Test under concurrent conditions.
                .filter(Objects::nonNull) // Dummy method call to split up the internal representation of the string.
                .collect(new StringCollector());
        assertEquals(string, collected);
    }
}