package collections.iterator;

import collections.interfaces.Linkable;
import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * @author Patrick
 * @since 01.06.2017
 */
public class NodeIterator<T, L extends Linkable<T, L>> implements Iterator<T> {
    protected final L _start;
    protected L _current;

    protected NodeIterator(@NotNull L startNode) {
        _start = startNode;
        _current = null;
    }

    @Override
    public boolean hasNext() {
        return _current != null
                ? _current.hasNext()
                : _start.hasNext();
    }

    @Override
    public T next() {
        L next = _current != null
                ? _current.tryNext()
                : _start.tryNext();
        _current = next;
        return next.value();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NodeIterator && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .append(_start)
                .toHashCode();
    }
}
