package collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 19.09.2017
 * Purpose:
 */
public class ListsTest {

    @Test
    public void toArrayTest() throws Exception {
        final Integer[] values = {-13, 4711, 0};

        ArrayList<Integer> intList = Lists.toArrayList(values);
        Integer[] resultValues= Lists.toArray(intList, Integer[]::new);

        assert Arrays.equals(values, resultValues);
    }

    @Test
    public void toArrayTest2() throws Exception {
        final String[] values = {"abc", "", "\""};

        ArrayList<String> intList = Lists.toArrayList(values);
        String[] resultValues= Lists.toArray(intList, String[]::new);

        assert Arrays.equals(values, resultValues);
    }
}