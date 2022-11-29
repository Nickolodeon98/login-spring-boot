package com.example.loginspringboot.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, "Duplicate username entry"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Id is not registered"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Password is not correct");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
