package collections.iteration;

import essentials.contract.Contract;
import essentials.functional.PredicateEx;
import essentials.functional.exception.FunctionEx;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * Creator: Patrick
 * Created: 16.05.2018
 * Purpose:
 */
interface ComputationHelper<T, X extends Exception> extends Computation<T, X> {

    IteratorEx<T, X> source();

    int index();

    void incIndex();

    //<editor-fold desc="Computation Method">
    @Override
    default <R> IterationEx<R, X> map(@NotNull FunctionEx<T, R, X> mapper) {
        Contract.checkNull(mapper, "mapper");

        MappingOps<T, R, X> mappingOps = new MappingOps<>(source(), mapper);
        return IterationEx.of(mappingOps);
    }

    @Override
    default <R> IterationEx<R, X> mapIndices(@NotNull BiFunction<T, Integer, R> mapper) {
        Contract.checkNull(mapper, "mapper");

        MappingOps<T, R, X> mappingOps = new MappingOps<>(source(), mapper);
        return IterationEx.of(mappingOps);
    }

    @Override
    default IterationEx<T, X> start(int start) {
        Contract.checkNegative(start, "start");

        LongPredicate predicate = index -> index >= start;
        FilterOps<T, X> filterOps = new FilterOps<>(source(), predicate);
        return IterationEx.of(filterOps);
    }

    @Override
    default IterationEx<T, X>limit(int end) {
        Contract.checkNegative(end, "end");

        LongPredicate predicate = index -> index <= end;
        FilterOps<T, X> filterOps = new FilterOps<>(source(), predicate);
        return IterationEx.of(filterOps);
    }

    @Override
    default IterationEx<T, X> doWhile(@NotNull IterationPredicate<T, X> filter) throws X {
        Contract.checkNull(filter, "filter");

        WhileOps<T, X> filterOps = new WhileOps<>(source(), filter, true);
        return IterationEx.of(filterOps);    }

    @Override
    default IterationEx<T, X> doWhile(@NotNull PredicateEx<T, X> filter) throws X {
        Contract.checkNull(filter, "filter");

        WhileOps<T, X> filterOps = new WhileOps<>(source(), filter, true);
        return IterationEx.of(filterOps);
    }

    @Override
    default IterationEx<T, X> filter(@NotNull PredicateEx<T, X> predicate) {
        Contract.checkNull(predicate, "end");

        FilterOps<T, X> filterOps = new FilterOps<>(source(), predicate);
        return IterationEx.of(filterOps);
    }

    @Override
    default IterationEx<T, X> filter(@NotNull IterationPredicate<T, X> predicate) {
        Contract.checkNull(predicate, "predicate");

        FilterOps<T, X> filterOps = new FilterOps<>(source(), predicate);
        return IterationEx.of(filterOps);
    }
    //</editor-fold>

}
