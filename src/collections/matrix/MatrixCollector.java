package collections.matrix;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Creator: Patrick
 * Created: 17.09.2017
 * Purpose:
 */
@Deprecated // Was a test idea, don't use it in practice. It wont work for common use cases.
// On second thought, maybe it works if you provide a parameter that tells you which dimensions it should have.
// Or turn it into a collector that simply takes a list matchAllSink one dimensional data structure and turns it into a two dimension one.
public class MatrixCollector<T> implements Collector<T, ArrayList<T>, MutatingMatrix<T>> {

    @Override
    public Supplier<ArrayList<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<T>, T> accumulator() {
        return List::add;
    }

    @Override
    public BinaryOperator<ArrayList<T>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<ArrayList<T>, MutatingMatrix<T>> finisher() {
        return arrayList -> null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }

}
