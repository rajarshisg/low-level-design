package exception;

public class CannotCreateSplit extends RuntimeException {
    public CannotCreateSplit(String message) {
        super(message);
    }
}
