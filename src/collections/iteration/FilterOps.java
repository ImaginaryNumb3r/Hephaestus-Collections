package collections.iteration;

import java.util.Iterator;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 14.01.2018
 */
class FilterOps<T> extends ComputationPipe<T, T> {
    protected final IterationPredicate<T> _predicate;

    public FilterOps(Iterator<T> aggregator, IterationPredicate<T> predicate) {
        super(aggregator);
        _predicate = predicate;
    }

    public FilterOps(Iterator<T> aggregator, Predicate<T> predicate) {
        this(aggregator, (item, index) -> predicate.test(item));
    }

    public FilterOps(Iterator<T> aggregator, LongPredicate predicate) {
        this(aggregator, (item, index) -> predicate.test(index));
    }

    @Override
    protected T compute(T item) {
        return item;
    }

    @Override
    protected boolean test(T item) {
        return _predicate.test(item, _cursorPos);
    }
}
