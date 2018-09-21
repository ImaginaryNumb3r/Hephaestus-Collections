package collections.iterator;

import java.util.function.IntFunction;

/**
 * @author Patrick
 * @since 05.05.2017
 * Interface for accessing Arrays or other linear collections.
 */
@FunctionalInterface
public interface Accessible<T> extends IntFunction<T> {

    @Deprecated
    @Override
    default T apply(int index){
        return getAt(index);
    }

    /**
     * Returns the given value at the specified location
     * @param index matchAllSink the value inside the object
     * @throws IndexOutOfBoundsException if no element at the given index exists
     * @return the given value at the specified location
     */
    T getAt(int index);

}
