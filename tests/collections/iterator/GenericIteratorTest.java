package collections.iterator;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Creator: Patrick
 * Created: 02.08.2017
 * Purpose:
 */
public class GenericIteratorTest {


    @Test
    public void testEmptyArray() {
        final String str = "";

        Iterator<Character> iterator = new GenericIterator<>(str::charAt, str.length());
        assert !iterator.hasNext();

        try {
            iterator.next();
            assert false; // Must throw NoSuchElementException
        } catch (NoSuchElementException ignored){ }
    }

    @Test
    public void testSingletonArray() {
        final Character character = 'c';
        final String str = character.toString();

        Iterator<Character> iterator = new GenericIterator<>(str::charAt, str.length());
        assert iterator.hasNext();

        boolean equals = iterator.next().equals(character);
        assert equals;

        try {
            iterator.next();
            assert false; // Must throw NoSuchElementException
        } catch (NoSuchElementException ignored){ }
    }
}