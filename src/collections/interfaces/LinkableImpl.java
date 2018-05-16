package collections.interfaces;

import java.util.function.Function;

/**
 * Creator: Patrick
 * Created: 19.08.2017
 * Purpose:
 */
// TODO: Make Minimal Value Type
public class LinkableImpl<T> implements Linkable<T, LinkableImpl<T>> {
    private final T _value;
    private final Function<T, T> _advanceFunc;

    public LinkableImpl(T value, Function<T, T> advanceFunc) {
        _value = value;
        _advanceFunc = advanceFunc;
    }

    @Override
    public T value() {
        return _value;
    }

    @Override
    public LinkableImpl<T> next() {
        return new LinkableImpl<>(_advanceFunc.apply(_value), _advanceFunc);
    }
}
