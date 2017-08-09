# AccumulableIteration
### Test Code
```java
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
```
### Result
```
a:1:a
a:2:a
a:3:a
a:4:a
a:5:a
a:1:b
a:2:b
a:3:b
a:4:b
a:5:b
a:1:c
a:2:c
a:3:c
a:4:c
a:5:c
b:1:a
b:2:a
b:3:a
b:4:a
b:5:a
b:1:b
b:2:b
b:3:b
b:4:b
b:5:b
b:1:c
b:2:c
b:3:c
b:4:c
b:5:c
c:1:a
c:2:a
c:3:a
c:4:a
c:5:a
c:1:b
c:2:b
c:3:b
c:4:b
c:5:b
c:1:c
c:2:c
c:3:c
c:4:c
c:5:c
```
