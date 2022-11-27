package com.newroutes.models.rabbitmq.user;

import com.newroutes.models.rabbitmq.Event;
import com.newroutes.models.rabbitmq.EventType;
import com.newroutes.models.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEvent extends Event<EventType, User> {

    public UserEvent(EventType eventType, User payload) {
        this.setEventType(eventType);
        this.setPayload(payload);
    }
}
