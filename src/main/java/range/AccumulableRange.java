package range;

import procedure.ComplexProcedure;
import procedure.SimpleProcedure;

import java.util.Iterator;

public abstract class AccumulableRange<E, R> implements Range<E, R> {
    protected Iterable<E> baseIterable;

    protected AccumulableRange(Iterable<E> baseIterable) {
        this.baseIterable = baseIterable;
    }


    public static <E, R> Range<E, R> createRange(Iterable<E> baseIterable, SimpleProcedure<E, R> procedure) {
        return new SimpleRange<>(baseIterable, procedure);
    }

    public static <E, P, R> Range<E, R> createRange(Iterable<E> baseIterable, Iterable<P> previousIterable, ComplexProcedure<E, P, R> procedure) {
        return new ComplexRange<>(baseIterable, previousIterable, procedure);
    }

    private static class SimpleRange<E, R> extends AccumulableRange<E, R> {
        private SimpleProcedure<E, R> procedure;

        public SimpleRange(Iterable<E> baseIterable, SimpleProcedure<E, R> procedure) {
            super(baseIterable);
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

        public ComplexRange(Iterable<E> baseIterable, Iterable<P> previousIterable, ComplexProcedure<E, P, R> procedure) {
            super(baseIterable);
            this.procedure = procedure;
            this.previousIterable = previousIterable;
        }

        @Override
        public Iterable<R> execute() {
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
