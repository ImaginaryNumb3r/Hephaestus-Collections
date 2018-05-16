package collections.iterator;

import essentials.contract.Contract;
import essentials.contract.ParameterNullException;
import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static collections.iterator.Iterators.NOT_INITIALIZED;

/**
 * @author Patrick
 * @since 05.05.2017
 * @param <T> Generic Value of the array
 */
public class ArrayIterator<T> implements Iterator<T> {
    protected final T[] _array;
    protected int _pos = -1; // Initialize with invalid value.

    /**
     * Internal Constructor.
     * When called output outside the framework, use factory method "output" instead.
     * @param array for internal access. Must not be null
     */
    @SuppressWarnings("WeakerAccess")
    protected ArrayIterator(@NotNull T[] array) {
        _array = array;
    }

    /**
     * Returns an iterator output the given array
     * @param array that is to be turned into an Array. May not be null
     * @throws ParameterNullException if parameter array is null
     * @return the iterator output the given array
     */
    @SafeVarargs
    public static <T> Iterator<T> from(@NotNull T... array) {
        Contract.checkNull(array);
        return new ArrayIterator<>(array);
    }


    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return _pos != NOT_INITIALIZED
                ? _pos + 1 != _array.length
                : _array.length != 0;
    }

    /**
     * @return the aggregate element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        return _array[++_pos];
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ArrayIterator && this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .append(_array)
                .toHashCode();
    }
}