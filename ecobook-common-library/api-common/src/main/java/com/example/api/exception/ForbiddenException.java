package com.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseApiException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException(String message) {
        super(message);
        super.setCode(HttpStatus.FORBIDDEN.value());
    }

}
