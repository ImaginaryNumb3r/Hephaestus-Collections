package collections.iteration;

import collections.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick
 * @since 19.01.2018
 */
public class IterationImplTest {

    @Test
    public void testDistinct() {
        Integer[] values = {0, 0, 1, 2, 3, 1, 4, 5, 0, null, null, 3};
        Integer[] expected = {0, 1, 2, 3, 4, 5, null};

        long count = Iteration.of(values)
                .toDistinct()
                .count();

        assert count == 7; // | 0, 1, 2, 3, 4, 5, null | == 7

        List<Integer> distinctList = Iteration.of(values)
                .toDistinct()
                .collect(Collectors.toList());

        assert distinctList.containsAll(Lists.toArrayList(expected));
    }
}