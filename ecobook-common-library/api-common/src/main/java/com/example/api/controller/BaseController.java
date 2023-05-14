package com.example.api.controller;

import com.example.security.model.EcobookUserPassAuthentiactionToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class BaseController {

    protected EcobookUserPassAuthentiactionToken getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            return (EcobookUserPassAuthentiactionToken) auth;
        }
        return null;
    }

    protected String getPrincipal() {
        EcobookUserPassAuthentiactionToken authentication = getAuthentication();
        if (Objects.nonNull(authentication)) {
            return authentication.getPrincipal().toString();
        }
        return null;
    }

    protected Long getOriginalId() {
        EcobookUserPassAuthentiactionToken authentication = getAuthentication();

        if (Objects.nonNull(authentication)) {
            return authentication.getOriginalId();
        }

        return null;
    }

}