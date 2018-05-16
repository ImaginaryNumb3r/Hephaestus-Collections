package collections.iterator;

import collections.interfaces.BiLinkable;

/**
 * @author Patrick
 * @since 01.06.2017
 *
 * Corner case iterator for when you need to return an iterator but internally have nothing to iterate
 */
public class EmptyIterator<T> extends NodeListIterator<T, EmptyIterator.EmptyLinkable<T>> {

    public EmptyIterator() {
        super(new EmptyLinkable<>());
    }

    @Override
    public void remove() {
        throw new IllegalStateException();
    }

    @Override
    public void add(T o) {
        throw new IllegalStateException();
    }

    @Override
    public void set(T o) {
        throw new IllegalStateException();
    }

    public static <T> EmptyIterator<T> get(){
        return new EmptyIterator<>();
    }

    public static class EmptyLinkable<T> implements BiLinkable<T> {

        @Override
        public T value() {
            return null;
        }

        @Override
        public EmptyLinkable<T> next() {
            // As an empty Linkable does not have a state, all nodes are equal.
            return this;
        }

        @Override
        public EmptyLinkable<T> previous() {
            // As an empty Linkable does not have a state, all nodes are equal.
            return this;
        }
    }
}
