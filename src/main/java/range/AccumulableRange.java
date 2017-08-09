package range;

import procedure.ComplexProcedure;
import procedure.SimpleProcedure;

import java.util.Iterator;

public abstract class AccumulableRange<E, R> implements Range<E, R> {
    private Class<E> inputType;
    private Class<R> returnType;
    protected Iterable<E> baseIterable;

    protected AccumulableRange(Class<E> inputType, Class<R> returnType, Iterable<E> baseIterable) {
        this.baseIterable = baseIterable;
        this.inputType = inputType;
        this.returnType = returnType;
    }

    public Class<E> getInputType() {
        return inputType;
    }

    public Class<R> getReturnType() {
        return returnType;
    }

    public static <E, R> Range createRange(Class<E> inputType, Class<R> returnType, Iterable<E> baseIterable, SimpleProcedure<E, R> procedure) {
        return new SimpleRange<>(inputType, returnType, baseIterable, procedure);
    }

    public static <E, P, R> Range createRange(Class<P> previousType, Class<E> inputType, Class<R> returnType, Iterable<E> baseIterable, ComplexProcedure<E, P, R> procedure) {
        return new ComplexRange<>(inputType, returnType, baseIterable, procedure);
    }

    private static class SimpleRange<E, R> extends AccumulableRange<E, R> {
        private SimpleProcedure<E, R> procedure;

        public SimpleRange(Class<E> inputType, Class<R> returnType, Iterable<E> baseIterable, SimpleProcedure<E, R> procedure) {
            super(inputType, returnType, baseIterable);
            this.procedure = procedure;
        }

        @Override
        public Iterable<R> execute() {
            return () -> {
                Iterator<E> originalIterator = baseIterable.iterator();

                return new Iterator<R>() {
                    @Override
                    public boolean hasNext() {
                        return originalIterator.hasNext();
                    }

                    @Override
                    public R next() {
                        return procedure.invoke(originalIterator.next());
                    }
                };
            };
        }
    }

    private static class ComplexRange<E, P, R> extends AccumulableRange<E, R> {
        private ComplexProcedure<E, P, R> procedure;
        private Iterable<P> previousIterable;
        private boolean isPreviousIterableSet;

        public ComplexRange(Class<E> inputType, Class<R> returnType, Iterable<E> baseIterable, ComplexProcedure<E, P, R> procedure) {
            super(inputType, returnType, baseIterable);
            this.procedure = procedure;
            isPreviousIterableSet = false;
        }

        @Override
        public Range setPreviousIterable(Iterable previousIterable) {
            this.previousIterable = (Iterable<P>) previousIterable;
            isPreviousIterableSet = true;
            return this;
        }

        @Override
        public Iterable<R> execute() {
            if (!isPreviousIterableSet) {
                throw new RuntimeException();
            }

            return () -> new Iterator<R>() {
                Iterator<P> previousIterator;
                Iterator<E> currentIterator = baseIterable.iterator();
                E currentItem;
                boolean isStarted = false;

                @Override
                public boolean hasNext() {
                    return currentIterator.hasNext() || previousIterator.hasNext();
                }

                @Override
                public R next() {
                    if (!isStarted || !previousIterator.hasNext()) {
                        previousIterator = previousIterable.iterator();
                        currentItem = currentIterator.next();
                        isStarted = true;
                    }

                    return procedure.invoke(previousIterator.next(), currentItem);
                }
            };
        }
    }
}
