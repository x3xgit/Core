package technologycommunity.net.core.exception;

public class CoreException extends RuntimeException {
    private final String message;

    public CoreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
