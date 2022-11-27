package com.newroutes.config.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newroutes.models.rabbitmq.UserEvent;
import com.newroutes.models.rabbitmq.UserEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.newroutes.config.rabbitmq.RabbitMqConfig.USER_QUEUE;

@Slf4j
@Service
public class Consumer {

    @RabbitListener(queues = {USER_QUEUE})
    public void consume(String event) {
        log.info("Received message [{}]", event);

        try {

            Gson gson = new Gson();
            UserEvent userEvent = gson.fromJson(event, new TypeToken<UserEvent>() {}.getType());
            UserEventData data = userEvent.getPayload();

            log.info("Payload {}", data);

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
