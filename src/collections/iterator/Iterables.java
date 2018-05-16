package collections.iterator;

import essentials.collections.IterableList;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Patrick
 * @since 01.06.2017
 */
public final class Iterables {

    public static <T> Iterable<T> of(Iterator<T> iterator){
        return () -> iterator;
    }

    @SafeVarargs
    public static <T> Iterable<T> of(T... objects){
        return () -> Iterators.of(objects);
    }

    public static <T> IterableList<T> ofReverse(ListIterator<T> objects){
        return Iterables.ofReverse(objects);
    }

    public static <T> boolean equals(Iterable<T> iterable1, Iterable<T> iterable2){
        return Iterators.equals(iterable1.iterator(), iterable2.iterator());
    }
}
