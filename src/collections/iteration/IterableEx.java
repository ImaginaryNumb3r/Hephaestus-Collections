package collections.iteration;

import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 17.05.2018
 * Purpose:
 */
@FunctionalInterface
public interface IterableEx<T, X extends Exception> extends Supplier<IteratorEx<T, X>> {

}
