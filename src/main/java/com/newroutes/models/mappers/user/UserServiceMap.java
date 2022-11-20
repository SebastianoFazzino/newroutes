package com.newroutes.models.mappers.user;

import com.google.gson.Gson;
import com.newroutes.entities.user.UserEntity;
import com.newroutes.exceptions.user.UserNotFoundException;
import com.newroutes.models.user.Log;
import com.newroutes.models.user.UserRole;
import com.newroutes.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceMap {

    private final UserRepository repository;
    private final Gson gson = new Gson();

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

    public String mapRoles(List<UserRole> roles) {
        return gson.toJson(roles);
    }

    public String mapLogs(List<Log> logs) {
        return gson.toJson(logs);
    }
}
