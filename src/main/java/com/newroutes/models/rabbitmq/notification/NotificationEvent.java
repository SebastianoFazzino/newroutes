package com.newroutes.models.rabbitmq.notification;

import com.newroutes.models.rabbitmq.Event;
import com.newroutes.models.rabbitmq.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationEvent extends Event<EventType, NotificationEventData> {

    public NotificationEvent(EventType eventType, NotificationEventData payload) {
        this.setEventType(eventType);
        this.setPayload(payload);
    }
}
