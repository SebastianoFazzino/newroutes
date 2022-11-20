package com.newroutes.entities.user;

import com.google.gson.Gson;
import com.newroutes.enums.user.*;
import com.newroutes.models.countries.CountryCode;
import com.newroutes.models.user.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "archived_user")
public class ArchivedUser {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    private Date createdAt;

    private Date lastModifiedAt;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @ToString.Exclude
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
    private UserClass userClass;

    private String authToken;

    private Date lastAuth;

    private String sendinBlueId;

    @Enumerated(EnumType.STRING)
    private NotificationReceptionLevel notificationReceptionLevel;

    @Column(columnDefinition = "TEXT")
    private String roles;

    @ToString.Exclude
    @Column(columnDefinition = "TEXT")
    private String logs;

    public void addDeletedLog() {

        Log deleteUserLog = new Log();
        deleteUserLog.setId(UUID.randomUUID());
        deleteUserLog.setUserId(this.getId());
        deleteUserLog.setLogMessage("Requested delete and unsubscribe user");
        deleteUserLog.setType(LogOperationType.DELETE_USER);
        deleteUserLog.setCreatedAt(new Date());
        deleteUserLog.setLastModifiedAt(new Date());

        Gson gson = new Gson();
        this.logs += gson.toJson(deleteUserLog);
    }
}
