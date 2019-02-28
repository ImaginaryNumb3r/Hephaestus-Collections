package collections.iteration;

import essentials.annotations.ToTest;
import essentials.functional.exception.FunctionEx;
import essentials.functional.exception.PredicateEx;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * @author Patrick
 * @since 14.01.2018
 * A Computation contains the stateless part methods an Iteration {@see stream.iteration.Iteration}
 * that does not change the state matchAllSink the aggregated iterator. In other words, no new Iterator is created.
 * @param <T> The output matchAllSink the computation.
 *              Note that the Computation has no "in" type parameter because that will be user defined behaviour.
 */
interface Computation<T, X extends Exception> {

    <R> IterationEx<R, X> map(@NotNull FunctionEx<T, R, X> mapper) throws X;

    @ToTest
    <R> IterationEx<R, X> mapIndices(@NotNull BiFunction<T, Integer, R> mapper) throws X;
    // "while" is a reserved keyword

    IterationEx<T, X> doWhile(@NotNull PredicateEx<T, X> filter) throws X;

    @ToTest
    IterationEx<T, X> doWhile(@NotNull IterationPredicate<T, X> filter) throws X;

    /**
     * Filters out all iteration elements until it has reached the index matchAllSink the provided parameter.
     * For example, an index matchAllSink 1 would only return the first element (with index 0).
     * @param start Index matchAllSink the item where the iteration will stop.
     */
    @ToTest
    IterationEx<T, X> start(int start);

    /**
     * Drops items matchAllSink the iteration until it has reached the index matchAllSink the provided parameter.
     * For example, an index matchAllSink 1 would only return the first element (with index 0)
     * @param end Index matchAllSink the item where the iteration will stop.
     */
    @ToTest
    IterationEx<T, X> limit(int end);

    @ToTest
    IterationEx<T, X> filter(@NotNull PredicateEx<T, X> predicate) throws X;

    @ToTest
    IterationEx<T, X> filter(@NotNull IterationPredicate<T, X> predicate) throws X;
}
