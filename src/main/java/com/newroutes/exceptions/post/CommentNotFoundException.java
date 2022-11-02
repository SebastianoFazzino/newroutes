package com.newroutes.exceptions.post;

import javax.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {

    public CommentNotFoundException(String message) {
        super(message);
    }
}
