package com.newroutes.exceptions.post;

import javax.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {

    public PostNotFoundException(String message) {
        super(message);
    }
}
