package collections.matrix;

import essentials.util.HashGenerator;

import java.util.Iterator;

/**
 * @author Patrick
 * @since 12.11.2016
 *
 * InnerIterator for 2D objects
 * InnerIterator which iterates through a matrix, line by line and returns the according pixels
 */
// TODO: Different iteration strategies
public class Iterator2D<T> implements Iterator<T> {
    protected ReadCollection2D<T> _matrix;
    protected int _curX;
    protected int _curY;

    //<editor-fold desc="Constructors">
    public Iterator2D(T[][] matrix) {
        _matrix = new MutatingMatrix<T>(matrix);
    }

    public Iterator2D(ReadCollection2D<T> matrix) {
        _matrix = matrix;
    }
    //</editor-fold>

    /**
     * Returns true if the iteration has more pixels
     * @return true if the iteration has more pixels
     */
    @Override
    public boolean hasNext() {
        return _curY < _matrix.getWidth() && _curX < _matrix.getHeight();
    }

    /**
     * Traverses the matrix line by line and returns the according pixels
     *
     * Returns the aggregate pixel of the matrix
     * @return the aggregate pixel of the matrix
     */
    @Override
    public T next(){
        int x = _curX;
        int y = _curY;

        ++_curY;
        // Reset line and jump to aggregate one
        if (_curY >= _matrix.getWidth()){
            _curY = 0;
            ++_curX;
        }

        return _matrix.getAt(y, x);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Iterator2D
                && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .appendObjs(_curX, _curY, _matrix)
                .toHashCode();
    }
}
