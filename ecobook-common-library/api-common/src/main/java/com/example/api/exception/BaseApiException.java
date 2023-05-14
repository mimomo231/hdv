package com.example.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code = ErrorCode.DEFAULT;

    public BaseApiException() {
        super();
    }

    public BaseApiException(String message) {
        super(message);
    }
}
