package exception;

public class InvalidOrderStateChangeException extends RuntimeException {
    public InvalidOrderStateChangeException(String message) {
        super(message);
    }
}
