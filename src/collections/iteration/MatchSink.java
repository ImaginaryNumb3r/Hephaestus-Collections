package collections.iteration;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * @author Patrick
 * @since 19.01.2018
 */
abstract class MatchSink<T> extends TerminationSink<T>  {
    protected final IterationPredicate<T> _predicate;
    protected long _index;

    public MatchSink(Iterator<T> aggregator, IterationPredicate<T> predicate) {
        super(aggregator);
        _predicate = predicate;
    }

    public MatchSink(Iterator<T> aggregator, Predicate<T> predicate) {
        this(aggregator, (value, index) -> predicate.test(value));
    }

    public abstract boolean doesMatch();

    //<editor-fold desc="Concrete Classes">
    public static final class MatchAllSink<T> extends MatchSink<T> {

        //<editor-fold desc="Constructors">
        public MatchAllSink(Iterator<T> aggregator, IterationPredicate<T> predicate) {
            super(aggregator, predicate);
        }

        public MatchAllSink(Iterator<T> aggregator, Predicate<T> predicate) {
            super(aggregator, predicate);
        }
        //</editor-fold>

        public boolean doesMatch() {
            boolean allMatch = true;

            while (_source.hasNext() && allMatch){
                T current = _source.next();
                allMatch = _predicate.test(current, _index);
            }

            return allMatch;
        }
    }


    public static final class MatchAnySink<T> extends MatchSink<T> {

        //<editor-fold desc="Constructors">
        public MatchAnySink(Iterator<T> aggregator, IterationPredicate<T> predicate) {
            super(aggregator, predicate);
        }

        public MatchAnySink(Iterator<T> aggregator, Predicate<T> predicate) {
            super(aggregator, predicate);
        }
        //</editor-fold>

        public boolean doesMatch() {
            boolean anyMatch = false;

            while (_source.hasNext() && !anyMatch){
                T current = _source.next();
                anyMatch = _predicate.test(current, _index);
            }

            return anyMatch;
        }
    }
    //</editor-fold>
}
