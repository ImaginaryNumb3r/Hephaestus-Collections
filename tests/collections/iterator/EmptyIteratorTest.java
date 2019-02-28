package collections.iterator;

import org.junit.Test;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Creator: Patrick
 * Created: 02.08.2017
 * Purpose:
 */
public class EmptyIteratorTest {

    @Test
    public void testNext() {
        ListIterator<Object> iter = new EmptyIterator<>();
        assert !iter.hasNext();
        try {
            iter.next();
            assert false;
        } catch (NoSuchElementException ignore){ }
    }

    @Test
    public void testPrevious() {
        ListIterator<Object> iter = new EmptyIterator<>();
        assert !iter.hasPrevious();
        try {
            iter.previous();
            assert false;
        } catch (NoSuchElementException ignore){ }
    }

    @Test
    public void testIllegalStateException() {
        ListIterator<String> iterator = EmptyIterator.get();

        try{
            iterator.set(null);
            assert false;
        } catch (IllegalStateException ignore){ }

        try{
            iterator.remove();
            assert false;
        } catch (IllegalStateException ignore){ }

        try{
            iterator.add(null);
            assert false;
        } catch (IllegalStateException ignore){ }
    }
}