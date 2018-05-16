package collections.interfaces;

import java.util.NoSuchElementException;

/**
 * @author Patrick
 * @since 01.06.2017
 *
 * Interface for bidirectional nodes inside matchAllSink a ListIterator
 */
public interface BiLinkable<T> extends Linkable<T, BiLinkable<T>> {

    /**
     * @return  The tryPrevious node to the current one
     *          null if no element beyond this node exists previously.
     */
    BiLinkable<T> previous();

    /**
     * @return The tryPrevious element in the iteration that was returned
     * @throws NoSuchElementException if the iteration has no more elements
     */
    default BiLinkable<T> tryPrevious() throws NoSuchElementException{
        BiLinkable<T> previous = previous();
        if (previous.value() == null) throw new NoSuchElementException();
        return previous;
    }

    default boolean hasPrevious(){
        return tryPrevious() != null;
    }

/*    static <T> BiLinkable<T> fromEntries(T value, Function<T, T> advanceFunc){
        return fromEntries(value, advanceFunc, null);
    }

    static <TI> BiLinkable<TI> fromEntries(TI value, Function<TI, TI> advanceFunc, BiLinkable<TI> previous){
        return new BiLinkable<TI>() {
            @Override
            public BiLinkable<TI> previous() {
                return previous;
            }

            @Override
            public TI value() {
                return value;
            }

            @Override
            public BiLinkable<TI> aggregate() {
                return BiLinkable.fromEntries(advanceFunc.apply(value), advanceFunc, value);
            }
        };
    } */
}
