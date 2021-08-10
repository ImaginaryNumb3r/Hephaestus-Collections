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

    public ValueMap(@NotNull Supplier<V> constructor) {
        super();
        _constructor = constructor;
    }

    //</editor-fold>

    @Override
    public V get(Object key) {
        V value = super.get(key);
        if (value == null) {
            value = _constructor.get();
        }

        return value;
    }

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
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        BiFunction< K, V,  V> remapping =  (K k, V v) -> {
            V v1 = v != null ? v : _constructor.get();
            return remappingFunction.apply(k, v1);
        };

        return super.compute(key, remapping);
    }

    public V compute(K key, Function<? super V, ? extends V> remappingFunction) {
        Function<V,  V> remapping =  value -> {
            // Replace null with default and then forward to parameter function.
            V v1 = value != null ? value : _constructor.get();
            return remappingFunction.apply(v1);
        };

        return super.compute(key, (k, v) -> remapping.apply(v));
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Contract.checkNull(value);

        return super.merge(key, value, remappingFunction);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Contract.checkNull(newValue);

        return super.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V newValue) {
        Contract.checkNull(newValue);

        return super.replace(key, newValue);
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
