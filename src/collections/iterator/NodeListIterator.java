package collections.iterator;

import collections.interfaces.BiLinkable;
import essentials.contract.Contract;
import essentials.datastructure.ListIteratorHelper;
import org.jetbrains.annotations.NotNull;

import static collections.iterator.Iterators.NOT_INITIALIZED;

/**
 * @author Patrick
 * @since 01.06.2017
 */
public abstract class NodeListIterator<T, L extends BiLinkable<T>>
        extends NodeIterator<T, BiLinkable<T>> implements ListIteratorHelper<T> {
    protected int _index;

    protected NodeListIterator(@NotNull L startNode) {
        super(startNode);
        _index = NOT_INITIALIZED;
    }

    @Override
    public T next(){
        ++_index;
        return super.next();
    }

    @Override
    public T previous() {
        --_index;
        BiLinkable<T> previous = _current != null
                ? _current.tryPrevious()
                : _start.tryPrevious();
        _current = previous;
        return previous.value();
    }

    @Override
    public int index() {
        return _index;
    }

    public static <T, L extends BiLinkable<T>> NodeListIterator<T, L> from(L startNode) {
        Contract.checkNull(startNode, "startNode");
        return new NodeListIteratorImpl<>(startNode);
    }

    //<editor-fold desc="Inner Impl Class">
    protected static class NodeListIteratorImpl<TI, LI extends BiLinkable<TI>>
            extends NodeListIterator<TI, LI> {

        protected NodeListIteratorImpl(@NotNull LI startNode) {
            super(startNode);
        }

        /**
         * No implementation
         * @throws UnsupportedOperationException on call
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * No implementation
         * @throws UnsupportedOperationException on call
         */
        @Override
        public void set(TI t) {
            throw new UnsupportedOperationException();
        }

        /**
         * No implementation
         * @throws UnsupportedOperationException on call
         */
        @Override
        public void add(TI t) {
            throw new UnsupportedOperationException();
        }
    }
    //</editor-fold>
}
