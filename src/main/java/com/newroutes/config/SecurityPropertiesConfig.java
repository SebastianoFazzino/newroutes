package com.newroutes.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

@Data
@Configuration
public class SecurityPropertiesConfig {

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

    public String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
