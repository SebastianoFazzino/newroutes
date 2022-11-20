package com.newroutes.enums.sendinblue;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebHookEvent {

    SENT("sent"),
    DELIVERED("delivered"),
    OPENED("opened"),
    FIRST_OPENING("first_opening"),
    CLICKED("clicked"),
    SOFT_BOUNCE("soft_bounce"),
    HARD_BOUNCE("hard_bounce"),
    INVALID_EMAIL("invalid_email"),
    REFERRED("referred"),
    COMPLAINT("complaint"),
    UNSUBSCRIBED("unsubscribed"),
    PROXY_OPEN("proxy_open"),
    BLOCKED("blocked"),
    ERROR("error");

    private String name;
}
