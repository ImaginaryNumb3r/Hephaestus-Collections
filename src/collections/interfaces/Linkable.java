package collections.interfaces;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Patrick
 * @since 01.06.2017
 * @param <T> Value of the node
 * @param <L> Type of node that follows
 * Lightweight interface for the nodes of an iterator inside a LinkedList or similar structures.
 */
public interface Linkable<T, L extends Linkable<T, L>> {

    /**
     * Returns The value of this node
     * @return the value of this node
     */
    T value();

    /**
     * @return  The aggregate node to the current one. <br>
     *          null if no element beyond this node exists.
     */
    L next();

    /**
     * @return The aggregate element in the iteration. Throws an exception if no more elements exist
     * @throws NoSuchElementException if the iteration has no more elements
     */
    default L tryNext(){
        L next = next();
        if (next.value() == null) throw new NoSuchElementException();
        return next;
    }

    default boolean hasNext(){
        return next().value() != null;
    }

    /**
     * Performs the action of the given consumer of all uniterated elements
     * @param consumer the action that is to be performed by each given element
     */
    default void forEachRemaining(Consumer<T> consumer){
        while (hasNext()){
            consumer.accept(next().value());
        }
    }

    static <TI> LinkableImpl<TI> of(TI value, Function<TI, TI> advanceFunc){
        return new LinkableImpl<>(value, advanceFunc);
    }
}
