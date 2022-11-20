package com.newroutes.models.mappers.sendinblue;

import com.newroutes.entities.sendinblue.SendinBlueUserEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.sendinblue.SendinBlueUser;
import com.newroutes.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SendinBlueUserMapper extends BaseMapper<SendinBlueUserEntity,SendinBlueUser> {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "sms", source = "phoneNumber")
    void mergeUserData(User source, @MappingTarget SendinBlueUser target);

}
