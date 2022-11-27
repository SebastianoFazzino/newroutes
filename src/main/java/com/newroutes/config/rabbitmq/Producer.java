package com.newroutes.config.rabbitmq;

import com.google.gson.Gson;
import com.newroutes.models.rabbitmq.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.newroutes.config.rabbitmq.RabbitMqConfig.USER_EXCHANGE;
import static com.newroutes.config.rabbitmq.RabbitMqConfig.USER_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public void sendUserEvent(UserEvent event) {

        String eventString = gson.toJson(event);

        rabbitTemplate.convertAndSend(USER_EXCHANGE, USER_QUEUE, eventString);
        log.info("User Event sent to [{}] exchange with routing key [{}]", USER_EXCHANGE, USER_QUEUE);
    }

}
