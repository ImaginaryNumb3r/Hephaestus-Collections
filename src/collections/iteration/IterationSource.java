package collections.iteration;

import java.util.Iterator;

/**
 * @author Patrick
 * @since 14.01.2018
 * Like an Iterator, but due to clashing generics we cannot simply take Iterator.
 */
interface IterationSource<T> extends Iterator<T> {

}
