package collections.iteration;

import essentials.contract.Contract;
import essentials.functional.exception.PredicateEx;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongPredicate;

/**
 * Creator: Patrick
 * Created: 16.05.2018
 * Purpose:
 */
class IterationImpl<T> extends IterationExImpl<T, RuntimeException> implements Iteration<T> {

    public IterationImpl(Iterator<T> source) {
        super(source);
    }

    public static <T, X extends RuntimeException> IterationImpl<T> of(IteratorEx<T, X> iterator){
        return new IterationImpl<>(IteratorEx.toIterator(iterator));
    }

    @Override
    public <R> Iteration<R> map(@NotNull Function<T, R> mapper) {
        Contract.checkNull(mapper, "mapper");

        MappingOps<T, R, RuntimeException> mappingOps = new MappingOps<>(source(), mapper);
        return Iteration.of(mappingOps.toIterator());
    }

    @Override
    public <R> Iteration<R> mapIndices(@NotNull BiFunction<T, Integer, R> mapper) {
        Contract.checkNull(mapper, "mapper");

        MappingOps<T, R, RuntimeException> mappingOps = new MappingOps<>(source(), mapper);
        return Iteration.of(mappingOps.toIterator());
    }

    @Override
    public Iteration<T> doWhile(@NotNull PredicateEx<T, RuntimeException> filter) {
        Contract.checkNull(filter, "filter");

        WhileOps<T, RuntimeException> filterOps = new WhileOps<>(source(), filter, true);
        return Iteration.of(filterOps.toIterator());
    }

    @Override
    public Iteration<T> start(int start) {
        Contract.checkNegative(start, "start");

        LongPredicate predicate = index -> index >= start;
        FilterOps<T, RuntimeException> filterOps = new FilterOps<>(source(), predicate);
        return Iteration.of(filterOps.toIterator());
    }

    @Override
    public Iteration<T> limit(int end) {
        Contract.checkNegative(end, "end");

        LongPredicate predicate = index -> index <= end;
        FilterOps<T, RuntimeException> filterOps = new FilterOps<>(source(), predicate);
        return Iteration.of(filterOps.toIterator());
    }

    @Override
    public Iteration<T> filter(@NotNull PredicateEx<T, RuntimeException> predicate) {
        Contract.checkNull(predicate, "end");

        FilterOps<T, RuntimeException> filterOps = new FilterOps<>(source(), predicate);
        return Iteration.of(filterOps.toIterator());
    }

    @Override
    public Iteration<T> filter(@NotNull IterationPredicate<T, RuntimeException> predicate) {
        Contract.checkNull(predicate, "predicate");

        FilterOps<T, RuntimeException> filterOps = new FilterOps<>(source(), predicate);
        return Iteration.of(filterOps.toIterator());
    }
}
