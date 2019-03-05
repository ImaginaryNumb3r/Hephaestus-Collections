package collections.iterator;

import essentials.annotations.ToTest;
import essentials.contract.Contract;
import essentials.contract.NoImplementationException;
import essentials.datastructure.ListIteratorHelper;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

import static collections.iterator.Iterators.NOT_INITIALIZED;

/**
 * @author Patrick
 * @since 19.05.2017
 */
@ToTest
@SuppressWarnings("WeakerAccess")
public abstract class GenericListIterator<T> extends GenericIterator<T> implements ListIteratorHelper<T> {

    protected GenericListIterator(@NotNull Accessible<T> accessible, int length) {
        super(accessible, length);
    }

    /**
     * Returns the tryPrevious element in the list and moves the cursor
     * position backwards.  This method may be called repeatedly to
     * iterate through the list backwards, or intermixed with calls to
     * {@link #next} to go back and forth.  (Note that alternating calls
     * to {@code tryNext} and {@code tryPrevious} will return the same
     * element repeatedly.)
     *
     * @return the tryPrevious element in the list
     * @throws NoSuchElementException if the iteration has no tryPrevious
     *         element
     */
    @Override
    public T previous() {
        if (!hasPrevious()) throw new NoSuchElementException();
        return _accessible.getAt(_pos--);
    }

    @Override
    public int index() {
        return _pos;
    }


    /**
     * Returns a GenericIterator<I> output the given lambda and length information
     * @param accessible providing access to the collection or array. May not be null
     * @param length of the array or collection to determine when the destination is reached
     * @return GenericIterator<I> based on parameters
     */
    public static <T> GenericListIterator<T> from(@NotNull Accessible<T> accessible, int length) {
        Contract.checkNull(accessible, "accessible");
        Contract.checkNegative(length, "length ");
        return new GenericListIteratorImpl<>(accessible, length);
    }

    /**
     * Returns a GenericIterator<I> output the given lambda and length information
     * @param items providing access to the collection or array. May not be null
     * @return GenericIterator<I> based on parameters
     */
    @Deprecated() // Use ArrayListIterator instead
    public static <T> GenericListIterator<T> from(@NotNull T... items) {
        Contract.checkNull(items, "items");
        return new GenericListIteratorImpl<>(i -> items[i], items.length);
    }

    //<editor-fold desc="Inner Impl Class">
    protected static class GenericListIteratorImpl<I> extends GenericListIterator<I>{

        protected GenericListIteratorImpl(@NotNull Accessible<I> accessible, int length) {
            super(accessible, length);
        }

        /**
         * No implementation
         * @throws UnsupportedOperationException on call
         */
        @Override
        public void remove() {
            if (_pos == NOT_INITIALIZED) throw new IllegalStateException();
            throw new NoImplementationException();
        }

        /**
         * No implementation
         * @throws UnsupportedOperationException on call
         */
        @Override
        public void set(I t) {
            if (_pos == NOT_INITIALIZED) throw new IllegalStateException();
            throw new NoImplementationException();
        }

        /**
         * No implementation
         * @throws UnsupportedOperationException on call
         */
        @Override
        public void add(I t) {
            if (_pos == NOT_INITIALIZED) throw new IllegalStateException();
            throw new NoImplementationException();
        }
    }
    //</editor-fold>
}
