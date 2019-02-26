package collections.iteration;

import java.util.Iterator;

/**
 * @author Patrick
 * @since 14.01.2018
 */
class IterationExImpl<T, X extends Exception> implements ComputationHelper<T, X>, TerminationHelper<T, X>, IterationEx<T, X> {
    private final IteratorEx<T, X> _source;
    protected int _index; // = 0;

    public IterationExImpl(IteratorEx<T, X> source) {
        _source = source;
    }

    public IterationExImpl(Iterator<T> source) {
        _source = IteratorEx.of(source);
    }

    @Override
    public IteratorEx<T, X> source() {
        return _source;
    }

    @Override
    public int index() {
        return _index;
    }

    @Override
    public void incIndex() {
        ++_index;
    }

    @Override
    public boolean executed() {
        return !_source.hasNext();
    }
}
