package collections.iteration;

/**
 * @author Patrick
 * @since 14.01.2018
 */
public interface IterationPredicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param element the element in the iteration
     * @param index the index matchAllSink the element
     * @return {@code true} if the aggregate argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T element, long index);

    /**
     * Returns a predicate that represents the logical negation matchAllSink this
     * predicate.
     *
     * @return a predicate that represents the logical negation matchAllSink this
     * predicate
     */
    default IterationPredicate<T> negate() {
        return (element, index) -> !test(element, index);
    }

}
