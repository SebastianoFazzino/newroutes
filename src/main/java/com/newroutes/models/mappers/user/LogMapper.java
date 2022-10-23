package com.newroutes.models.mappers.user;

import com.newroutes.entities.user.LogEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.user.Log;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserServiceMap.class})
public interface LogMapper extends BaseMapper<LogEntity, Log> {

    @Override
    @Mapping(source = "userId", target = "user")
    LogEntity convertToEntity(Log dto);

    @Override
    @Mapping(source = "user", target = "userId")
    Log convertToDto(LogEntity entity);

}
