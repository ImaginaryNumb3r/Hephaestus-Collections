package collections.matrix;

import collections.iterator.Iterables;
import collections.matrix.value.Coord2D;
import essentials.annotations.ToTest;
import essentials.tuple.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Creator: Patrick
 * Created: 09.12.2017
 * TODO: Matrix Functions
 * Invertieren, Eigenwerte, Diagonalisieren.
 * In dieser Reihenfolge sind die am leichtesten zum Implementieren (für naive Algorithmen)
 * Zurvor noch den Rang bestimmen
 */
// TODO: Move to Collections Framework
public class NumericMatrix<T extends Number> extends MutatingMatrix<T> {
    protected final Function<Number, T> _numberSupplier;

    //<editor-fold desc="Constructors">
    public NumericMatrix(@NotNull T[][] matrix, @NotNull Function<Number, T> numberSupplier) {
        super(matrix);
        _numberSupplier = numberSupplier;
    }

    public NumericMatrix(@NotNull Tuple<Integer, Integer> bounds, @NotNull Function<Number, T> numberSupplier) {
        super(bounds);
        _numberSupplier = numberSupplier;
    }

    public NumericMatrix(int width, int height, @NotNull Function<Number, T> numberSupplier) {
        super(width, height);
        _numberSupplier = numberSupplier;
    }

    public NumericMatrix(int width, int height, @NotNull Class<T> type) {
        super(width, height);

        if (Double.class.equals(type) || double.class.equals(type)){
            _numberSupplier = para1 -> (T) Double.valueOf(para1.doubleValue());
        }
        else if (Integer.class.equals(type) || int.class.equals(type)){
            _numberSupplier = para1 -> (T) Integer.valueOf(para1.intValue());
        }
        else if (Long.class.equals(type) || long.class.equals(type)){
            _numberSupplier = para1 -> (T) Long.valueOf(para1.longValue());
        }
        else if (Byte.class.equals(type) || byte.class.equals(type)){
            _numberSupplier = para1 -> (T) Byte.valueOf(para1.byteValue());
        } else {
            throw new IllegalArgumentException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Transposition">
    /**
     * Non-mutating function
     *  (A B C) => (A D)
     *  (D E F)    (B E)
     *             (C F)
     */
    @ToTest
    public NumericMatrix<T> computeTransposition(){
        // Make matrix with inverted width and height
        T[][] matrix = (T[][]) new Object[getHeight()][getWidth()];

        // Iterate the values
        for (Coord2D coord : Iterables.of(coordIterator())) {
            T value = getAt(coord);
            coord.invert();
            coord.setAt(matrix, value);
        }

        return new NumericMatrix<>(matrix, _numberSupplier);
    }
    //</editor-fold>

    public void invertThis(){

    }

    //<editor-fold desc="Determinate">
    /**
     * Calculates the determinate of the current matrix
     * ( A B C )
     * ( D E F )
     * ( G H I )
     * A*E*I + B*F*G + C*D*H - A*F*H - B*D*I - C*E*G^
     * @throws IllegalStateException if the matrix is not square
     */
    public double determinate(){
        if (!isQuadratic()) {
            throw new IllegalStateException("Cannot perform the determinant on a non quadratic matrix!");
        }

        // Number of multiplication operations
        final int max = getWidth();
        double total = determinateTotal(max, false);
        total -= determinateTotal(max, true);

        return total;
    }

    private double determinateTotal(int max, boolean backwards){
        double total = 0;
        for (int i = 0; i!= max; ++i){
            int x = i;
            int y = 0;

            double sum = 1; // Initialize with 1, as this is the neutral element to the multiplication below
            int count = 0;
            while (count++ != max) {
                double value = getAt(x, y).doubleValue();
                sum *= value;
                x = Math.floorMod(x + 1, max);
                y = backwards
                        ? Math.floorMod(y - 1, max)
                        : Math.floorMod(y + 1, max);
            }
            total += sum;
        }

        return total;
    }
    //</editor-fold>


    public boolean isQuadratic(){
        return getWidth() == getHeight();
    }

    //<editor-fold desc="Multiplication">
    /**
     *  (A B) * (E F) => (A+E B+F)
     *  (C D)   (G H)    (C+G D+H)
     * @param other matrix
     * @return the resulting matrix
     */
    @ToTest
    public NumericMatrix<T> multiply(Matrix<T> other){
        NumericMatrix<T> result = new NumericMatrix<>(bounds(), _numberSupplier);
        computeMultiplication(other, result._matrix);
        return result;
    }

    /**
     *  (A B) * (E F) => (A+E B+F)
     *  (C D)   (G H)    (C+G D+H)
     * @param other matrix
     */
    @ToTest
    public void multiplyThis(Matrix<T> other){
        computeMultiplication(other, _matrix);
    }

    @ToTest
    protected void computeMultiplication(Matrix<T> other, T[][] matrix){
        checkBounds(other);

        BinaryOperator<T> multiplication = (val1, val2) -> {
            double double1 = val1.doubleValue();
            double double2 = val2.doubleValue();

            return _numberSupplier.apply(double1 + double2);
        };
        computeResult(other, multiplication, matrix);
    }
    //</editor-fold>

    /**
     * Make empty matrix with the bounds and supplier of the current matrix.
     * @return new empty numeric Matrix
     */
    protected NumericMatrix<T> makeEmpty(){
        return new NumericMatrix<>(bounds(), _numberSupplier);
    }
}
