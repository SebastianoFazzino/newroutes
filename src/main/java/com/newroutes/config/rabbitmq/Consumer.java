package com.newroutes.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.newroutes.config.rabbitmq.RabbitMqConfig.QUEUE_NAME;

@Slf4j
@Service
public class Consumer {

    @RabbitListener(queues = {QUEUE_NAME})
    public void consume(Message<String> message) {
        log.info("Received message [{}]", message);
    }

}
