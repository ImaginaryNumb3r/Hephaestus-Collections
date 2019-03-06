package collections.iteration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Patrick
 * @since 16.01.2018
 */
public class WhileOpsTest {

    @Test
    public void testAbort() {
        Boolean[] booleans = {true, true, true, true, false, true, true};
        int expectedCount = 4;

        Iteration<Boolean> primary = Iteration.of(booleans);
        IterationEx<Boolean, RuntimeException> whileFilter = primary.doWhile(item -> item);

        long count = whileFilter.count();
        assertEquals(expectedCount, count);
    }

    @Test
    public void testNoAbort() {
        Boolean[] booleans = {true, true, true, true, true, true, true};
        int expectedCount = 7;

        Iteration<Boolean> primary = Iteration.of(booleans);
        Iteration<Boolean> whileFilter = primary.doWhile(item -> item);

        long count = whileFilter.count();
        assertEquals(expectedCount, count);
    }
}
