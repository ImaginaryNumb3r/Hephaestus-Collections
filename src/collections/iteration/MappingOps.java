package collections.iteration;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Patrick
 * @since 14.01.2018
 */
public class MappingOps<in, out> extends ComputationPipe<in, out> {
    private final BiFunction<in, Integer, out> _computation;

    public MappingOps(Iterator<in> aggregator,BiFunction<in, Integer, out> computation) {
        super(aggregator);
        _computation = computation;
        _cursorPos = -1;
    }

    public MappingOps(Iterator<in> aggregator,Function<in, out> computation) {
        this(aggregator, (item, index) -> computation.apply(item));
    }

    @Override
    protected out compute(in input) {
        return _computation.apply(input, _cursorPos);
    }

    @Override
    protected boolean test(in item) {
        return true;
    }
}
