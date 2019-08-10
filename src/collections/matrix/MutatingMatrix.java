package collections.matrix;

import collections.Matrices;
import collections.iterator.Iterables;
import collections.iterator.Iterators;
import collections.matrix.value.Bounds;
import collections.matrix.value.Coord2D;
import essentials.annotations.ToTest;
import essentials.contract.Contract;
import essentials.contract.NoImplementationException;
import essentials.functional.TriFunction;
import essentials.tuple.Tuple;
import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Patrick
 * @since 09.12.2017
 * A mutating matrix class. For a non-mutating, return the Matrix interface.
 */
// TODO: Move to Collections Framework
public class MutatingMatrix<T> implements Matrix<T>, Collection2D<T> {
    protected final T[][] _matrix;
    protected final int _size; // Calculate size only once

    //<editor-fold desc="Constructors">
    /**
     * Creates a new instance of a Matrix
     * @param width of the matrix. May only be zero if height is zero as well
     * @param height of the matrix. May only be zero if width is zero as well
     * @throws NegativeArraySizeException if one of the indices is negative
     * @throws InvalidParameterException when width or height are initialized with zero, but not both
     */ @SuppressWarnings("unchecked")
    public MutatingMatrix(int width, int height) {
        areValidParameters(width, height, false);
        _matrix = (T[][])new Object[width][height];
        _size = width * height;
    }

    public MutatingMatrix(@NotNull T[][] matrix) {
        Contract.checkNull(matrix, "matrix");
        _matrix = matrix;
        _size = getHeight() * getWidth();
    }

    public MutatingMatrix(Tuple<Integer, Integer> bounds) {
        this(bounds.getA(), bounds.getB());
    }

    //</editor-fold>

    //<editor-fold desc="Construction Validation">
    /**
     * Checks parameters of the matrix amd returns true if they are valid
     * @param width of the matrix. May only be zero if height is zero as well
     * @param height of the matrix. May only be zero if width is zero as well
     * @throws NegativeArraySizeException if one of the indices is negative
     * @throws InvalidParameterException when width or height are initialized with zero, but not both
     * @return true if parameters are valid to initialize a Matrix
     */
    public boolean areValidParameters(int width, int height){
        return areValidParameters(width, height, true);
    }

    /**
     * Checks parameters of the matrix amd returns true if they are valid
     * @param bounds of the matrix, containing height and width. Values must not be zero when they are both
     * @throws NegativeArraySizeException if one of the indices is negative
     * @throws InvalidParameterException when width or height are initialized with zero, but not both
     * @return true if parameters are valid to initialize a Matrix
     */
    public boolean areValidParameters(Bounds bounds){
        return areValidParameters(bounds.getWidth(), bounds.getHeight(), true);
    }

    /**
     * Checks parameters of the matrix
     * @param suppressExceptions to decide whether to suppress the expression or not
     * @param width of the matrix. May only be zero if height is zero as well
     * @param height of the matrix. May only be zero if width is zero as well
     * @throws NegativeArraySizeException if one of the indices is negative
     * @throws InvalidParameterException when width or height are initialized with zero, but not both
     */
    private boolean areValidParameters(int width, int height, boolean suppressExceptions){
        boolean isNegative = width < 0 && height < 0;
        if (isNegative && !suppressExceptions) throw new NegativeArraySizeException();

        boolean isZero = width == 0 && width != height;
        if (isZero && !suppressExceptions) throw new InvalidParameterException("Matrix can only be empty if it is initialized with both, width and height at 0");

        return isZero;
    }
    //</editor-fold>

    //<editor-fold desc="Accessors">
    /**
     * @param width horizontal index for the value
     * @param heigth vertical index for the value
     * @return value at the spcified location
     * @throws ArrayIndexOutOfBoundsException if either one of the parameters is smaller than zero or greater the maximum length
     */
    public T getAt(int width, int heigth){
        if (width < 0 || heigth < 0) throw new ArrayIndexOutOfBoundsException();
        return _matrix[width][heigth];
    }

    public void setAt(int width, int height, T value){
        _matrix[width][height] = value;
    }
    //</editor-fold>

    //<editor-fold desc="Bound Information">
    public int size(){
        return _size;
    }

    public int getWidth(){
        return _matrix.length;
    }

