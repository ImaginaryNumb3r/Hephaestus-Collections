package collections.iteration;

import java.util.Iterator;

/**
 * @author Patrick
 * @since 14.01.2018
 */
abstract class TerminationSink<T> /* Consumer<T>, */ {
    protected final Iterator<T> _source;

    public TerminationSink(Iterator<T> source) {
        _source = source;
    }
}
