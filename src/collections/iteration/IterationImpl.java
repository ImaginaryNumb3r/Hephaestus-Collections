package collections.iteration;

import collections.iterator.CompositeIterator;
import collections.iterator.Iterators;
import essentials.contract.Contract;
import essentials.util.ComparisonResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * @author Patrick
 * @since 14.01.2018
 */
class IterationImpl<T> implements Iteration<T> {
    private final Iterator<T> _source;
    protected int _index; // = 0;

    public IterationImpl(Iterator<T> source) {
        _source = source;
    }

    //<editor-fold desc="Computation Method">
    @Override
    public <R> Iteration<R> map(@NotNull Function<T, R> mapper) {
        Contract.checkNull(mapper, "mapper");

        MappingOps<T, R> mappingOps = new MappingOps<>(_source, mapper);
        return Iteration.of(mappingOps);
    }

    @Override
    public <R> Iteration<R> mapIndices(@NotNull BiFunction<T, Integer, R> mapper) {
        Contract.checkNull(mapper, "mapper");

        MappingOps<T, R> mappingOps = new MappingOps<>(_source, mapper);
        return Iteration.of(mappingOps);
    }

    @Override
    public Iteration<T> start(int start) {
        Contract.checkNegative(start, "start");

        LongPredicate predicate = index -> index >= start;
        FilterOps<T> filterOps = new FilterOps<>(_source, predicate);
        return Iteration.of(filterOps);
    }

    @Override
    public Iteration<T> limit(int end) {
        Contract.checkNegative(end, "end");

        LongPredicate predicate = index -> index <= end;
        FilterOps<T> filterOps = new FilterOps<>(_source, predicate);
        return Iteration.of(filterOps);
    }

    @Override
    public Iteration<T> doWhile(@NotNull IterationPredicate<T> filter) {
        Contract.checkNull(filter, "filter");

        WhileOps<T> filterOps = new WhileOps<>(_source, filter, true);
        return Iteration.of(filterOps);    }

    @Override
    public Iteration<T> doWhile(@NotNull Predicate<T> filter) {
        Contract.checkNull(filter, "filter");

        WhileOps<T> filterOps = new WhileOps<>(_source, filter, true);
        return Iteration.of(filterOps);
    }

    @Override
    public Iteration<T> filter(@NotNull Predicate<T> predicate) {
        Contract.checkNull(predicate, "end");

        FilterOps<T> filterOps = new FilterOps<>(_source, predicate);
        return Iteration.of(filterOps);
    }

    @Override
    public Iteration<T> filter(@NotNull IterationPredicate<T> predicate) {
        Contract.checkNull(predicate, "end");

        FilterOps<T> filterOps = new FilterOps<>(_source, predicate);
        return Iteration.of(filterOps);
    }
    //</editor-fold>

    //<editor-fold desc="Termination Operation">
    @Override
    public Iteration<T> toDistinct() {
        // Make distinct via HashSet.
        HashSet<T> collection = collect(HashSet::new);
        return Iteration.of(collection.iterator());
    }

    @Override
    public Iteration<T> toSorted(Comparator<T> comparator) {
        // Sort with priority queue.
        PriorityQueue<T> collection = collect(() -> new PriorityQueue<>(comparator));
        return Iteration.of(collection.iterator());
    }

    private <C extends Collection<T>> C collect(Supplier<C> supplier){
        LinearCollector<T, C> collector = LinearCollector.of(supplier);
        return collect(collector);
    }

    @Override
    public <R> Iteration<R> toFlattened(Function<T, Iterable<R>> mapper) {
        // Map all iterables to iterators and concatenate them all in a composite iterator.
        Iterator<Iterator<R>> iterators =
                Iterators.map(_source, iterator -> {
                    Iterable<R> iterable = mapper.apply(iterator);
                    return iterable.iterator();
        });

        CompositeIterator<R> compositeIterator = new CompositeIterator<>(iterators);
        return Iteration.of(compositeIterator);
    }

