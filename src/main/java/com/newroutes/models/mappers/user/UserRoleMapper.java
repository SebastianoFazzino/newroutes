package com.newroutes.models.mappers.user;

import com.newroutes.entities.user.UserRoleEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.user.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserServiceMap.class})
public interface UserRoleMapper extends BaseMapper<UserRoleEntity, UserRole> {

    @Override
    @Mapping(source = "userId", target = "user.id")
    UserRoleEntity convertToEntity(UserRole dto);

    @Override
    @Mapping(source = "user.id", target = "userId")
    UserRole convertToDto(UserRoleEntity entity);

}
