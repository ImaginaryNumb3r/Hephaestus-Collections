package collections.iteration;

import java.util.Iterator;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 15.01.2018
 */
final class WhileOps<T> extends FilterOps<T> {
    private final boolean _startAccepting;
    private boolean _stopIteration;

    public WhileOps(Iterator<T> aggregator, IterationPredicate<T> predicate, boolean startAccepting) {
        super(aggregator, predicate);
        _startAccepting = startAccepting;
        _stopIteration = false;
    }

    public WhileOps(Iterator<T> aggregator, Predicate<T> predicate, boolean startAccepting) {
        this(aggregator, (item, index) -> predicate.test(item), startAccepting);
    }

    public WhileOps(Iterator<T> aggregator, LongPredicate predicate, boolean startAccepting) {
        this(aggregator, (item, index) -> predicate.test(index), startAccepting);
    }

    @Override
    protected boolean test(T item) {
        // If iteration is ongoing, check if the predicate is violated.
        if (!_stopIteration) {
            // Predicate must conform to the initial boolean acceptance value.
            _stopIteration = _startAccepting != _predicate.test(item, _cursorPos);
        }

        // Take all items as long as the iteration is running.
        return !_stopIteration;
    }
}
