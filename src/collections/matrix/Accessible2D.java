package collections.matrix;

import essentials.contract.Contract;
import essentials.tuple.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * @author Patrick
 * @since 21.05.2017
 * Interface for accessing 2D Arrays or other 2D datastructures like a Matrix.
 */
// TODO: Move to Collections Framework
@FunctionalInterface
public interface Accessible2D<T> extends BiFunction<Integer, Integer, T>{

    /**
     * Deprecated since this method allows nullable parameters, use getAt instead.
     * This method is only to be called as a instance of BiFunction.
     * @param x horizontal index of the value inside the object
     * @param y vertical index of the value inside the object
     * @throws IndexOutOfBoundsException if no element at the given index exists
     * @throws NullPointerException if either parameter is null
     * @return the given value at the specified location
     */
    @Deprecated
    @Override
    default T apply(@NotNull Integer x, @NotNull Integer y){
        return getAt(x,y);
    }

    /**
     * Returns the given value at the specified location
     * @param tuple of coordinates serving as index of the value inside the object
     * @throws IndexOutOfBoundsException if no element at the given index exists
     * @throws NullPointerException if either parameter is null
     * @return the given value at the specified location
     */
    default T getAt(@NotNull Tuple<Integer, Integer> tuple){
        Contract.checkNull(tuple, "tuple");
        return getAt(tuple.getA(), tuple.getB());
    }

    /**
     * Returns the given value at the specified location
     * @param x horizontal index of the value inside the object
     * @param y vertical index of the value inside the object
     * @throws IndexOutOfBoundsException if no element at the given index exists
     * @throws NullPointerException if either parameter is null
     * @return the given value at the specified location
     */
    T getAt(int x, int y);
}
