package com.newroutes.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newroutes.models.responses.AuthenticationResponse;
import com.newroutes.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@Service
public class TokenUtils {

    @Autowired
    UserService userService;

    @Autowired
    SecurityConfig securityProps;


    public AuthenticationResponse createAuthenticationResponse(
            String username, Authentication authentication, HttpServletRequest request) {

        log.info("Creating authentication response for User '{}'", username);

        return new AuthenticationResponse(
                authentication.isAuthenticated(),
                username,
                this.generateAccessToken(request, authentication),
                this.generateRefreshToken(request, authentication),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }

    private String generateAccessToken(HttpServletRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        log.info("Generating access token for user {}", user);

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateUtils.addHours(new Date(), securityProps.getAccessTokenExpiration()))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(securityProps.getJwtClaim(), authorities)
                .sign(securityProps.getAlgorithm());
    }

    private String generateRefreshToken(HttpServletRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        log.info("Generating refresh token for user {}", user);

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateUtils.addDays(new Date(), securityProps.getRefreshTokenExpiration()))
                .withIssuer(request.getRequestURL().toString())
                .sign(securityProps.getAlgorithm());
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if ( authorizationHeader != null && authorizationHeader.startsWith(securityProps.getJwtHeader()) ) {

            try {
                // remove header to get token
                String refreshToken = authorizationHeader.substring(securityProps.getJwtHeader().length());

                // verify JWT
                JWTVerifier verifier = JWT.require(securityProps.getAlgorithm()).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                // Extract User
                String username = decodedJWT.getSubject();
                com.newroutes.models.user.User user = userService.getByUsername(username);

                List<String> roles = user.getRoles()
                        .stream().map(role -> role.getRole().name()).toList();

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(DateUtils.addMinutes(new Date(), securityProps.getAccessTokenExpiration()))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(securityProps.getJwtClaim(), roles)
                        .sign(securityProps.getAlgorithm());

                Map<String,String> tokens = new HashMap<>();
                tokens.put(securityProps.getAccessTokenHeader(), accessToken);
                tokens.put(securityProps.getRefreshTokenHeader(), refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                this.generateErrorHeader(response, exception);
            }

        } else {

            throw new RuntimeException("Refresh token is missing");
        }
    }

    private void generateErrorHeader(HttpServletResponse response, Exception exception)
            throws IOException {

        response.setHeader(securityProps.getErrorHeader(), exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());

        Map<String,String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
