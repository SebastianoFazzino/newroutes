package com.newroutes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {

    private UUID id;

    private Date createdAt;

    private Date lastModifiedAt;

}
