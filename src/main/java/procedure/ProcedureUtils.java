package procedure;

public class ProcedureUtils {
    public static <T> SimpleProcedure<T, T> identity() {
        return (p) -> p;
    }

    public static SimpleProcedure<String, String> addPrefix(String prefix) {
        return (p) -> prefix + p;
    }

    public static SimpleProcedure<String, String> addPostfix(String postfix) {
        return (p) -> p + postfix;
    }

    public static ComplexProcedure<String, String, String> concatAsPrefix() {
        return (p, i) -> i + p;
    }

    public static ComplexProcedure<String, String, String> concatAsPostfix() {
        return (p, i) -> p + i;
    }

    public static <T> SimpleProcedure<T, String> toStringProcedure() {
        return (p) -> p.toString();
    }

    public static ComplexProcedure<String, String, String> concatAsPrefixWithMid(String mid) {
        return (p, i) -> i + mid + p;
    }

    public static ComplexProcedure<String, String, String> concatAsPostfixWithMid(String mid) {
        return (p, i) -> p + mid + i;
    }
}
