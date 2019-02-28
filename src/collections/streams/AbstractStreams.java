package collections.streams;

import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Patrick
 * @since 19.11.2016
 */
public abstract class AbstractStreams<T> implements BaseStreams<T>{
    protected final Spliterator<T> _spliterator;

    //<editor-fold desc="Constructors">
    public AbstractStreams(@NotNull T[] array){
        this(Spliterators.spliterator(array, Spliterator.ORDERED));
    }

    public AbstractStreams(@NotNull List<T> list){
        this(list.spliterator());
    }

    public AbstractStreams(@NotNull Spliterator<T> spliterator) {
        _spliterator = spliterator;
    }

    public AbstractStreams(Iterator<T> iterator, int size) {
        _spliterator = Spliterators.spliterator(iterator, 0, size);
    }
    //</editor-fold>

    public Stream<T> stream(){
        return StreamSupport.stream(_spliterator, isParallel());
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .append(_spliterator)
                .toHashCode();
    }
}
