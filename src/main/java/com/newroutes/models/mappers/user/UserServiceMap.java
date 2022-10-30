package com.newroutes.models.mappers.user;


import com.newroutes.entities.user.UserEntity;
import com.newroutes.exceptions.user.UserNotFoundException;
import com.newroutes.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceMap {

    @Autowired
    UserRepository repository;

    public UserEntity map(UUID id) {

        if (id == null) {
            return null;
        }

        return repository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("User not found by id '%s'", id)) );
    }

    public UUID map(UserEntity user) {

        if (user == null) {
            return null;
        }

        return user.getId();
    }

    /* ******************************************************** */
}
