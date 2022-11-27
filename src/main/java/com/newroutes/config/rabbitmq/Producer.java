package com.newroutes.config.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.newroutes.config.rabbitmq.RabbitMqConfig.EXCHANGE_NAME;
import static com.newroutes.config.rabbitmq.RabbitMqConfig.QUEUE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Message msg) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_NAME, msg);
        log.info("Message with id [{}] send to [{}] exchange with routing key [{}]", msg.getId(), EXCHANGE_NAME, QUEUE_NAME);
    }

}
