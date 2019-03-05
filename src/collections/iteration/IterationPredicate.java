package collections.iteration;

/**
 * @author Patrick
 * @since 14.01.2018
 */
public interface IterationPredicate<T, X extends Exception> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param element the element in the iteration
     * @param index the index of the element
     * @return {@code true} if the aggregate argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T element, long index) throws X;

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default IterationPredicate<T, X> negate() throws X {
        return (element, index) -> !test(element, index);
    }

}
