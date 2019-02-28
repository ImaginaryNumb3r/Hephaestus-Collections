package collections.streams;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * @author Patrick
 * @since 19.11.2016
 */
public class Streams<T> extends AbstractStreams<T> {

    //<editor-fold desc="Constructors">
    protected Streams(T[] array) {
        super(array);
    }

    protected Streams(List<T> list) {
        super(list);
    }

    protected Streams(Spliterator<T> spliterator) {
        super(spliterator);
    }

    protected Streams(Iterator<T> iterator, int size) {
        super(iterator, size);
    }
    //</editor-fold>

    public static <T> Stream<T> stream(T[] array){
        Contract.checkNull(array, "array");
        return new Streams<>(array).stream();
    }

    public static <T> Stream<T> stream(Iterator<T> iterator, int size){
        Contract.checkNull(iterator, "iterator");
        Contract.checkNegative(size);
        return new Streams<>(iterator, size).stream();
    }
    public static <T> Stream<T> stream(List<T> list){
        Contract.checkNull(list, "list");
        return new Streams<>(list).stream();
    }

    public static <T> Stream<T> stream(@NotNull Spliterator<T> spliterator){
        Contract.checkNull(spliterator, "spliterator");
        return new Streams<>(spliterator).stream();
    }

    @Override
    public boolean isParallel() {
        return false;
    }
}
