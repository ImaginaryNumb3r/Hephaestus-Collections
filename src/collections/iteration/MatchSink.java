package collections.iteration;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 19.01.2018
 */
abstract class MatchSink<T, X extends Exception> extends TerminationSink<T, X>  {
    protected final IterationPredicate<T, X> _predicate;
    protected long _index;

    public MatchSink(IteratorEx<T, X> aggregator, IterationPredicate<T, X> predicate) {
        super(aggregator);
        _predicate = predicate;
    }

    public MatchSink(IteratorEx<T, X> aggregator, Predicate<T> predicate) {
        this(aggregator, (value, index) -> predicate.test(value));
    }

    /**
     * The common abstract method to test for the matchings.
     * Implemented for "allMatch" and "anyMatch".
     */
    public abstract boolean doesMatch() throws Exception;

    //<editor-fold desc="Match All">
    public static final class MatchAllSink<T, X extends Exception> extends MatchSink<T, X> {

        //<editor-fold desc="Constructors">
        public MatchAllSink(IteratorEx<T, X> aggregator, IterationPredicate<T, X> predicate) {
            super(aggregator, predicate);
        }

        public MatchAllSink(IteratorEx<T, X> aggregator, Predicate<T> predicate) {
            super(aggregator, predicate);
        }
        //</editor-fold>

        public boolean doesMatch() throws X {
            boolean allMatch = true;

            while (_source.hasNext() && allMatch){
                T current = _source.next();
                allMatch = _predicate.test(current, _index);
                ++_index;
            }

            return allMatch;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Match Any">
    public static final class MatchAnySink<T, X extends Exception> extends MatchSink<T, X> {

        //<editor-fold desc="Constructors">
        public MatchAnySink(IteratorEx<T, X> aggregator, IterationPredicate<T, X> predicate) {
            super(aggregator, predicate);
        }

        public MatchAnySink(IteratorEx<T, X> aggregator, Predicate<T> predicate) {
            super(aggregator, predicate);
        }
        //</editor-fold>

        public boolean doesMatch() throws X {
            boolean anyMatch = false;

            while (_source.hasNext() && !anyMatch){
                T current = _source.next();
                anyMatch = _predicate.test(current, _index);
                ++_index;
            }

            return anyMatch;
        }
    }
    //</editor-fold>
}
