package com.newroutes.entities.sendinblue;


import com.newroutes.entities.BaseEntity;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.enums.sendinblue.WebHookEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sendinblue_webhook",
        uniqueConstraints = {
                @UniqueConstraint(name = "uniqueWebHook", columnNames = {"email","event","templateId"})
})
public class WebHookEntity extends BaseEntity {

    private Long webHookId;

    @Type(type="uuid-char")
    private UUID userId;

    private String email;

    private Long templateId;

    @Enumerated(EnumType.STRING)
    private Template template;

    private String messageId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String tags;

    private String tag;

    @Enumerated(EnumType.STRING)
    private WebHookEvent event;

    private String subject;

    private String sendingIp;

    private Long ts;

    private Long tsEvent;

    private Long tsEpoch;

    private String reason;

    private String link;

    private String header;
}
