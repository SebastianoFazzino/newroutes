package com.newroutes.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!local")
public class WebSecurityConfig {

    @Autowired
    SecurityConfig securityProps;

    @Autowired
    CustomAuthorizationFilter customAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/healthz").permitAll();
        http.authorizeRequests().antMatchers("/v1/user/public/**").permitAll();
        http.authorizeRequests().antMatchers("/v1/authentication/public/**").permitAll();
        http.authorizeRequests().antMatchers("/v1/sendinblue/hook/public/**").permitAll();
        http.authorizeRequests().antMatchers("/swagger/**", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
