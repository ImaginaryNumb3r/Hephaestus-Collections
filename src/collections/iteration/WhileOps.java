package collections.iteration;

import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 15.01.2018
 */
final class WhileOps<T, X extends Exception> extends FilterOps<T, X> {
    private final boolean _startAccepting;
    private boolean _stopIteration;

    public WhileOps(IteratorEx<T, X> aggregator, IterationPredicate<T, X> predicate, boolean startAccepting) {
        super(aggregator, predicate);
        _startAccepting = startAccepting;
        _stopIteration = false;
    }

    public WhileOps(IteratorEx<T, X> aggregator, Predicate<T> predicate, boolean startAccepting) {
        this(aggregator, (item, index) -> predicate.test(item), startAccepting);
    }

    public WhileOps(IteratorEx<T, X> aggregator, LongPredicate predicate, boolean startAccepting) {
        this(aggregator, (item, index) -> predicate.test(index), startAccepting);
    }

    @Override
    protected boolean test(T item) throws X {
        // If iteration is ongoing, check if the predicate is violated.
        if (!_stopIteration) {
            // Predicate must conform to the initial boolean acceptance value.
            _stopIteration = _startAccepting != _predicate.test(item, _cursorPos);
        }

        // Take all items as long as the iteration is running.
        return !_stopIteration;
    }
}
