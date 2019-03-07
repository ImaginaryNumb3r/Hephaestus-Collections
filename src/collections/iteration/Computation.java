package collections.iteration;

import essentials.annotations.ToTest;
import essentials.functional.exception.ConsumerEx;
import essentials.functional.exception.FunctionEx;
import essentials.functional.exception.PredicateEx;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * @author Patrick
 * @since 14.01.2018
 * A Computation contains the stateless part methods of an Iteration {@see stream.iteration.Iteration}.
 * Computations do not change the state of the aggregated iterator. In other words, no new Iterator is created.
 * @param <T> The output of the computation.
 *              Note that the Computation has no "in" type parameter because that will be user defined behaviour.
 */
interface Computation<T, X extends Exception> {

    <R> IterationEx<R, X> map(@NotNull FunctionEx<T, R, X> mapper) throws X;

    @ToTest
    <R> IterationEx<R, X> mapIndices(@NotNull BiFunction<T, Integer, R> mapper) throws X;

    IterationEx<T, X> doWhile(@NotNull PredicateEx<T, X> filter) throws X;

    @ToTest
    IterationEx<T, X> doWhile(@NotNull IterationPredicate<T, X> filter) throws X;

    /**
     * Filters out all iteration elements until it has reached the index of the provided parameter.
     * For example, an index of 1 would only return the first element (with index 0).
     * @param start Index of the item where the iteration will stop.
     */
    @ToTest
    IterationEx<T, X> start(int start);

    /**
     * Drops items of the iteration until it has reached the index of the provided parameter.
     * For example, an index of 1 would only return the first element (with index 0)
     * @param end Index of the item where the iteration will stop.
     */
    @ToTest
    IterationEx<T, X> limit(int end);

    @ToTest
    IterationEx<T, X> filter(@NotNull PredicateEx<T, X> predicate) throws X;

    @ToTest
    IterationEx<T, X> filter(@NotNull IterationPredicate<T, X> predicate) throws X;
}
