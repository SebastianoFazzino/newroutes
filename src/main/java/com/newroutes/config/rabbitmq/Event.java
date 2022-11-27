package com.newroutes.config.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event<T, P> implements Serializable {

    private T eventType;
    private P payload;

}
