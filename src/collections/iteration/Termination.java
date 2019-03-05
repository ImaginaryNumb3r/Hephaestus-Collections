package collections.iteration;

import essentials.annotations.ToTest;
import essentials.functional.exception.BinaryOperatorEx;
import essentials.functional.exception.ConsumerEx;
import essentials.functional.exception.FunctionEx;
import essentials.functional.exception.PredicateEx;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * @author Patrick
 * @since 14.01.2018
 * A Termination contains the finishing methods of an Iteration {@see stream.iteration.Iteration}
 * and iterates the aggregated iterator.
 * In other words, it will use the backing iterator and might allocate a new backing collection.
 */
public interface Termination<T, X extends Exception> {

    @ToTest
    IterationEx<T, X> toDistinct() throws X ;

    @ToTest
    IterationEx<T, X> toSorted(Comparator<T> comparator);

    @ToTest
    <R> IterationEx<R, X> toFlattened(FunctionEx<T, Supplier<IteratorEx<R, X>>, X> mapper) throws X;

    @ToTest
    IterationEx<T, X> toReverse() throws X;

    @ToTest
    Optional<T> reduce(BinaryOperatorEx<T, X> operation) throws X;

    /**
     * A terminating method which performs an action for all remaining items in the iteration.
     * Furthermore, a new iteration with a new list of the just iterated values is returned.
     * @param action the performed action for each value.
     * @return New Iteration, containing all just visited items in a newly allocated collection.
     */
    @ToTest
    IterationEx<T, X> visitEach(ConsumerEx<T, X> action) throws X;

    @ToTest
    void forEach(ConsumerEx<T, X> action) throws X;

    <R, A> R collect(Collector<T, A, R> collector);

    @ToTest
    <R> R collect(LinearCollector<T, R> collector);

    @ToTest
    Optional<T> min(Comparator<T> comparator) throws X;

    @ToTest
    Optional<T> max(Comparator<T> comparator) throws X;

    long count() throws X;

    @ToTest
    boolean anyMatch(PredicateEx<T, X> predicate) throws X;

    @ToTest
    boolean anyMatch(IterationPredicate<T, X> predicate) throws X;

    @ToTest
    boolean allMatch(PredicateEx<T, X> predicate) throws X;

    @ToTest
    boolean allMatch(IterationPredicate<T, X> predicate) throws X;

    @ToTest
    boolean noneMatch(PredicateEx<T, X> predicate) throws X;

    @ToTest
    boolean noneMatch(IterationPredicate<T, X> predicate) throws X;

    @ToTest
    boolean isDistinct() throws X;

    @ToTest
    Optional<T> first() throws X;
}
