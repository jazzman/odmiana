package pro.jazzman.odmiana;

public class ApplicationRuntimeException extends RuntimeException {
    public ApplicationRuntimeException(String message) {
        super(message);
    }

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }

    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
