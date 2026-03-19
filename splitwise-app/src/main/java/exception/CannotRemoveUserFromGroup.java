package exception;

public class CannotRemoveUserFromGroup extends RuntimeException {
    public CannotRemoveUserFromGroup(String message) {
        super(message);
    }
}
