package collections.iteration;

import essentials.annotations.ToTest;
import essentials.functional.exception.PredicateEx;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Creator: Patrick
 * Created: 19.05.2018
 * Purpose:
 */
public interface IterationHelper<T> extends IterationEx<T, RuntimeException> {

    <R> Iteration<R> map(@NotNull Function<T, R> mapper);

    @ToTest
    <R> Iteration<R> mapIndices(@NotNull BiFunction<T, Integer, R> mapper);
    // "while" is a reserved keyword

    @ToTest
    Iteration<T> doWhile(@NotNull PredicateEx<T, RuntimeException> filter);

    /**
     * Filters out all iteration elements until it has reached the index of the provided parameter.
     * For example, an index of 1 would only return the first element (with index 0).
     * @param end Index of the item where the iteration will stop.
     */
    @ToTest
    @Override
    Iteration<T> start(int end);

    /**
     * Drops items of the iteration until it has reached the index of the provided parameter.
     * For example, an index of 1 would only return the first element (with index 0)
     * @param end Index of the item where the iteration will stop.
     */
    @ToTest
    @Override
    Iteration<T> limit(int end);

    @ToTest
    @Override
    Iteration<T> filter(@NotNull PredicateEx<T, RuntimeException> predicate) ;

    @ToTest
    Iteration<T> filter(@NotNull IterationPredicate<T, RuntimeException> predicate);

}
