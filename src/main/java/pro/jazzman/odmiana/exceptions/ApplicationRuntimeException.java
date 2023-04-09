package pro.jazzman.odmiana.exceptions;

public class ApplicationRuntimeException extends RuntimeException {
    public ApplicationRuntimeException(String message) {
        super(message);
    }

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }
}
