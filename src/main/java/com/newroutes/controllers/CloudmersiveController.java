package com.newroutes.controllers;

import com.newroutes.models.responses.CloudmersiveEmailValidationResponse;
import com.newroutes.services.integrations.CloudmersiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/cloudmersive"})
public class CloudmersiveController {

    private final CloudmersiveService service;

    @GetMapping("/validate")
    public ResponseEntity<CloudmersiveEmailValidationResponse> validateEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(service.validateEmail(email));
    }
}

