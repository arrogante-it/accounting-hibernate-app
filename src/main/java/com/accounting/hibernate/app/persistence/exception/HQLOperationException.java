package com.accounting.hibernate.app.persistence.exception;

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
