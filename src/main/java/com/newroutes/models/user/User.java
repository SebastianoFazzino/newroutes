package com.newroutes.models.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newroutes.enums.user.*;
import com.newroutes.models.BaseModel;
import com.newroutes.models.countries.CountryCode;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {

    private String username;

    private String email;

    private boolean emailConfirmed = false;

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

    private UserClass userClass = UserClass.STANDARD;

    private String authToken;

    private Date lastAuth;

    private String sendinBlueId;

    private NotificationReceptionLevel notificationReceptionLevel = NotificationReceptionLevel.ALL;

    private List<UserRole> roles = new ArrayList<>();

    private List<Log> logs = new ArrayList<>();


    public void generateAuthToken() {
        String authToken = RandomStringUtils.random(24, true, true);
        this.setAuthToken(authToken.toUpperCase());
        this.setLastAuth(new Date());
    }

    public boolean validateToken() {
        if (this.lastAuth == null) return false;
        return this.lastAuth.before(DateUtils.addMinutes(this.lastAuth, 30));
    }

}
