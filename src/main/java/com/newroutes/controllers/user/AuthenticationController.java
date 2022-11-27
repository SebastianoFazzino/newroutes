package com.newroutes.controllers.user;

import com.newroutes.config.security.TokenUtils;
import com.newroutes.exceptions.user.BadCredentialException;
import com.newroutes.models.requests.AuthenticationRequest;
import com.newroutes.models.responses.user.AuthenticationResponse;
import com.newroutes.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/authentication"})
public class AuthenticationController {

    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PreAuthorize("hasAuthority({@securityConfig.ADMIN, @securityConfig.USER})")
    @GetMapping("/token-refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        tokenUtils.refreshToken(request, response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {

        String username = authenticationRequest.getUsername().trim();
        String password = authenticationRequest.getPassword().trim();

        log.info("Trying authenticating User '{}'", username);

        try {

            //**************************************************

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            AuthenticationResponse response = tokenUtils
                    .createAuthenticationResponse(username, authentication, request);

            //**************************************************

            log.info("User '{}' logged successfully!", username);
            userService.updateLastLogin(username, authenticationRequest.getLoginSource(), request);

            //**************************************************

            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            throw new BadCredentialException(String.format(
                    "Bad Credentials, either User '%s' does not exist or password is wrong", username));
        }
    }

}
