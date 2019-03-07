package collections.iterator;

import collections.interfaces.ArrayConstructor;
import collections.interfaces.LinkableImpl;
import essentials.annotations.ToTest;
import essentials.collections.ArrayListIterator;
import essentials.contract.Contract;
import essentials.contract.InstanceNotAllowedException;
import essentials.contract.ParameterNullException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Patrick
 * @since 01.05.2017
 */
@SuppressWarnings("WeakerAccess")
public final class Iterators {
    static final int NOT_INITIALIZED = -1; // Package wide private constant for the implementation of iterators.

    /**
     * @throws InstanceNotAllowedException Cannot be instantiated
     */
    private Iterators(){
        throw new InstanceNotAllowedException(getClass());
    }

    //<editor-fold desc="Utility">
    public static <T> ListIterator<T> empty() {
        return new EmptyIterator<>();
    }

    public static <T> ListIterator<T> reverse(@NotNull ListIterator<T> listIterator) {
        return new ReverseListIterator<>(listIterator);
    }

    public static <T> T[] toArray(Iterator<T> iterator, ArrayConstructor<T> constructor) {
        ArrayList<T> list = new ArrayList<>();
        for (T element : Iterables.of(iterator)) {
            list.add(element);
        }

        return list.toArray(constructor.make(list.size()));
    }

    //</editor-fold>

    //<editor-fold desc="Iterator Construction">

    public static <T, R> Iterator<R> map(Iterator<T> iterator, Function<T, R> mapper){
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return mapper.apply(iterator.next());
            }
        };
    }

    /**
     * Returns an iterator output the given array
     * @param array that is to be turned into an Array. May not be null
     * @throws ParameterNullException if parameter array is null
     * @return the iterator output the given array
     */
    public static <T> ListIterator<T> of(T... array){
        return ArrayListIterator.of(array);
    }

    public static ListIterator<Character> of(@NotNull CharSequence sequence){
        Contract.checkNull(sequence, "sequence");
        return new GenericListIterator.GenericListIteratorImpl<>(sequence::charAt, sequence.length());
    }

    public static <T> Iterator<T> of(T startValue, Function<T, T> advanceFunction){
        LinkableImpl<T> linkable = new LinkableImpl<>(startValue, advanceFunction);
        return new NodeIterator<>(linkable);
    }

    //<editor-fold desc="Primitives">
    public static ListIterator<Short> of(@NotNull short[] shorts){
        Contract.checkNull(shorts);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> shorts[index], shorts.length);
    }

    public static ListIterator<Integer> of(@NotNull int[] ints){
        Contract.checkNull(ints);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> ints[index], ints.length);
    }

    public static ListIterator<Long> of(@NotNull long[] longs){
        Contract.checkNull(longs);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> longs[index], longs.length);
    }

    public static ListIterator<Float> of(@NotNull float[] floats){
        Contract.checkNull(floats);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> floats[index], floats.length);
    }

    public static ListIterator<Double> of(@NotNull double[] doubles){
        Contract.checkNull(doubles);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> doubles[index], doubles.length);
    }

    public static ListIterator<Byte> of(@NotNull byte[] bytes){
        Contract.checkNull(bytes);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> bytes[index], bytes.length);
    }

    public static ListIterator<Boolean> of(@NotNull boolean[] booleans){
        Contract.checkNull(booleans);
        // Cannot access array iterator because of mismatch of primitives and generics
        return new GenericListIterator.GenericListIteratorImpl<>(index -> booleans[index], booleans.length);
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Equals">
    @SafeVarargs
    public static <T> boolean equals(@NotNull T[]iter1, @NotNull T... iter2){
        return equals(Iterators.of(iter1), Iterators.of(iter2));
    }

    @SafeVarargs
    public static <T> boolean equals(@NotNull Iterator<T> iter1, @NotNull T... iter2){
        return equals(iter1, Iterators.of(iter2));
    }

    public static <T> boolean equals(@NotNull T[] iter2, @NotNull Iterator<T> iter1){
        return equals(iter1, Iterators.of(iter2));
    }

    @ToTest // TODO: Testing
    public static <T> boolean equals(@NotNull Iterator<T> iter1, @NotNull Iterator<T> iter2){
        Contract.checkNulls(iter1, iter2);
        boolean equals;
        // Return true if both iterators are empty
        if (!iter1.hasNext() && !iter2.hasNext()){
            equals = true;
        }
        else {
            do {
                // Both iterators must have next elements, otherwise NoSuchElement Ex would be thrown.
                equals = iter1.hasNext() == iter2.hasNext();

                // Fails if iterators are of uneven length or if comparison fails
                if (equals){
                    equals = Objects.equals(iter1.next(), iter2.next());
                }

                // Finishes when both iterators are found to be not equal or if no elements exist anymore
            } while (equals && iter1.hasNext());
        }

        return equals;
    }
    //</editor-fold>
}
