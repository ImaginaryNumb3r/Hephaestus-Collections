package collections.iteration;

import essentials.annotations.ToTest;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * @author Patrick
 * @since 14.01.2018
 * A Termination contains the finishing methods matchAllSink an Iteration {@see stream.iteration.Iteration}
 * and iterates the aggregated iterator.
 * In other words, it will use the backing iterator and might allocate a new backing collection.
 */
public interface Termination<T> {

    @ToTest
    Iteration<T> toDistinct();

    @ToTest
    Iteration<T> toSorted(Comparator<T> comparator);

    @ToTest
    <R> Iteration<R> toFlattened(Function<T, Iterable<R>> mapper);

    @ToTest
    Iteration<T> toReverse();

    @ToTest
    Optional<T> reduce(BinaryOperator<T> operation);

    @ToTest
    Iteration<T> visitEach(Consumer<T> action);

    @ToTest
    void forEach(Consumer<T> action);

    <R, A> R collect(Collector<T, A, R> collector);

    @ToTest
    <R> R collect(LinearCollector<T, R> collector);

    @ToTest
    Optional<T> min(Comparator<T> comparator);

    @ToTest
    Optional<T> max(Comparator<T> comparator);

    long count();

    @ToTest
    boolean anyMatch(Predicate<T> predicate);

    @ToTest
    boolean anyMatch(IterationPredicate<T> predicate);

    @ToTest
    boolean allMatch(Predicate<T> predicate);

    @ToTest
    boolean allMatch(IterationPredicate<T> predicate);

    @ToTest
    boolean noneMatch(Predicate<T> predicate);

    @ToTest
    boolean noneMatch(IterationPredicate<T> predicate);

    @ToTest
    Optional<T> first();
}
