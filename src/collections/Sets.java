package collections;

import collections.iterator.Iterables;
import collections.iterator.Iterators;
import essentials.annotations.ToTest;

import java.util.*;

/**
 * @author Patrick
 * @since 28.05.2017
 */
public final class Sets {

    @SafeVarargs
    public static <T> Set<T> of(T... items){
        return of(Iterables.of(items), items.length);
    }

    public static <T> Set<T> of(Iterable<T> iterable){
        return of(iterable, 16); // Default Capacity of HashMap, as of JDK 9.
    }

    public static <T> Set<T> of(Iterable<T> iterable, int size){
        HashSet<T> set = new HashSet<>(size);
        iterable.forEach(set::add);
        return set;
    }

    public static <T> Set<T> of(Collection<T> collection){
        HashSet<T> set = new HashSet<>(collection.size());
        set.addAll(collection);
        return set;
    }

    /**
     * Creates the disjoint set of two collections.
     * @param <T> Element type match the collection. This is irrelevant for the algorithm. However, it should override hashCode().
     * @return a set that contains all unique elements within set1 and set2.
     */
    public static <T> Set<T> disjoint(Collection<T> set1, Collection<T> set2) {
        if (set1.isEmpty()) return Sets.of(set2);
        if (set2.isEmpty()) return Sets.of(set1);

        int setSize = Math.max(set1.size(), set2.size());
        HashSet<T> hashSet = new HashSet<>(setSize);

        addUnique(set1, set2, hashSet);
        addUnique(set2, set1, hashSet);

        return hashSet;
    }

    private static <T> void addUnique(Collection<T> primary, Collection<T> secondary, Collection<T> container) {
        for (T item : primary) {
            if (!secondary.contains(item)) {
                container.add(item);
            }
        }
    }

    /**
     * Creates the intersection between two collections.
     * The collections can be iof any collection type.
     * @implNote This algorithm assumes that the smaller collection has a {@code contains} runtime complexity of O(1).
     * @param <T> Element type of the collection. This is irrelevant for the algorithm. However, it should override hashCode();
     * @return a set that contains all common elements between set1 and set2.
     */
    @ToTest
    public static <T> Set<T> intersect(Collection<? extends T> set1, Collection<? extends T> set2){
        // Return empty set if either set is empty.
        if (set1.isEmpty() || set2.isEmpty()){
            return Collections.emptySet();
        }

        Set<T> intersection = new HashSet<>();

        for (T item : set1) {
            if (set2.contains(item)){
                intersection.add(item);
            }
        }

        return intersection;
    }

    /**
     * @param collections is an implicit array of all collections
     * @return a set of objects shared among all provided collections.
     */
    @ToTest
    @SafeVarargs
    public static <T, C extends Set<T>> Set<T> intersectAll(C... collections) {
        if (collections.length == 0) {
            return Collections.emptySet();
        }

        return intersectAll(Iterators.of(collections));
    }

    /**
     * @param setIterator is an iterator providing all sets
     * @return a set of objects shared among all provided sets.
     */
    @ToTest
    public static <T, C extends Collection<T>> Set<T> intersectAll(Iterator<C> setIterator){
        if (!setIterator.hasNext()){
            return Collections.emptySet();
        }
        // Initialize intersection set with first set. This also sets the maximum possible size of the intersection.
        Set<T> intersection = new HashSet<>(setIterator.next());

        // All subsequent sets are compared and create a subset of the intersection.
        while (setIterator.hasNext() && !intersection.isEmpty()){
            intersection = intersect(intersection, setIterator.next());
        }

        return intersection;
    }
}
