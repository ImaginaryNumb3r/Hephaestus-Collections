package collections.iteration;

import java.util.Iterator;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 14.01.2018
 */
class FilterOps<T, X extends Exception> extends ComputationPipe<T, T, X> {
    protected final IterationPredicate<T, X> _predicate;

    public FilterOps(IteratorEx<T, X> aggregator, IterationPredicate<T, X> predicate) {
        super(aggregator);
        _predicate = predicate;
    }

    public FilterOps(IteratorEx<T, X> aggregator, Predicate<T> predicate) {
        this(aggregator, (item, index) -> predicate.test(item));
    }

    public FilterOps(IteratorEx<T, X> aggregator, LongPredicate predicate) {
        this(aggregator, (item, index) -> predicate.test(index));
    }

    @Override
    protected T compute(T item) {
        return item;
    }

    @Override
    protected boolean test(T item) throws X {
        return _predicate.test(item, _cursorPos);
    }
}
