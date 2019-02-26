package collections.matrix;


import collections.Matrices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author Patrick
 * @since 09.01.2018
 */
// TODO: Move to Collections Framework
public class MatrixSplitter<T> implements Collector<T[][], ArrayList<T>, ArrayList<T>> {
    @Override
    public Supplier<ArrayList<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<T>, T[][]> accumulator() {
        return (arrayList, ts) ->
                Matrices.foreachLine(ts,
                        array -> arrayList.addAll(Arrays.asList(array)));
    }

    @Override
    public BinaryOperator<ArrayList<T>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<ArrayList<T>, ArrayList<T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
