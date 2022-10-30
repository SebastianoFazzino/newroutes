package com.newroutes.models.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newroutes.enums.*;
import com.newroutes.models.BaseModel;
import com.newroutes.models.countries.CountryCode;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {

    private String username;

    private String email;

    @JsonIgnore
    @ToString.Exclude
    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Language language = Language.EN;

    private CountryCode country;

    private Gender gender;

    private Date birthday;

    private Date lastLogin;

    private UserStatus status = UserStatus.ACTIVE;

    private UserClass userClass;

    private String authToken;

    private Date lastAuth;

    private String sendinBlueId;

    private NotificationReceptionLevel notificationReceptionLevel = NotificationReceptionLevel.ALL;

    private List<UserRole> roles;

}
