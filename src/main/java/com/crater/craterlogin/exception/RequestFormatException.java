package com.crater.craterlogin.exception;

public class RequestFormatException extends RuntimeException{
    public RequestFormatException() {
    }

    public RequestFormatException(String message) {
        super(message);
    }

    public RequestFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
