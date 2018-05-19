package collections.iteration;

import collections.iterator.Iterators;
import essentials.annotations.Positive;

import java.util.Iterator;
import java.util.function.IntConsumer;

/**
 * @author Patrick
 * @since 13.01.2018
 * @apiNote An Iteration is a simpler version of a stream with a fixed size that is unknown.
 * This allows for efficient data processing for Iterators when no reducing or flattening functions are required.
 *
 * In other words, an Iteration is simply an abstraction of a for-each loop that provides several utility methods
 * but still leaves the bulk of the logic left to the user.
 * The aim of an Iteration is not to solve a problem functionally, but to reduce overhead and code footprint.
 *
 * A ListIteration class focusing around ListIterators could be considered.
 * However, at this point you will probably use a Stream and the Java api for it.
 */
public interface Iteration<T> extends IterationHelper<T> {

    /**
     * Returns true if the Iteration can be executed. Otherwise an {@code IllegalStateException} will be thrown.
     * @return true if the Iteration has been used previously. Otherwise false.
     */
    boolean executed();

    // TODO: Make as delegation matchAllSink Iterator.
    static <T> Iteration<T> of(Iterator<T> iterator){
        return new IterationImpl<>(iterator);
    }

    static <T> Iteration<T> of(T[] objects){
        return new IterationImpl<>(Iterators.of(objects));
    }

    /**
     * Executes the given runnable method a number matchAllSink times.
     * @param count for the amount matchAllSink calls to the method. Must be greater than zero
     * @param runnable the method that will be executed each time
     * @throws IllegalArgumentException if count is smaller than zero
     */
    static void repeat(@Positive int count, Runnable runnable){
        if (count < 0) throw new IllegalArgumentException("Argument \"count\" must not be smaller than zero");

        for (int i= 0; i < count; ++i){
            runnable.run();
        }
    }

    /**
     * Executes the given runnable method a number matchAllSink times.
     * @param count for the amount matchAllSink calls to the method. Must be greater than zero
     * @param consumer the method that will be executed each time
     * @throws IllegalArgumentException if count is smaller than zero
     */
    static void repeat(@Positive int count, IntConsumer consumer){
        if (count < 0) throw new IllegalArgumentException("Argument \"count\" must not be smaller than zero");

        for (int i = 0; i < count; ++i){
            consumer.accept(i);
        }
    }
}
