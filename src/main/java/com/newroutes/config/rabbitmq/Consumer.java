package com.newroutes.config.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newroutes.models.rabbitmq.notification.NotificationEvent;
import com.newroutes.models.rabbitmq.notification.NotificationEventData;
import com.newroutes.models.rabbitmq.user.UserEvent;
import com.newroutes.models.user.User;
import com.newroutes.services.integrations.sendinblue.EmailService;
import com.newroutes.services.integrations.sendinblue.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.newroutes.config.rabbitmq.RabbitMqConfig.NOTIFICATION_QUEUE;
import static com.newroutes.config.rabbitmq.RabbitMqConfig.USER_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class Consumer {

    private final EmailService emailService;
    private final SendinblueService sendinblueService;

    @RabbitListener(queues = {USER_QUEUE})
    public void consumeUserEvents(String event) {
        log.info("Received message [{}]", event);

        try {

            Gson gson = new Gson();
            UserEvent userEvent = gson.fromJson(event, new TypeToken<UserEvent>() {}.getType());
            User user = userEvent.getPayload();

            switch (userEvent.getEventType()) {

                case USER_SIGNUP -> sendinblueService.createContact(user);
                case USER_LOGIN, UPDATE_USER -> sendinblueService.updateContact(user);
                case DELETE_USER -> sendinblueService.deleteContact(user);
            }

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @RabbitListener(queues = {NOTIFICATION_QUEUE})
    public void consumeNotificationEvents(String event) {
        log.info("Received message [{}]", event);

        try {

            Gson gson = new Gson();
            NotificationEvent notificationEvent = gson.fromJson(event, new TypeToken<NotificationEvent>() {}.getType());
            NotificationEventData data = notificationEvent.getPayload();

            switch (notificationEvent.getEventType()) {

                case CONTACT_CREATED -> emailService.sendWelcomeEmail(data.getUserId());
            }

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
