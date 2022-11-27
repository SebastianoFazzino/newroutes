package com.newroutes.config.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> implements Serializable {

    private UUID id = UUID.randomUUID();
    private String messageType;
    private T payload;
}
