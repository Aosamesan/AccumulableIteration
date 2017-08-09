package accumulate;

import exception.IntegrityException;
import range.Range;

import java.util.*;

public class RangeAccumulator<E, R> implements Iterable<R> {
    private List<Range> rangeList;

    public RangeAccumulator(Collection<Range> ranges) {
        rangeList = new ArrayList<>(ranges);
    }

    public RangeAccumulator() {
        this(new ArrayList<>());
    }

    public RangeAccumulator<E, R> append(Range range) {
        rangeList.add(range);
        return this;
    }

    public boolean checkIntegrity() {
        boolean result = true;
        int listSize = rangeList.size();

        if (listSize == 0) {
            return true;
        }

        Class inputType;
        Class outputType = rangeList.get(0).getReturnType();

        for (int i = 1; i < rangeList.size(); i++) {
            inputType = rangeList.get(i).getInputType();
            if (!inputType.equals(outputType)) {
                result = false;
                break;
            }
            outputType = rangeList.get(i).getReturnType();
        }

        return result;
    }

    public Iterable<R> execute() {
        boolean integrity = checkIntegrity();

        if (integrity) {
            int listSize = rangeList.size();

            if (listSize == 0) {
                return Collections.emptyList();
            }

            Iterable iterable = rangeList.get(0).execute();
            for (int i = 1; i < listSize; i++) {
                iterable = rangeList.get(i).setPreviousIterable(iterable).execute();
            }

            return (Iterable<R>) iterable;
        } else {
            throw new IntegrityException();
        }
    }

    @Override
    public Iterator<R> iterator() {
        return execute().iterator();
    }
}
