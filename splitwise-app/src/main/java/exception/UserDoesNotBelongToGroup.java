package exception;

public class UserDoesNotBelongToGroup extends RuntimeException {
    public UserDoesNotBelongToGroup(String message) {
        super(message);
    }
}
