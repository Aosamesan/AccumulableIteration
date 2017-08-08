import org.junit.Test;
import range.AccumulableRange;
import range.Range;

import java.util.Arrays;
import java.util.List;

public class FiniteIterableTest {

    @Test
    public void shouldIterable4() {
        List<String> numberList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Range<String, String> range = AccumulableRange.createRange(numberList, (e) -> e);
        for (int i = 0; i < 2; i++) {
            range = AccumulableRange.createRange(numberList, range.execute(), (p, e) -> e + p);
        }
        Iterable<String> result = range.execute();

        for (String s : result) {
            System.out.println(s);
        }
    }
}
