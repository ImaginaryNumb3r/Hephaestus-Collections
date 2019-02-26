package collections.iteration;

import org.junit.Test;

import java.util.Objects;

/**
 * @author Patrick
 * @since 19.01.2018
 */
public class MatchSinkTest {

    @Test
    public void testMatchAll() {
        Integer[] negatives = {-1, -2, -3, -12283, -90124};

        boolean allMatch = Iteration.of(negatives)
                .allMatch(val -> val < 0);
        assert allMatch;

        boolean noneMatch = Iteration.of(negatives)
                .allMatch(val -> val >= 0);
        assert !noneMatch;
    }

    @Test
    public void testMatchNone() {
        Integer[] negatives = {-1, -2, -3, -12283, -90124};

        boolean allMatch = Iteration.of(negatives)
                .noneMatch(val -> val < 0);
        assert !allMatch;

        boolean noneMatch = Iteration.of(negatives)
                .noneMatch(val -> val >= 0);
        assert noneMatch;
    }

    @Test
    public void testMatchAny() throws Exception {
        Integer[] negatives = {-1, -2, -3, -12283, null, -90124, null};

        boolean anyMatch = Iteration.of(negatives)
                .anyMatch(Objects::isNull);
        assert anyMatch;
    }
}