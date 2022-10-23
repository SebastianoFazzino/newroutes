package com.newroutes.models.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequestData {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
