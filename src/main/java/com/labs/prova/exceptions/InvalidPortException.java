package com.labs.prova.exceptions;

public class InvalidPortException extends RuntimeException {

    private static final long serialVersionUID = 77023843150104334L;

    public InvalidPortException (String message) {
        super(message);
    }
}
