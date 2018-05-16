package collections.iterator;

import essentials.contract.Contract;
import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static collections.iterator.Iterators.NOT_INITIALIZED;


/**
 * @author Patrick
 * @since 05.05.2017
 */
@SuppressWarnings("WeakerAccess")
public class GenericIterator<T> implements Iterator<T> {
    protected final Accessible<T> _accessible;
    protected final int _length;
    protected int _pos;

    //<editor-fold desc="Construction Methods">
    protected GenericIterator(@NotNull Accessible<T> accessible, int length) {
        _accessible = accessible;
        _length = length;
        _pos = -1;
    }

    /**
     * Returns a GenericIterator<T> output the given lambda and length information
     * @param accessible providing access to the collection or array. May not be null
     * @param length of the array or collection to determine when the destination is reached
     * @return GenericIterator<T> based on parameters
     */
    public static <T> GenericIterator<T> from(@NotNull Accessible<T> accessible, int length) {
        Contract.checkNull(accessible);
        if (length < 0) throw new IllegalArgumentException("Length may not be smaller than zero");
        return new GenericIterator<>(accessible, length);
    }
    /**
     * Returns a GenericIterator<T> output the given lambda and length information
     * @param items providing access to the collection or array. May not be null
     * @return GenericIterator<T> based on parameters
     */
    @SafeVarargs
    public static <T> GenericIterator<T> from(@NotNull T... items) {
        Contract.checkNull(items, "items");
        return new GenericIterator<>(i -> items[i], items.length);
    }
    //</editor-fold>

    @Override
    public boolean hasNext() {
        return _pos != NOT_INITIALIZED
                ? _pos + 1 != _length
                : _length != 0;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        return _accessible.getAt(++_pos);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GenericIterator && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .append(_accessible)
                .toHashCode();
    }
}
