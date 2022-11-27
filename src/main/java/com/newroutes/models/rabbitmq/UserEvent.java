package com.newroutes.models.rabbitmq;

import com.newroutes.config.rabbitmq.Event;
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