    public Bounds bounds(){
        return new Bounds(getWidth(), getHeight());
    }

    public int getHeight(){
        return _matrix.length != 0
                ? _matrix[0].length
                : 0;
    }
    //</editor-fold>

    protected void checkBounds(ReadCollection2D<T> other){
        if (!bounds().equals(other.bounds())) {
            throw new IllegalArgumentException("Matrices must not have different sizes when multiplying");
        }
    }

    /**
     * Changes the values of the matrix based on the given computation.
     * @param other
     * @param computation
     */
    public void mutate(ReadCollection2D<T> other, BiFunction<T, T, T> computation){
        computeResult(other, computation, _matrix);
    }


    public void add(Accessible2D<T> accssor){
        throw new NoImplementationException();
    }

    public void subtract(Accessible2D<T> accssor){
        throw new NoImplementationException();
    }

    public void multiply(Number factor){
        throw new NoImplementationException();
    }

    @Override
    @ToTest
    public boolean contains(T element) {
        for (T cur : this) {
            boolean equals = Objects.equals(element, cur);
            if (equals) return true;
        }
        return false;
    }

    @Override
    @ToTest
    public boolean containsAll(Collection<T> elements) {
        HashSet<T> set = new HashSet<>(elements);

        for (T cur : this) {
            set.remove(cur);
        }

        return set.isEmpty();
    }

    @ToTest
    public void mapValues(TriFunction<Integer, Integer, T, T> function){
        Contract.checkNull(function, "function");
        for (int w = 0; w != getWidth(); ++w){
            for (int h = 0; h != getHeight(); ++h){
                T value = _matrix[h][w];
                _matrix[h][w] = function.apply(h, w, value);
            }
        }
    }

    public MutatingMatrix<T> compute(MutatingMatrix<T> other, BiFunction<T, T, T> computation){
        T[][] matrix = computeResult(other, computation);
        return new MutatingMatrix<>(matrix);
    }

    protected T[][] computeResult(ReadCollection2D<T> other, BiFunction<T, T, T> computation){
        T[][] matrix = Collection2D.toMatrix(bounds());
        computeResult(other, computation, matrix);

        return matrix;
    }

    /**
     * Returns a matrix based on the computation and the values of this and the other matrix.^
     * @param other values of the other matrix
     * @param computation the computation taking the two matrices and returns according values
     * @param matrix the out-parameter in which the values will be saved into
     */
    protected void computeResult(ReadCollection2D<T> other, BiFunction<T, T, T> computation, T[][] matrix){

        for (Coord2D coord : Iterables.of(coordIterator())) {
            T thisVal = getAt(coord);
            T otherVal = other.getAt(coord);

            T resultValue = computation.apply(thisVal, otherVal);
            coord.setAt(matrix, resultValue);
        }
    }

    /**
     *  (A B) * (E F) = (A+E B+F)
     *  (C D)   (G H)   (C+G D+H)
     * @param other matrix
     * @return
     */
    public MutatingMatrix<T> multiply(MutatingMatrix<T> other){
        checkBounds(other);
        MutatingMatrix<T> result = new MutatingMatrix<>(getWidth(), getHeight());

        for (Coord2D coord : Iterables.of(coordIterator())) {
            int y = coord.getY();
            int x = coord.getX();

        }

        return result;
    }

    /**
     * Returns a copy of the internal matrix
     * @return a copy of the internal matrix
     */
    public T[][] toArray(){
        return _matrix.clone();
    }

    //<editor-fold desc="java.lang.Object">
    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return toString(Object::toString);
    }

    public String toString(Function<T, String> mapper) {
        return Matrices.toString(_matrix, mapper);
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .append(iterator())
                .toHashCode();
    }
    //</editor-fold>

    //<editor-fold desc="Iteration">
    public ListIterator<T[]> iterateLines(){
        return Matrices.iterateLines(_matrix);
    }


    public Iterator<Coord2D> coordIterator() {
        Coord2D[] coordinates = new Coord2D[size()];

        int index = 0;
        for (int i = 0; i != _matrix.length; ++i){
            for (int j = 0; j != _matrix[i].length; ++j){
                coordinates[index++] = new Coord2D(i, j);
            }
        }

        return Iterators.of(coordinates);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator2D<>(this);
    }
    //</editor-fold>

}
