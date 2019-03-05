package collections.matrix.value;

import essentials.contract.Contract;
import essentials.tuple.Tuple;
import essentials.util.HashGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Patrick
 * @since 21.05.2017
 *
 * Saves the height and width of a 2D collection.
 * Value class that only contains non-nullable values.
 */
public class Bounds implements Tuple<Integer, Integer> {
    private final int WIDTH;
    private final int HEIGHT;

    public Bounds(int width, int height) {
        Contract.checkNegative(width, "width");
        Contract.checkNegative(height, "height");

        WIDTH = width;
        HEIGHT = height;
    }

    /**
     * Performs a null check on the Tuple values and returns a Bound if they are not nullable
     * @param tuple containing the values for the bound
     * @return The Bounds fromEntries the Tuple
     */
    public static Bounds fromTuple(@NotNull Tuple<Integer, Integer> tuple){
        Contract.checkNull(tuple, "tuple");
        return new Bounds(tuple.getA(), tuple.getB());
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public Bounds invert(){
        return new Bounds(HEIGHT, WIDTH);
    }

    public <T> T[][] makeMatrix(Class<T> type){
        return (T[][]) new Object[getWidth()][getHeight()];
    }

    /**
     * Deprecated method, due to backwards comparability with Tuple.
     * Use only when referred to as a Tuple. Do not call manually to improve code readability.
     */
    @Deprecated
    @Override
    public Integer getA() {
        return WIDTH;
    }

    /**
     * Deprecated method, due to backwards comparability with Tuple.
     * Use only when referred to as a Tuple. Do not call manually to improve code readability.
     */
    @Deprecated
    @Override
    public Integer getB() {
        return HEIGHT;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Bounds
                && equals((Bounds) obj);
    }

    public boolean equals(Bounds other) {
        return WIDTH == other.WIDTH && HEIGHT == other.HEIGHT;
    }

    @Override
    public int hashCode() {
        return new HashGenerator(getClass())
                .appendAll(WIDTH, HEIGHT)
                .toHashCode();
    }
}
