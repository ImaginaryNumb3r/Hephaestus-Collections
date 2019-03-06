package collections.iteration;

import collections.iterator.Iterators;
import essentials.functional.exception.ConsumerEx;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.ListIterator;

import static essentials.contract.Contract.checkNull;

/**
 * Creator: Patrick
 * Created: 16.05.2018
 * Purpose:
 */
public interface IterationEx<T, X extends Exception> extends Computation<T, X>, Termination<T, X>  {

    // TODO: Make as delegation of Iterator.
    static <T, X extends Exception> IterationEx<T, X> of(IteratorEx<T, X> iterator){
        return new IterationExImpl<>(iterator);
    }

    // TODO: Make as delegation of Iterator.
    static <T, X extends Exception> IterationEx<T, X> of(Iterator<T> iterator){
        return new IterationExImpl<>(iterator);
    }

    static <T, X extends Exception> IterationEx<T, X> of(T[] objects){
        ListIterator<T> iterator = Iterators.of(objects);
        return new IterationExImpl<>(IteratorEx.of(iterator));
    }
}
