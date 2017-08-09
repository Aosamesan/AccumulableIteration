import accumulate.RangeAccumulator;
import org.junit.Test;
import procedure.ProcedureUtils;
import range.AccumulableRange;

import java.util.Arrays;
import java.util.List;

public class FiniteIterableTest {

    @Test
    public void shouldAccumulate() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<String> charList = Arrays.asList("a", "b", "c");

        Iterable<String> result =
                new RangeAccumulator<String, String>()
                .append(
                    AccumulableRange.createRange(
                            Integer.class,
                            String.class,
                            list,
                            ProcedureUtils.toStringProcedure()
                    )
                ).append(
                    AccumulableRange.createRange(
                            String.class,
                            String.class,
                            String.class,
                            charList,
                            ProcedureUtils.concatAsPostfixWithMid(":")
                    )
                ).append(
                    AccumulableRange.createRange(
                            String.class,
                            String.class,
                            String.class,
                            charList,
                            ProcedureUtils.concatAsPrefixWithMid(":")
                    )
                ).execute();

        for (String s : result) {
            System.out.println(s);
        }
    }
}
