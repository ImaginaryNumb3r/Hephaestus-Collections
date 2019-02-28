package collections.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Creator: Patrick
 * Created: 02.08.2017
 * Purpose:
 */
public class GenericListIteratorTest {
    private static final String[] STRINGS = {"Item1", "Item2", "Item3", "Item4", "Item5"};

    private static GenericListIterator<String> get(){
        return new GenericListIterator.GenericListIteratorImpl<>(i -> STRINGS[i], STRINGS.length);
    }

    @Test
    public void testIteration() {
        GenericListIterator<String> iterator = get();
        assert Iterators.equals(iterator, STRINGS);
    }

    @Test
    public void testEmptyArray() { //noinspection MismatchedReadAndWriteOfArray
        Integer[] emptyArray = new Integer[0];
        GenericListIterator<Integer> iterator = new GenericListIterator.GenericListIteratorImpl<>(i -> emptyArray[i], emptyArray.length);
        assert !iterator.hasNext();
        assert !iterator.hasPrevious();

        try {
            iterator.next();
            assert false; // Must throw NoSuchElementException
        } catch (NoSuchElementException ignored){ }

        try {
            iterator.previous();
            assert false; // Must throw NoSuchElementException
        } catch (NoSuchElementException ignored){ }
    }

    @Test
    public void testSingletonArray() {
        final int element = 1;
        Integer[] integers = {element};

        GenericListIterator<Integer> iterator = new GenericListIterator.GenericListIteratorImpl<>(i -> integers[i], integers.length);
        assert iterator.hasNext();

        boolean equals = iterator.next() == element;
        assert equals;

        assert iterator.hasPrevious();

        try {
            iterator.next();
            assert false; // Must throw NoSuchElementException
        } catch (NoSuchElementException ignored){ }

        equals = iterator.previous() == element;
        assert equals;

        try {
            iterator.previous();
            assert false; // Must throw NoSuchElementException
        } catch (NoSuchElementException ignored){ }
    }

    @Test
    public void testIllegalStateException() {
        GenericListIterator<String> iterator = get();

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