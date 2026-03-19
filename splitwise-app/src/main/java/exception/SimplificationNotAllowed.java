package exception;

public class SimplificationNotAllowed extends RuntimeException {
    public SimplificationNotAllowed(String message) {
        super(message);
    }
}
