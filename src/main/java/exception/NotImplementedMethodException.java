package exception;

public class NotImplementedMethodException extends RuntimeException {

    public NotImplementedMethodException(String methodName) {
        super("Not implemented : " + methodName);
    }
}
