package com.newroutes.models.user;

import com.newroutes.enums.user.UserClass;
import com.sun.istack.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupData {

    @NonNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    @ToString.Exclude
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private UserClass userClass;

}
