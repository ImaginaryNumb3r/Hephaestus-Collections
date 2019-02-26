package collections;

import essentials.annotations.ToTest;
import essentials.collections.ArrayIterator;
import essentials.contract.InstanceNotAllowedException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * @author Patrick
 * @since 05.05.2017
 */
@SuppressWarnings("WeakerAccess")
@ToTest
public final class Lists {

    private Lists(){ throw new InstanceNotAllowedException(getClass());}

    //<editor-fold desc="LinkedList">
    private static <T, L extends List<T>> L toList(Iterable<T> iterable, Supplier<L> supplier){
        L list = supplier.get();
        iterable.forEach(list::add);
        return list;
    }

    public static <T> LinkedList<T> toLinkedList(Iterable<T> iterable){
        return toList(iterable, LinkedList::new);
    }

    public static <T> LinkedList<T> toLinkedList(Iterator<T> iterator){
        return toList(() -> iterator, LinkedList::new);
    }

    @SafeVarargs
    public static <T> LinkedList<T> toLinkedList(T... array){
        return toList(() -> ArrayIterator.of(array), LinkedList::new);
    }

    public static <T> LinkedList<T> toLinkedList(T item){
        LinkedList<T> list = new LinkedList<>();
        list.add(item);
        return list;
    }

    public static <T> LinkedList<T> toSingleLinkedList(T item){
        LinkedList<T> list = new LinkedList<>();
        list.add(item);
        return list;
    }
    //</editor-fold>

    //<editor-fold desc="ArrayList">
    public static <T> ArrayList<T> toArrayList(Iterable<T> iterable){
        return toList(iterable, ArrayList::new);
    }

    public static <T> ArrayList<T> toArrayList(Iterator<T> iterator){
        return toList(() -> iterator, ArrayList::new);
    }

    @SafeVarargs
    public static <T> ArrayList<T> toArrayList(T... array){
        return toList(() -> ArrayIterator.of(array), ArrayList::new);
    }

    public static <T> ArrayList<T> toArrayList(T item){
        ArrayList<T> list = new ArrayList<>();
        list.add(item);
        return list;
    }

    public static <T> T[] toArray(Collection<T> collection, Function<Integer, T[]> arrayConstructor) {
        T[] array = arrayConstructor.apply(collection.size());

        int i = 0;
        for (T item : collection) {
            array[i++] = item;
        }

        return array;
    }
    //</editor-fold>

    //<editor-fold desc="Construction">
    public static <T> List<T> toList(@NotNull Iterable<T> iterable){
        return toArrayList(iterable);
    }

    public static <T> List<T> toList(@NotNull Iterator<T> iterator){
        return toArrayList(() -> iterator);
    }
    
    //</editor-fold>
}