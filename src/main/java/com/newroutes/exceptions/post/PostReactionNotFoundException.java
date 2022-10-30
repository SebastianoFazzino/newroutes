package com.newroutes.exceptions.post;

import javax.persistence.EntityNotFoundException;

public class PostReactionNotFoundException extends EntityNotFoundException {

    public PostReactionNotFoundException(String message) {
        super(message);
    }
}
