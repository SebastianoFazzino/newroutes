package com.newroutes.models.user;

import com.newroutes.enums.LogOperationType;
import com.newroutes.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log extends BaseModel {

    private UUID userId;

    private LogOperationType type;

    private String logMessage;

}
