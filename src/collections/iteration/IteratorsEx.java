package collections.iteration;

import essentials.contract.InstanceNotAllowedException;
import essentials.functional.exception.FunctionEx;

import java.util.NoSuchElementException;

/**
 * @author Patrick
 * @since 01.05.2017
 */
@SuppressWarnings("WeakerAccess")
public final class IteratorsEx {

    /**
     * @throws InstanceNotAllowedException Cannot be instantiated
     */
    private IteratorsEx(){
        throw new InstanceNotAllowedException(getClass());
    }

    //<editor-fold desc="Utility">
    public static <T, X extends Exception> IteratorEx<T, X> empty() {
        return new IteratorEx<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                throw new NoSuchElementException();
            }
        };
    }

    //</editor-fold>

    public static <T, R, X extends Exception> IteratorEx<R, X> map(IteratorEx<T, X> iterator, FunctionEx<T, R, X> mapper){
        return new IteratorEx<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() throws X {
                return mapper.apply(iterator.next());
            }
        };
    }
}
