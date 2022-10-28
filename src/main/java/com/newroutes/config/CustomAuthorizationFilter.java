package com.newroutes.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    private final SecurityPropertiesConfig securityProps;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {

        if ( request.getServletPath().equals("/v1/authentication/login")
                || request.getServletPath().equals("/v1/authentication/token-refresh")
        ) {
            filterChain.doFilter(request, response);

        } else {

            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if ( authorizationHeader != null && authorizationHeader.startsWith(securityProps.getJwtHeader()) ) {

                try {
                    // remove header to get token
                    String token = authorizationHeader.substring(securityProps.getJwtHeader().length());

                    // verify JWT
                    JWTVerifier verifier = JWT.require(securityProps.getAlgorithm()).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    // Extract User
                    String username = decodedJWT.getSubject();

                    // Extract User's roles
                    String[] roles = decodedJWT.getClaim(securityProps.getJwtClaim()).asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);

                } catch (Exception exception) {

                    log.error("Error logging in: {}", exception.getMessage());
                    this.generateErrorHeader(response, exception);
                }

            } else {

                filterChain.doFilter(request, response);

            }
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
