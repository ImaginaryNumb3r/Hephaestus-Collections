package collections.iteration;

/**
 * @author Patrick
 * @since 14.01.2018
 * The external API for Iteration pipes.
 */
public interface Provider<T> {

    boolean hasNext();

    // T output();
}
