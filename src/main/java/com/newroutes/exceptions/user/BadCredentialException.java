package com.newroutes.exceptions.user;

public class BadCredentialException extends RuntimeException {

    public BadCredentialException(String message) {
        super(message);
    }
}
