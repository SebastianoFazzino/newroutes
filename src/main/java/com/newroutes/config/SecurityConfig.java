package com.newroutes.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Data
@Configuration
public class SecurityConfig {

    @Value("${security.jwt.header}")
    private String jwtHeader;

    @Value("${security.jwt.claim}")
    private String jwtClaim;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.access-token.header}")
    private String accessTokenHeader;

    @Value("${security.jwt.access-token.expiration-hours}")
    private int accessTokenExpiration;

    @Value("${security.jwt.refresh-token.header}")
    private String refreshTokenHeader;

    @Value("${security.jwt.refresh-token.expiration-days}")
    private int refreshTokenExpiration;

    @Value("${security.jwt.error.header}")
    private String errorHeader;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.getJwtSecret().getBytes());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("local")
    public CustomAuthorizationFilter customAuthorizationFilterLocal() {
        return new CustomAuthorizationFilter(this, true);
    }

    @Bean
    @Profile("!local")
    public CustomAuthorizationFilter customAuthorizationFilter() {
        return new CustomAuthorizationFilter(this, false);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
