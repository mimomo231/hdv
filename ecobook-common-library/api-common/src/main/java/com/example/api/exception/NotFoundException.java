package com.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseApiException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String msg) {
        super(msg);
        super.setCode(HttpStatus.NOT_FOUND.value());
    }
}