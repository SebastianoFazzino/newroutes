package com.newroutes.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private boolean authenticated;

    private String username;

    private String accessToken;

    private String refreshToken;

    private List<String> authorities;
}
