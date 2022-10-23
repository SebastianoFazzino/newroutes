package com.newroutes.models.user;


import com.newroutes.enums.Gender;
import com.newroutes.enums.Language;
import com.newroutes.enums.UserClass;
import com.newroutes.enums.UserStatus;
import com.newroutes.models.BaseModel;
import com.newroutes.models.countries.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {

    private String username;

    private String email;

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

    private List<UserRole> roles;

}
