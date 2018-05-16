package collections.matrix;

import collections.matrix.value.Bounds;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Creator: Patrick
 * Created: 09.12.2017
 * Purpose:
 */
// TODO: Move to Collections Framework
public interface ReadCollection2D<T> extends Iterable<T>, Accessible2D<T> {

    boolean contains(T element);

    boolean containsAll(Collection<T> elements);

    int getWidth();

    int getHeight();

    Bounds bounds();

    Iterator<T> iterator();

    T[][] toArray();

    default int size(){
        return getWidth() * getHeight();
    }

    default boolean isEmpty(){
        return size() == 0;
    }

    default Stream<T> stream(){
        Spliterator<T> spliterator = Spliterators.spliterator(iterator(), 0, size());
        return StreamSupport.stream(spliterator, false);
    }
}
