package uk.ac.ebi.uniprot.variation.exception;

public class InvalidadHgvsDescription extends InvalidHgvsException {
	private static final long serialVersionUID = 1L;

	public InvalidadHgvsDescription() {
		super();
	}

	public InvalidadHgvsDescription(String message) {
		super(message);
	}

	public InvalidadHgvsDescription(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidadHgvsDescription(Throwable cause) {
		super(cause);
	}

}
