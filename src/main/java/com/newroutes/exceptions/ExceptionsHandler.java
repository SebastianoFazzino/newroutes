package com.newroutes.exceptions;

import com.newroutes.exceptions.post.PostNotFoundException;
import com.newroutes.exceptions.post.PostReactionNotFoundException;
import com.newroutes.exceptions.user.BadCredentialException;
import com.newroutes.exceptions.user.EmailNotValidException;
import com.newroutes.exceptions.user.UserAlreadyExistsException;
import com.newroutes.exceptions.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

    //********************************************************************************
    // User related exceptions

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<Object> handleEmailNotValidException(
            EmailNotValidException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<Object> handleBadCredentialException(
            BadCredentialException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(
            UserAlreadyExistsException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    //********************************************************************************
    // Post related exceptions

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(
            PostNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostReactionNotFoundException.class)
    public ResponseEntity<Object> handlePostReactionNotFoundException(
            PostReactionNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
