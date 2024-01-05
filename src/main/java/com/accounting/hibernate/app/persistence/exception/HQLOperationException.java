package com.accounting.hibernate.app.persistence.exception;

// todo create own custom runtime exception
public class HQLOperationException extends RuntimeException {
    public HQLOperationException(String message) {
        super(message);
    }

    public HQLOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HQLOperationException(Throwable cause) {
        super(cause);
    }
}
