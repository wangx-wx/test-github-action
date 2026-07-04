package com.example.aichat.exception;

public class ChatServiceException extends RuntimeException {

    private final String errorCode;

    public ChatServiceException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
