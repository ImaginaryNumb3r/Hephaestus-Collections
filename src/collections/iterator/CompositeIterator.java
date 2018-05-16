package collections.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Patrick
 * @since 19.01.2018
 */
public class CompositeIterator<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> _iterators;
    private Iterator<T> _curIterator;
    private Iterator<T> _nextIterator;

    public CompositeIterator(Iterator<Iterator<T>> iterators) {
        _iterators = iterators;
    }

    public static <T, C1 extends Iterable<T>, C2 extends Iterable<C1>>
        CompositeIterator<T> of(C2 composite) {
        ArrayList<Iterator<T>> iterables = new ArrayList<>();

        for (C1 iterable : composite) {
            Iterator<T> iterator = iterable.iterator();
            iterables.add(iterator);
        }

        Iterator<Iterator<T>> iterators = iterables.iterator();
        return new CompositeIterator<>(iterators);
    }

    @Override
    public boolean hasNext() {
        if (_curIterator == null){
            initialize();
        }

        return _curIterator.hasNext() || _nextIterator != null;
    }

    private void initialize(){
        _curIterator = _iterators.hasNext()
                ? nextIterator()
                : Iterators.empty(); // Must not be null.

        _nextIterator = _iterators.hasNext()
                ? nextIterator()
                : null; // Signals that there are no more iterators
    }

    public T next() {
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
    private Iterator<T> nextIterator() {
        Iterator<T> iterator = null;

        // Iterate through all iterators and add the next one having elements.
        while (_iterators.hasNext() && iterator == null){
            Iterator<T> iter = _iterators.next();

            if (iter.hasNext()){
                iterator = iter;
            }
        }

        return iterator;
    }
}