    @Override
    public Iteration<T> toReverse() {
        // Push items on Stack and pop via Iterator in reverse.
        Stack<T> collection = collect(Stack::new);
        return Iteration.of(collection.iterator());
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> operation) {
        if (_source.hasNext()){
            return Optional.empty();
        }

        T reduce = _source.next();
        while (_source.hasNext()){
            T next = _source.next();
            reduce = operation.apply(reduce, next);
        }

        return Optional.ofNullable(reduce);
    }

    @Override
    public Iteration<T> visitEach(Consumer<T> action) {
        ArrayList<T> iterated = new ArrayList<>();

        while (_source.hasNext()){
            T cur = _source.next();
            action.accept(cur);
            iterated.add(cur);
        }

        return Iteration.of(iterated.iterator());
    }

    @Override
    public void forEach(Consumer<T> action) {
        while (_source.hasNext()){
            action.accept(_source.next());
        }
    }

    @Override
    public <R, A> R collect(Collector<T, A, R> collector) {
        // Get collection from collector.
        A intermediate = collector.supplier().get();

        // Add all values to collection.
        _source.forEachRemaining(item -> collector.accumulator().accept(intermediate, item));

        // If the intermediate collection is the expected end result, cast intermediate. Otherwise call finisher.
        return !collector.characteristics().contains(IDENTITY_FINISH)
                ? collector.finisher().apply(intermediate)
                : (R) intermediate;

    }

    public <R> R collect(LinearCollector<T, R> collector) {
        return collect((Collector<T, R, R>) collector);
    }

    @Override
    public Optional<T> min(Comparator< T> comparator) {
        return desiredExtreme(comparator, ComparisonResult::isSmaller);
    }

    /**
     * Either returns the maximum or minimum element within the iteration.
     * @param comparator which determines the max/min element.
     * @param predicate to determine whether to keep the smaller or greater element.
     * @return the min/max element if it exists.
     */
    private Optional<T> desiredExtreme(Comparator<T> comparator, IntPredicate predicate) {
        if (_source.hasNext()){
            return Optional.empty();
        }

        T desired = _source.next();
        while (_source.hasNext()){
            T cur = _source.next();
            int result = comparator.compare(cur, desired);

            if (predicate.test(result)){
                desired = cur;
            }
        }

        return Optional.of(desired);
    }

    @Override
    public Optional<T> max(Comparator<T> comparator) {
        return desiredExtreme(comparator, ComparisonResult::isGreater);
    }

    @Override
    public long count() {
        while (_source.hasNext()){
            _source.next();
            ++_index;
        }

        return _index;
    }

    //<editor-fold desc="Matching Sinks">
    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        MatchSink.MatchAnySink<T> matchAnySink = new MatchSink.MatchAnySink<>(_source, predicate);
        return matchAnySink.doesMatch();
    }

    @Override
    public boolean anyMatch(IterationPredicate<T> predicate) {
        MatchSink.MatchAnySink<T> matchAnySink = new MatchSink.MatchAnySink<>(_source, predicate);
        return matchAnySink.doesMatch();
    }

    @Override
    public boolean allMatch(Predicate<T> predicate) {
        MatchSink.MatchAllSink<T> matchAllSink = new MatchSink.MatchAllSink<>(_source, predicate);
        return matchAllSink.doesMatch();
    }

    @Override
    public boolean allMatch(IterationPredicate<T> predicate) {
        MatchSink.MatchAllSink<T> matchAllSink = new MatchSink.MatchAllSink<>(_source, predicate);
        return matchAllSink.doesMatch();
    }

    @Override
    public boolean noneMatch(Predicate<T> predicate) {
        MatchSink.MatchAllSink<T> matchNoneSink = new MatchSink.MatchAllSink<>(_source, predicate);
        return !matchNoneSink.doesMatch();
    }

    @Override
    public boolean noneMatch(IterationPredicate<T> predicate) {
        MatchSink.MatchAllSink<T> matchNoneSink = new MatchSink.MatchAllSink<>(_source, predicate);
        return !matchNoneSink.doesMatch();
    }
    //</editor-fold>

    @Override
    public Optional<T> first() {
        return _source.hasNext()
                ? Optional.of(_source.next())
                : Optional.empty();
    }
    //</editor-fold>

    @Override
    public boolean executed() {
        return !_source.hasNext();
    }
}
