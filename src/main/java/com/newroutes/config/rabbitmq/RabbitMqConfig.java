package com.newroutes.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    //user
    public static final String USER_QUEUE = "user-queue";
    public static final String USER_EXCHANGE = "user-exchange";

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, false);
    }

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(USER_EXCHANGE, false, true);
    }

    @Bean
    Binding userBinding() {
        return BindingBuilder.bind(this.userQueue()).to(this.userExchange()).withQueueName();
    }

    //notification
    public static final String NOTIFICATION_QUEUE = "notification-queue";
    public static final String NOTIFICATION_EXCHANGE = "notification-exchange";

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, false);
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(NOTIFICATION_EXCHANGE, false, true);
    }

    @Bean
    Binding notificationBinding() {
        return BindingBuilder.bind(this.notificationQueue()).to(this.notificationExchange()).withQueueName();
    }


}
