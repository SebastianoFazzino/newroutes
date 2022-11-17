package com.newroutes.config;

import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader(this.getJwtHeader());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Profile("!local")
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info().title("NewRoutes API")
                        .description("NewRoutes API").version("v1"))
                .addServersItem(new Server()
                        .url("http://localhost:9325")
                        .description("Local"))
                .addServersItem(new Server()
                        .url("http://localhost:9324")
                        .description("Local-Auth-Required"))
                .addServersItem(new Server()
                        .url("https://newroutes-develop.up.railway.app")
                        .description("Develop"))
                .addServersItem(new Server()
                        .url("https://newroutes.up.railway.app")
                        .description("Production"));
    }
}
