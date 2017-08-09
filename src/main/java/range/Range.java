package range;

import exception.NotImplementedMethodException;

public interface Range<E, R> {
    Iterable<R> execute();
    Class<E> getInputType();
    Class<R> getReturnType();
    default Range setPreviousIterable(Iterable peviousIterable) {
        throw new NotImplementedMethodException("setPreviousIterable");
    }
}
