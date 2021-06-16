package io.devzona.springboot.emailproducer.exception;

public class EmailProducerException extends RuntimeException {
    private static final long serialVersionUID = 2177384894681724459L;

    public EmailProducerException(String msg) {
        super(msg);
    }
}
