package collections.matrix;

import essentials.contract.Contract;
import essentials.tuple.Tuple;
import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * @author Patrick
 * @since 21.05.2017
 */
// TODO: Move to Collections Framework
public class Generic2DIterator<T> implements Iterator<T> {
    private final Accessible2D<T> _accessible;
    private final int _width;
    private final int _heigth;

    @SuppressWarnings("WeakerAccess")
    protected Generic2DIterator(@NotNull Accessible2D<T> accessible, int width, int height){
        _accessible = accessible;
        _width = width;
        _heigth = height;
    }

    public <I> Generic2DIterator<I> from (@NotNull MutatingMatrix<I> matrix){
        Contract.checkNull(matrix, "matrix");
        return from(matrix, matrix.getWidth(), matrix.getHeight());
    }

    public <I> Generic2DIterator<I> from (@NotNull Accessible2D<I> accessible, @NotNull Tuple<Integer, Integer> bounds){
        Contract.checkNulls(bounds, bounds.getA(), bounds.getB());
        return from(accessible, bounds.getA(), bounds.getB());
    }

    public <I> Generic2DIterator<I> from (@NotNull Accessible2D<I> accessible, int width, int heigth){
        Contract.checkNull(accessible, "accessible");
        if (width  < 0) throw new IllegalArgumentException("Width may not be smaller 0");
        if (heigth < 0) throw new IllegalArgumentException("Height may not be smaller 0");
        return new Generic2DIterator<>(accessible, width, heigth);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Generic2DIterator
                && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .append(_accessible)
                .appendAll(_width, _heigth)
                .toHashCode();
    }
}
