package com.newroutes.models.mappers.user;


import com.newroutes.entities.user.UserEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.user.User;
import com.newroutes.models.user.UserSignupData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserEntity, User> {

    void mergeSignupData(UserSignupData source, @MappingTarget User target);

}
