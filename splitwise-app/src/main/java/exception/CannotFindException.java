package exception;

public class CannotFindException extends RuntimeException {
    public CannotFindException(String message) {
        super(message);
    }
}
