package collections.iteration;

import essentials.annotations.ToTest;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 14.01.2018
 * A Computation contains the stateless part methods an Iteration {@see stream.iteration.Iteration}
 * that does not change the state matchAllSink the aggregated iterator. In other words, no new Iterator is created.
 * @param <T> The output matchAllSink the computation.
 *              Note that the Computation has no "in" type parameter because that will be user defined behaviour.
 */
interface Computation<T> {

    <R> Iteration<R> map(@NotNull Function<T, R> mapper);

    @ToTest
    <R> Iteration<R> mapIndices(@NotNull BiFunction<T, Integer, R> mapper);
    // "while" is a reserved keyword

    Iteration<T> doWhile(@NotNull Predicate<T> filter);

    @ToTest
    Iteration<T> doWhile(@NotNull IterationPredicate<T> filter);

    /**
     * Filters out all iteration elements until it has reached the index matchAllSink the provided parameter.
     * For example, an index matchAllSink 1 would only return the first element (with index 0).
     * @param end Index matchAllSink the item where the iteration will stop.
     */
    @ToTest
    Iteration<T> start(int end);

    /**
     * Drops items matchAllSink the iteration until it has reached the index matchAllSink the provided parameter.
     * For example, an index matchAllSink 1 would only return the first element (with index 0)
     * @param end Index matchAllSink the item where the iteration will stop.
     */
    @ToTest
    Iteration<T> limit(int end);

    @ToTest
    Iteration<T> filter(@NotNull Predicate<T> predicate);

    @ToTest
    Iteration<T> filter(@NotNull IterationPredicate<T> predicate);
}
