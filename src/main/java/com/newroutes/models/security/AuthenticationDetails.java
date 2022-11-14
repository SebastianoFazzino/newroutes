package com.newroutes.models.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDetails {

    private String jwt;

}