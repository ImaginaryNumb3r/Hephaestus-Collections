package collections.iteration;

import collections.Lists;
import collections.iterator.Iterators;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Patrick
 * @since 15.01.2018
 */
public class MappingOpsTest {

    @Test
    public void testMapping() {
        int[] intValues = {0, 1, 2, 3, 4, 5};
        String[] stringValues = {"0", "1", "2", "3", "4", "5"};
        ListIterator<Integer> iterator = Iterators.of(intValues);
        List<String> strings = Arrays.asList(stringValues);

        List<String> mappedList = Iteration.of(iterator)
                .map(Object::toString)
                .collect(Collectors.toList());

        assert Objects.equals(strings, mappedList);
    }

    @Test
    public void testIndexMapping() {
        String[] strings = {"a", "ba", null, "ao", "%"};
        Integer[] indices = {0, 1, 2, 3, 4};

        ListIterator<String> iterator = Iterators.of(strings);

        List<Integer> indexList = Iteration.of(iterator)
                .mapIndices((val, index) -> index)
                .collect(Collectors.toList());

        ArrayList<Integer> expectedIndices = Lists.toArrayList(indices);
        assert Objects.equals(expectedIndices, indexList);
    }
}