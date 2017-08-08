package procedure;

public interface SimpleProcedure<E, R> {
    R invoke(E input);
}
