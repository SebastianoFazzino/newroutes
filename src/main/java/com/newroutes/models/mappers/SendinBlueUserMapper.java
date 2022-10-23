package com.newroutes.models.mappers;

import com.newroutes.models.sendinblue.SendinBlueUser;
import com.newroutes.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SendinBlueUserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "sms", source = "phoneNumber")
    void mergeUserData(User source, @MappingTarget SendinBlueUser target);

}
