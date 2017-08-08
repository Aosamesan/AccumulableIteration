package procedure;

public interface ComplexProcedure<E, P, R> {
    R invoke(P previous, E input);
}
