package com.newroutes.controllers.user;

import com.newroutes.models.user.User;
import com.newroutes.models.user.UserSignupData;
import com.newroutes.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/user"})
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getById() {
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        service.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //*****************
    // Public endpoints

    @PostMapping("/public/signup")
    public ResponseEntity<User> signup(@RequestBody @Validated UserSignupData signupData) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/user/public/signup").toUriString());
        return ResponseEntity.created(uri).body(service.signup(signupData));
    }

    //*****************
}

