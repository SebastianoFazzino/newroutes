package com.newroutes.models.sendinblue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newroutes.enums.sendinblue.WebHookEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebHook {

    @JsonProperty("id")
    private Long webHookId;

    private String email;

    @JsonProperty("template_id")
    private Long templateId;

    @JsonProperty("message-id")
    private String messageId;

    private String date;

    private List<String> tags;

    private String tag;

    private WebHookEvent event;

    private String subject;

    @JsonProperty("sending_ip")
    private String sendingIp;

    private Long ts;

    @JsonProperty("ts_event")
    private Long tsEvent;

    @JsonProperty("ts_epoch")
    private Long tsEpoch;

    private String reason;

    private String link;

    @JsonProperty("X-Mailin-custom")
    private String header;
}