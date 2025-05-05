package ro.unibuc.hello.exception;

public class InvalidCNPException extends RuntimeException {
    public InvalidCNPException(String message) {
        super(message);
    }
}
