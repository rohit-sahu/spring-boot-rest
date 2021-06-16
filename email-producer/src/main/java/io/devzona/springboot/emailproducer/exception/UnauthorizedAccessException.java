package io.devzona.springboot.emailproducer.exception;

public class UnauthorizedAccessException extends Exception {

	private static final long serialVersionUID = -2344309359088747309L;

	public UnauthorizedAccessException() {
		super();
	}

	public UnauthorizedAccessException(String message) {
		super(message);
	}

}
