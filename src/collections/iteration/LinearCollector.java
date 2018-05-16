package collections.iteration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * @author Patrick
 * @since 14.01.2018
 *
 * A collector for easily scalable, purely linear collections.
 */
public interface LinearCollector<T, R> extends Collector<T, R, R> {

    @Override
    Supplier<R> supplier();

    @Override
    BiConsumer<R, T> accumulator();

    @Override
    BinaryOperator<R> combiner();

    @Override
    default Function<R, R> finisher(){
        return Function.identity();
    }

    @Override
    default Set<Characteristics> characteristics() {
        return Set.of(IDENTITY_FINISH);
    }

    static <T> LinearCollector<T, ArrayList<T>> arrayList(){
        return new LinearCollector<>() {
            @Override
            public Supplier<ArrayList<T>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<ArrayList<T>, T> accumulator() {
                return ArrayList::add;
            }

            @Override
            public BinaryOperator<ArrayList<T>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }
        };
    }

    static <T> LinearCollector<T, T[]> array(IntFunction<T[]> arrayConstructor){
        return new LinearCollector<>() {
            @Override
            public Supplier<T[]> supplier() {
                return () -> arrayConstructor.apply(0);
            }

            @Override
            public BiConsumer<T[], T> accumulator() {
                return (array, item) -> {

                };
            }

            @Override
            public BinaryOperator<T[]> combiner() {
                return (left, right) -> {
                    T[] newArray = arrayConstructor.apply(left.length + right.length);

                    int shift = 0;
                    for (int i = 0; i != left.length; ++i, ++shift){
                        newArray[i] = left[i];
                    }
                    for (int i = 0; i != left.length; ++i, ++shift){
                        newArray[i + shift] = left[i];
                    }

                    return newArray;
                };
            }
        };
    }

    static <T, R extends Collection<T>> LinearCollector<T, R> of(Supplier<R> supplier){
        return new LinearCollector<>() {
            @Override
            public Supplier<R> supplier() {
                return supplier;
            }

            @Override
            public BiConsumer<R, T> accumulator() {
                return Collection::add;
            }

            @Override
            public BinaryOperator<R> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }
        };
    }
}
