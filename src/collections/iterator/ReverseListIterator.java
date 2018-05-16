package collections.iterator;

import essentials.annotations.Unfinished;
import essentials.contract.NoImplementationException;

import java.util.ListIterator;

/**
 * @author Patrick
 * @since 13.01.2018
 */
class ReverseListIterator<T> implements ListIterator<T> {
    private final ListIterator<T> _listIterator;

    public ReverseListIterator(java.util.ListIterator<T> listIterator) {
        _listIterator = listIterator;
    }

    @Override
    public boolean hasNext() {
        return _listIterator.hasPrevious();
    }

    @Override
    public T next() {
        return _listIterator.previous();
    }

    @Override
    public boolean hasPrevious() {
        return _listIterator.hasNext();
    }

    @Override
    public T previous() {
        return _listIterator.next();
    }

    @Override
    public int nextIndex() {
        return _listIterator.previousIndex();
    }

    @Override
    public int previousIndex() {
        return _listIterator.nextIndex();
    }

    @Override
    public void remove() {
        _listIterator.remove();
    }

    @Override
    public void set(T item) {
        _listIterator.set(item);
    }

    @Override
    @Unfinished
    public void add(T item) {
        // This cannot be purely delegated in logic without braking the expected action.
        throw new NoImplementationException();
    }

}
