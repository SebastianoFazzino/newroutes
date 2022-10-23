package com.newroutes.models.requests;

import com.newroutes.enums.LoginSource;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private LoginSource loginSource = LoginSource.WEBSITE;
}
