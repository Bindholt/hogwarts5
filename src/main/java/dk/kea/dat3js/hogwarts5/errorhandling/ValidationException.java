package dk.kea.dat3js.hogwarts5.errorhandling;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
