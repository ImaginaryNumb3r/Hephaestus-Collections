package collections;

import collections.iterator.Iterables;
import collections.iterator.Iterators;
import essentials.annotations.ToTest;
import essentials.contract.InstanceNotAllowedException;
import essentials.contract.NoImplementationException;
import essentials.tuple.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;

/**
 * @author Patrick
 * @since 22.05.2017
 */
@ToTest
public final class Maps {

    /**
     * @throws InstanceNotAllowedException Cannot be instantiated
     */
    private Maps(){
        throw new InstanceNotAllowedException(getClass());
    }

    //<editor-fold desc="Factory Methods">
    //<editor-fold desc="From Keys">
    //<editor-fold desc="From Iterable">
    public static <K, V> HashMap<K, V> fromKeys(@NotNull Iterable<K> iterable, @NotNull Supplier<V> initializer){
        return fromKeys(iterable, 16, initializer);
    }

    public static <K, V> HashMap<K, V> fromKeys(@NotNull Iterable<K> iterable, int size, @NotNull Supplier<V> initializer){
        return fromKeys(iterable, size, key -> initializer.get());
    }

    public static <K, V> HashMap<K, V> fromKeys(@NotNull Iterable<K> iterable, @NotNull Function<K, V> initializer){
        return fromKeys(iterable, 16 /* Default load factory */, initializer);
    }

    public static <K, V> HashMap<K, V> fromKeys(@NotNull Iterable<K> iterable, int size, @NotNull Function<K, V> mapper){
        HashMap<K, V> map = new HashMap<>(size);
        for (K curKey : iterable) {
            map.put(curKey, mapper.apply(curKey));
        }
        return map;
    }
    //</editor-fold>

    //<editor-fold desc="From Collection">
    public static <K, V> HashMap<K, V> fromKeys(@NotNull Collection<K> collection, @NotNull Function<K, V> mapper){
        return fromKeys(collection, collection.size(), mapper);
    }

