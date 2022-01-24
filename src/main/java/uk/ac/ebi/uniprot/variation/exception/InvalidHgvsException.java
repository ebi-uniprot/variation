package uk.ac.ebi.uniprot.variation.exception;

public class InvalidHgvsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidHgvsException() {
        super();
    }

    public InvalidHgvsException(String message) {
        super(message);
    }

    public InvalidHgvsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHgvsException(Throwable cause) {
        super(cause);
    }

}
