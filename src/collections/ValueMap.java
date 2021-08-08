package collections;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A map implementation which guarantees to never return null in any context.
 * It's based on the HashMap class and values non-nullability over performance.
 * The additional overhead does not change run-time complexity.
 *
 * @author Patrick Plieschnegger
 */
public class ValueMap<K, V> extends HashMap<K, V> {
    private final Supplier<V> _constructor;

    //<editor-fold desc="Constructors">

    //<editor-fold desc="Supplier Constructors">
    public ValueMap(int initialCapacity, float loadFactor, @NotNull Supplier<V> constructor) {
        super(initialCapacity, loadFactor);
        _constructor = constructor;
    }

    public ValueMap(int initialCapacity, @NotNull Supplier<V> constructor) {
        super(initialCapacity);
        _constructor = constructor;
    }

    public ValueMap(Map<? extends K, ? extends V> m, @NotNull Supplier<V> constructor) {
        super(m);
        _constructor = constructor;
    }
    //</editor-fold>

    //<editor-fold desc="Value Constructors">
    public ValueMap(int initialCapacity, float loadFactor, @NotNull V defaultValue) {
        super(initialCapacity, loadFactor);
        _constructor = () -> defaultValue;
    }

    public ValueMap(int initialCapacity, @NotNull  V defaultValue) {
        super(initialCapacity);
        _constructor = () -> defaultValue;
    }

    public ValueMap(Map<? extends K, ? extends V> m, @NotNull V defaultValue) {
        super(m);
        _constructor = () -> defaultValue;
    }
    //</editor-fold>

    //</editor-fold>

    @Override
    public V put(K key, V value) {
        Contract.checkNull(value);

        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Contract.checkNulls(m.values());

        super.putAll(m);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (containsKey(key)) {
            Contract.checkNull(value);
        }

        return super.putIfAbsent(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V value = mappingFunction.apply(key);
        Contract.checkNull(value);

        return super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Contract.checkNull(value);

        return super.merge(key, value, remappingFunction);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        for (Entry<K, V> entry : entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            Contract.checkNull(function.apply(key, value));
        }

        super.replaceAll(function);
    }
}
