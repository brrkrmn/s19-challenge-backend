package com.berrakaraman.s19_challenge_backend.exception;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends ApiException {
    public UnauthenticatedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
