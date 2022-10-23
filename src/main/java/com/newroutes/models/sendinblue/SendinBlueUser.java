package com.newroutes.models.sendinblue;

import com.newroutes.enums.Gender;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class SendinBlueUser {

    private UUID userId;

    private String firstName;

    private String lastName;

    private String sms;

    private Gender gender;

    private String language;

    private String country;

    private Date birthday;

    private Date lastLogin;

}
