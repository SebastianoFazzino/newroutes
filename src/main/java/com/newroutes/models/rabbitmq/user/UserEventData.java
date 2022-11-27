package com.newroutes.models.rabbitmq.user;

import com.newroutes.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEventData {

    private User user;
}
