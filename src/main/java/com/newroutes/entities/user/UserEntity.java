package com.newroutes.entities.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newroutes.entities.BaseEntity;
import com.newroutes.enums.*;
import com.newroutes.models.countries.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uniqueUsername", columnNames = {"username"}),
        @UniqueConstraint(name = "uniqueEmail", columnNames = {"email"}),
        @UniqueConstraint(name = "uniqueAuthToken", columnNames = {"authToken"}),
        @UniqueConstraint(name = "uniqueSendinBlueId", columnNames = {"sendinBlueId"}),
})
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private CountryCode country;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserClass userClass = UserClass.STANDARD;

    private String authToken;

    private Date lastAuth;

    private String sendinBlueId;

    @Enumerated(EnumType.STRING)
    private NotificationReceptionLevel notificationReceptionLevel;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "user"
    )
    private List<UserRoleEntity> roles;

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<LogEntity> logs;

}
