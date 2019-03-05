package collections.streams;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Patrick
 * @since 19.11.2016
 *
 * Utility interface for creating own streams.
 * Delegates all methods of a stream to avoid code duplication.
 */
public interface BaseStreams<T> extends Stream<T>{

    Stream<T> stream();

    boolean isParallel();

    @Override
    default Stream<T> filter(Predicate<? super T> predicate){
        return stream().filter(predicate);
    }

    @Override
    default <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return stream().map(mapper);
    }

    @Override
    default IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream().mapToInt(mapper);
    }

    @Override
    default LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream().mapToLong(mapper);
    }

    @Override
    default DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream().mapToDouble(mapper);
    }

    @Override
    default <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return stream().flatMap(mapper);
    }

    @Override
    default IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream().flatMapToInt(mapper);
    }

    @Override
    default LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream().flatMapToLong(mapper);
    }

    @Override
    default DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream().flatMapToDouble(mapper);
    }

    @Override
    default Stream<T> distinct() {
        return stream().distinct();
    }

    @Override
    default Stream<T> sorted() {
        return stream().sorted();
    }

    @Override
    default Stream<T> sorted(Comparator<? super T> comparator) {
        return stream().sorted(comparator);
    }

    @Override
    default Stream<T> peek(Consumer<? super T> action) {
        return stream().peek(action);
    }

    @Override
    default Stream<T> limit(long maxSize) {
        return stream().limit(maxSize);
    }

    @Override
    default Stream<T> skip(long n) {
        return stream().skip(n);
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        stream().forEach(action);
    }

    @Override
    default void forEachOrdered(Consumer<? super T> action) {
        stream().forEachOrdered(action);
    }

    @Override
    default Object[] toArray() {
        return stream().toArray();
    }

    @Override
    default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }

    @Override
    default T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream().reduce(identity, accumulator);
    }

    @Override
    default Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream().reduce(accumulator);
    }

    @Override
    default <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream().reduce(identity, accumulator, combiner);
    }

    @Override
    default <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream().collect(supplier, accumulator, combiner);
    }

    @Override
    default <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream().collect(collector);
    }

    @Override
    default Optional<T> min(Comparator<? super T> comparator) {
        return stream().min(comparator);
    }

    @Override
    default Optional<T> max(Comparator<? super T> comparator) {
        return stream().max(comparator);
    }

    @Override
    default long count() {
        return stream().count();
    }

    @Override
    default boolean anyMatch(Predicate<? super T> predicate) {
        return stream().anyMatch(predicate);
    }

    @Override
    default boolean allMatch(Predicate<? super T> predicate) {
        return stream().allMatch(predicate);
    }

    @Override
    default boolean noneMatch(Predicate<? super T> predicate) {
        return stream().noneMatch(predicate);
    }

    @Override
    default Optional<T> findFirst() {
        return stream().findFirst();
    }

    @Override
    default Optional<T> findAny() {
        return stream().findAny();
    }

    @Override
    default Iterator<T> iterator() {
        return stream().iterator();
    }

    @Override
    default Spliterator<T> spliterator() {
        return stream().spliterator();
    }

    @Override
    default Stream<T> sequential() {
        return stream().sequential();
    }

    @Override
    default Stream<T> parallel() {
        return stream().parallel();
    }

    @Override
    default Stream<T> unordered() {
        return stream().unordered();
    }

    @Override
    default Stream<T> onClose(Runnable closeHandler) {
        return stream().onClose(closeHandler);
    }

    @Override
    default void close() {
        stream().close();
    }
}
