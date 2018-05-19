package collections.iteration;

/**
 * @author Patrick
 * @since 14.01.2018
 */
abstract class TerminationSink<T, X extends Exception> {
    protected final IteratorEx<T, X> _source;

    public TerminationSink(IteratorEx<T, X> source) {
        _source = source;
    }
}
