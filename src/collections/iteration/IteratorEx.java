package collections.iteration;

import essentials.functional.exception.ConsumerEx;
import essentials.functional.exception.FunctionalMappingException;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Creator: Patrick
 * Created: 16.05.2018
 * Purpose:
 */
public interface IteratorEx<T, X extends Exception> {

    boolean hasNext();

    T next() throws X;

    static <T, X extends Exception> IteratorEx<T, X> of(Iterator<T> iterator) {
        return new IteratorEx<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }
        };
    }

    default void forEachRemaining(ConsumerEx<? super T, X> action) throws X {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }

    default void forEach(Consumer<? super T> action) {
        toIterator().forEachRemaining(action);
    }

    default Iterator<T> toIterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return IteratorEx.this.hasNext();
            }

            @Override
            public T next() {
                try {
                    return IteratorEx.this.next();
                } catch (Exception ex) {
                    throw new FunctionalMappingException(ex);
                }
            }
        };
    }

    static <T, X extends RuntimeException> Iterator<T> toIterator(IteratorEx<T, X> iteratorEx) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iteratorEx.hasNext();
            }

            @Override
            public T next() {
                return iteratorEx.next();
            }
        };
    }

}