    public static <K, V> HashMap<K, V> fromKeys(@NotNull Collection<K> collection, @NotNull Supplier<V> initializer){
        return fromKeys(collection, collection.size(), ignore -> initializer.get());
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="From Values">
    //<editor-fold desc="From Iterable">

    public static <K, V> HashMap<K, V> fromValues(@NotNull Iterable<V> iterable, @NotNull Function<V, K> mapper){
        return fromValues(iterable, 16 /* Default load factory */, mapper);
    }

    public static <K, V> HashMap<K, V> fromValues(@NotNull Iterable<V> iterable, int size, @NotNull Function<V, K> mapper){
        HashMap<K, V> map = new HashMap<>(size);
        for (V curValue : iterable) {
            map.put(mapper.apply(curValue), curValue);
        }
        return map;
    }
    //</editor-fold>

    //<editor-fold desc="From Collection">
    public static <K, V> HashMap<K, V> fromValues(@NotNull Collection<V> collection, @NotNull Function<V, K> mapper){
        return fromValues((Iterable<V>) collection, mapper);
    }

    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="From Tuple">
    public static <K, V, T extends Tuple<K, V>> HashMap<K, V> fromPair(@NotNull Iterable<T> iterable){
        return fromPair(iterable, 16 /* Default load factor */);
    }

    public static <K, V, T extends Tuple<K, V>> HashMap<K, V> fromPair(@NotNull Collection<T> collection){
        return fromPair(collection, collection.size());
    }

    public static <K, V, T extends Tuple<K, V>> HashMap<K, V> fromPair(@NotNull Iterable<T> iterable, int size){
        HashMap<K, V> map = new HashMap<>(size);
        for (T keyPair : iterable) {
            K key = keyPair.getA();
            V value = keyPair.getB();
            map.put(key, value);
        }
        return map;
    }
    //</editor-fold>


    //<editor-fold desc="Map Entries">
    public static <K, V, T extends Map.Entry<K, V>> HashMap<K, V> fromEntries(@NotNull Iterable<T> iterable){
        return fromEntries(iterable, 16 /* Default load factor */);
    }

    public static <K, V, T extends Map.Entry<K, V>> HashMap<K, V> fromEntries(@NotNull Collection<T> collection){
        return fromEntries(collection, collection.size());
    }

    public static <K, V, T extends Map.Entry<K, V>> HashMap<K, V> fromEntries(@NotNull Iterable<T> iterable, int size){
        HashMap<K, V> map = new HashMap<>(size);
        for (T entry : iterable) {
            K key = entry.getKey();
            V value = entry.getValue();
            map.put(key, value);
        }
        return map;
    }
    //</editor-fold>

    //<editor-fold desc="Set Oriented Joins">
    /**
     * @param left map of values
     * @param right map of values
     * @param <V> Values that will be collected in the returning Set
     * @return A Set of all values in both maps.
     *         Because more than one value can be associated with a key, this cannot be represented as a map.
     */
    public static <K, V> Set<V> fullJoin(Map<K, V> left, Map<K, V> right){
        Set<V> intersection = new HashSet<>(left.values());
        intersection.addAll(left.values());
        intersection.addAll(right.values());

        return intersection;
    }

    /**
     * Equivalent to a left join or right join.
     * @param primary map of values
     * @param secondary map of values
     * @param <V> Values that will be collected in the returning Set
     * @return A Map matchAllSink all values matchAllSink the primary set along with all secondary values that share a key with the primary map.
     *         Because more than one value can be associated with a key, this cannot be represented as a map.
     */
    public static <K, V> Set<V> mappedJoin(Map<K, V> primary, Map<K, V> secondary){
        Set<V> intersection = new HashSet<>(primary.values());
        // Add all values from the right map if they have a corresponding key in the left map.
        customJoin(secondary, primary, primary::containsKey, intersection::add);

        return intersection;
    }

    /**
     * @param left map of values
     * @param right map of values
     * @param <V> Values that will be collected in the returning Set
     * @return A Set of all values that have an equivalent key in the other set.<br>
     *         Because more than one value can be associated with a key, this cannot be represented as a map.
     */
    public static <K, V> Set<V> innerJoin(Map<K, V> left, Map<K, V> right){
        Set<V> intersection = new HashSet<>(Math.min(left.size(), right.size()));
        customJoin(left, right, right::containsKey, intersection::add);
        customJoin(right, left, left::containsKey, intersection::add);

        return intersection;
    }
    //</editor-fold>

    public static <K, V> Map<K, V> mappedOuterJoin(Map<K, V> primary, Map<K, V> secondary){
        HashMap<K, V> disjoint = new HashMap<>();
        customJoin(primary, secondary, value -> !primary.containsKey(value), disjoint::put);
        customJoin(secondary, primary, value -> !secondary.containsKey(value), disjoint::put);

        // TODO: Also make Maps.fromEntries();
        return disjoint;
    }

    public static <K, V> Map<K, V> fullOuterJoin(Map<K, V> left, Map<K, V> right){
        HashMap<K, V> disjoint = new HashMap<>();
        customJoin(left, right, value -> !left.containsKey(value), disjoint::put);
        customJoin(right, left, value -> !right.containsKey(value), disjoint::put);

        return disjoint;
    }

    @SafeVarargs
    public static <K, V> Set<V> innerJoinAll(Map<K, V>... maps){
        return innerJoinAll(Iterators.of(maps));
    }

    @ToTest // TODO: Test
    public static <K, V> Set<V> innerJoinAll(ListIterator<Map<K, V>> keyIterator){
        if (!keyIterator.hasNext()){
            return Collections.emptySet();
        }

        // Step 1: create a set of all keys shared among all maps.
        Set<K> keySet = new HashSet<>(keyIterator.next().keySet());
        while (keyIterator.hasNext() && !keySet.isEmpty()) {
            Map<K, V> cur = keyIterator.next();
            keySet = Sets.intersect(keySet, cur.keySet());
        }

        // Step 2: For each shared key, add the values of the maps sharing the key.
        Set<V> valueSet = new HashSet<>(keySet.size());

        for (K key : keySet) {
            for (Map<K, V> map : Iterables.ofReverse(keyIterator)) {
                if (map.containsKey(key)){
                    valueSet.add(map.get(key));
                }
            }
        }

        return valueSet;
    }

    /**
     * @param maps the list of maps whose values with common keys will be returned.
     * @return All values that are mapped to the set of shared keys among all maps.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> outerJoinAll(Map<K, V>... maps){
        throw new NoImplementationException();
    }

    /**
     * @apiNote This method is not thread safe!<br>
     *          It must be ensured that the list of maps is not modified until the method has finished.
     * @param maps the list of maps whose values with common keys will be returned.
     * @return All values that are mapped to the set of shared keys among all maps.
     */
    public static <K, V> Map<K, V> outerJoinAll(Iterable<Map<K, V>> maps){
        throw new NoImplementationException();
    }

    /**
     * Custom join of two maps.
     * @apiNote It should be noted that a call to this method will only iterate the reference map and not both maps.
     * @param referenceMap The map that is being iterated.
     * @param valueMap The map whose values will be passed to the consumer.
     * @param predicate To test whether to perform the action or not
     * @param action The consumer that is used for the computation on a key-pair basis.
     */
    private static <K, V> void customJoin(Map<K, V> referenceMap, Map<K, V> valueMap,
                                          Predicate<K> predicate, Consumer<V> action){
        customJoin(referenceMap, valueMap, predicate, (key, value) -> action.accept(value));
    }

    /**
     * Custom join of two maps.
     * @apiNote It should be noted that a call to this method will only iterate the reference map and not both maps.
     * @param referenceMap The map that is being iterated.
     * @param valueMap The map whose values will be passed to the consumer.
     * @param predicate To test whether to perform the action or not
     * @param action The consumer that is used for the computation on a key-pair basis.
     */
    private static <K, V> void customJoin(Map<K, V> referenceMap, Map<K, V> valueMap,
                                          Predicate<K> predicate, BiConsumer<K, V> action){
        for (Map.Entry<K, V> entry : referenceMap.entrySet()) {
            K key = entry.getKey();
            if (predicate.test(key)){
                action.accept(key, valueMap.get(key));
            }
        }
    }

    @Deprecated
    public static <T> HashMap<Character, T> from(Supplier<T> supplier, Character... chars) {
        HashMap<Character, T> map = new HashMap<>(chars.length);

        for (int i = 0; i != chars.length; ++i){
            map.put(chars[i], supplier.get());
        }

        return map;
    }
}
