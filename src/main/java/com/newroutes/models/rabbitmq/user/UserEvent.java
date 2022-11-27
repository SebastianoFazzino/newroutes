package com.newroutes.models.rabbitmq.user;

import com.newroutes.models.rabbitmq.Event;
import com.newroutes.models.rabbitmq.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEvent extends Event<EventType, UserEventData> {

    public UserEvent(EventType eventType, UserEventData payload) {
        this.setEventType(eventType);
        this.setPayload(payload);
    }
}
