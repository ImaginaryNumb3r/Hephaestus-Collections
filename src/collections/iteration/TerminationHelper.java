package collections.iteration;

import collections.iterator.CompositeIterator;
import essentials.comparison.RichComparator;
import essentials.functional.exception.BinaryOperatorEx;
import essentials.functional.exception.ConsumerEx;
import essentials.functional.exception.FunctionEx;
import essentials.functional.exception.PredicateEx;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * Creator: Patrick
 * Created: 16.05.2018
 * Purpose:
 */
interface TerminationHelper<T, X extends Exception> extends Termination<T, X> {

    IteratorEx<T, X> source();

    int index();

    void incIndex();

    //<editor-fold desc="Termination Operation">
    @Override
    default IterationEx<T, X> toDistinct() {
        // Make distinct via HashSet.
        HashSet<T> collection = collect(HashSet::new);
        return IterationEx.of(collection.iterator());
    }

    @Override
    default IterationEx<T, X> toSorted(Comparator<T> comparator) {
        // Sort with priority queue.
        PriorityQueue<T> collection = collect(() -> new PriorityQueue<>(comparator));
        return IterationEx.of(collection.iterator());
    }

    private <C extends Collection<T>> C collect(Supplier<C> supplier){
        LinearCollector<T, C> collector = LinearCollector.of(supplier);
        return collect(collector);
    }

    @Override
    default <R> IterationEx<R, X> toFlattened(FunctionEx<T, Supplier<IteratorEx<R, X>>, X> mapper) {
        // Map all iterables to iterators and concatenate them all in a composite iterator.

        IteratorEx<IteratorEx<R, X>, X> map = IteratorsEx.map(source(), (T item) -> {
            Supplier<IteratorEx<R, X>> apply = mapper.apply(item);
            return apply.get();
        });

        CompositeIterator<R, X> compositeIterator = new CompositeIterator<>(map.toIterator());
        return IterationEx.of(compositeIterator);
    }

    @Override
    default IterationEx<T, X> toReverse() {
        // Push items on Stack and pop via Iterator in reverse.
        Stack<T> collection = collect(Stack::new);
        return IterationEx.of(collection.iterator());
    }

    @Override
    default Optional<T> reduce(BinaryOperatorEx<T, X> operation) throws X {
        if (source().hasNext()){
            return Optional.empty();
        }

        T reduce = source().next();
        while (source().hasNext()){
            T next = source().next();
            reduce = operation.apply(reduce, next);
        }

        return Optional.ofNullable(reduce);
    }

    @Override
    default IterationEx<T, X> visitEach(ConsumerEx<T, X> action) throws X{
        ArrayList<T> iterated = new ArrayList<>();

        while (source().hasNext()){
            T cur = source().next();
            action.accept(cur);
            iterated.add(cur);
        }

        return IterationEx.of(iterated.iterator());
    }

    @Override
    default void forEach(ConsumerEx<T, X> action) throws X {
        while (source().hasNext()){
            action.accept(source().next());
        }
    }

    @Override
    default <R, A> R collect(Collector<T, A, R> collector) {
        // Get collection from collector.
        A intermediate = collector.supplier().get();

        // Add all values to collection.
        source().forEach(item -> collector.accumulator().accept(intermediate, item));

        // If the intermediate collection is the expected end result, cast intermediate. Otherwise call finisher.
        return !collector.characteristics().contains(IDENTITY_FINISH)
                ? collector.finisher().apply(intermediate)
                : (R) intermediate;

    }

    default <R> R collect(LinearCollector<T, R> collector) {
        return collect((Collector<T, R, R>) collector);
    }

    @Override
    default Optional<T> min(Comparator< T> comparator) throws X {
        return desiredExtreme(comparator, RichComparator::isSmaller);
    }

    /**
     * Either returns the maximum or minimum element within the iteration.
     * @param comparator which determines the max/min element.
     * @param predicate to determine whether to keep the smaller or greater element.
     * @return the min/max element if it exists.
     */
    private Optional<T> desiredExtreme(Comparator<T> comparator, IntPredicate predicate) throws X {
        if (source().hasNext()){
            return Optional.empty();
        }

        T desired = source().next();
        while (source().hasNext()){
            T cur = source().next();
            int result = comparator.compare(cur, desired);

            if (predicate.test(result)){
                desired = cur;
            }
        }

        return Optional.of(desired);
    }

    @Override
    default Optional<T> max(Comparator<T> comparator) throws X {
        return desiredExtreme(comparator, RichComparator::isGreater);
    }

    @Override
    default long count() throws X {
        while (source().hasNext()){
            source().next();
            incIndex();
        }

        return index();
    }

    //<editor-fold desc="Matching Sinks">
    @Override
    default boolean anyMatch(PredicateEx<T, X> predicate) throws X {
        MatchSink.MatchAnySink<T, X> matchAnySink = new MatchSink.MatchAnySink<>(source(), predicate);
        return matchAnySink.doesMatch();
    }

    @Override
    default boolean anyMatch(IterationPredicate<T, X> predicate) throws X {
        MatchSink.MatchAnySink<T, X> matchAnySink = new MatchSink.MatchAnySink<>(source(), predicate);
        return matchAnySink.doesMatch();
    }

    @Override
    default boolean allMatch(PredicateEx<T, X> predicate) throws X {
        MatchSink.MatchAllSink<T, X> matchAllSink = new MatchSink.MatchAllSink<>(source(), predicate);
        return matchAllSink.doesMatch();
    }

    @Override
    default boolean allMatch(IterationPredicate<T, X> predicate) throws X {
        MatchSink.MatchAllSink<T, X> matchAllSink = new MatchSink.MatchAllSink<>(source(), predicate);
        return matchAllSink.doesMatch();
    }

    @Override
    default boolean noneMatch(PredicateEx<T, X> predicate) throws X {
        MatchSink.MatchAllSink<T, X> matchNoneSink = new MatchSink.MatchAllSink<>(source(), predicate);
        return !matchNoneSink.doesMatch();
    }

    @Override
    default boolean noneMatch(IterationPredicate<T, X> predicate) throws X {
        MatchSink.MatchAllSink<T, X> matchNoneSink = new MatchSink.MatchAllSink<>(source(), predicate);
        return !matchNoneSink.doesMatch();
    }

    @Override
    default boolean isDistinct() throws X {
        HashSet<T> set = new HashSet<>();

        int count = 0;
        IteratorEx<T, X> iterator = source();
        while (iterator.hasNext()) {
            set.add(iterator.next());
            ++count;
        }

        return count == set.size();
    }
    //</editor-fold>

    @Override
    default Optional<T> first() throws X {
        return source().hasNext()
                ? Optional.of(source().next())
                : Optional.empty();
    }
    //</editor-fold>

    default boolean executed() {
        return !source().hasNext();
    }
}
