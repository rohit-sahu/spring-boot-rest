package io.devzona.springboot.emailproducer.exception;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String errorMessage;
    private String requestedURI;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestedURI() {
        return requestedURI;
    }

    public void callerURL(final String requestedURI) {
        this.requestedURI = requestedURI;
    }
}
