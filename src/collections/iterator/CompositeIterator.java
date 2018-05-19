package collections.iterator;

import collections.iteration.IterableEx;
import collections.iteration.IteratorEx;
import collections.iteration.IteratorsEx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Patrick
 * @since 19.01.2018
 */
public class CompositeIterator<T, X extends Exception> implements IteratorEx<T, X> {
    private final Iterator<IteratorEx<T, X>> _iterators;
    private IteratorEx<T, X> _curIterator;
    private IteratorEx<T, X> _nextIterator;

    public CompositeIterator(Iterator<IteratorEx<T, X>> iterators) {
        _iterators = iterators;
    }

    public static <
            T,
            C1 extends IterableEx<T, X>,
            C2 extends Iterable<C1>, X extends Exception
            >
    CompositeIterator<T, X> of(C2 composite) {
        ArrayList<IteratorEx<T, X>> iterables = new ArrayList<>();

        for (C1 iterable : composite) {
            IteratorEx<T, X> iterator = iterable.get();
            iterables.add(iterator);
        }

        Iterator<IteratorEx<T, X>> iterators = iterables.iterator();
        return new CompositeIterator<>(iterators);
    }

    @Override
    public boolean hasNext() {
        if (_curIterator == null){
            initialize();
        }

        return _curIterator.hasNext() || _nextIterator != null;
    }

    private void initialize() {
        _curIterator = _iterators.hasNext()
                ? nextIterator()
                : IteratorsEx.empty(); // Must not be null.

        _nextIterator = _iterators.hasNext()
                ? nextIterator()
                : null; // Signals that there are no more iterators.
    }

    public T next() throws X {
        // Initialize at first call to avoid a call of O(n) within constructor.
        if (_curIterator == null){
            initialize();
        }

        if (!hasNext()){
            throw new NoSuchElementException();
        }

        // Assign next iterator and preload the following iterator after that.
        if (!_curIterator.hasNext()){
            _curIterator = _nextIterator;
            _nextIterator = nextIterator();
        }

        // At this point "curIterator" must have elements.
        return _curIterator.next();
    }

    /**
     * @return An Iterator that contains elements.<br>
     *         Or null if all iterators are exhausted.
     */
    private IteratorEx<T, X> nextIterator() {
        IteratorEx<T, X> iterator = null;

        // Iterate through all iterators and add the next one having elements.
        while (_iterators.hasNext() && iterator == null){
            IteratorEx<T, X> iter = _iterators.next();

            if (iter.hasNext()){
                iterator = iter;
            }
        }

        return iterator;
    }
}
