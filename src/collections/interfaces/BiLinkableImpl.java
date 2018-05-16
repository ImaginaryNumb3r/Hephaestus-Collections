package collections.interfaces;

import java.util.function.Function;

/**
 * Creator: Patrick
 * Created: 12.11.2017
 * Purpose:
 */
public class BiLinkableImpl<T>  implements BiLinkable<T> {
    private final BiLinkable<T> _previous;
    private final T _value;
    private final Function<T, T> _advanceFunc;

    public BiLinkableImpl(T value, Function<T, T> advanceFunc, BiLinkable<T> previous) {
        _previous = previous;
        _value = value;
        _advanceFunc = advanceFunc;
    }

    @Override
    public T value() {
        return _value;
    }

    @Override
    public BiLinkable<T> next() {
        T apply = _advanceFunc.apply(_value);
        return new BiLinkableImpl<>(apply, _advanceFunc, this);
    }

    @Override
    public BiLinkable<T> previous() {
        return _previous;
    }
}
