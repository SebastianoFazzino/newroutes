package com.newroutes.entities.sendinblue;

import com.newroutes.enums.user.Gender;
import com.newroutes.enums.user.NotificationReceptionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sendinblue_user",
        uniqueConstraints = {
        @UniqueConstraint(name = "uniqueUsername", columnNames = {"username"}),
        @UniqueConstraint(name = "uniqueEmail", columnNames = {"email"}),
        @UniqueConstraint(name = "uniqueSendinBlueId", columnNames = {"sendinBlueId"})
})
public class SendinBlueUserEntity {

    @Id
    private UUID userId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_at")
    private Date lastModifiedAt;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String sms;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String language;

    private String country;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    private String sendinBlueId;

    @Enumerated(EnumType.STRING)
    private NotificationReceptionLevel notificationReceptionLevel;

}
