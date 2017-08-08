# AccumulableIteration
### Test Code
```java
List<String> originalList = Arrays.asList("0", "1", "2");
Range<String, String> range = AccumulableRange.createRange(originalList, (item) -> item);
for ( int i = 0; i < 2; i++) {
 range = AccumulableRange.createRange(originalList, range.execute(), (prev, item) -> prev + ":" + item);
}
Iterable<String> result = range.execute();
```
### Result
|result|
|-|
| 0:0:0 |
|1:0:0|
|2:0:0|
|0:1:0|
|1:1:0|
|2:1:0|
|....|
|2:1:2|
|0:2:2|
|1:2:2|
|2:2:2|
