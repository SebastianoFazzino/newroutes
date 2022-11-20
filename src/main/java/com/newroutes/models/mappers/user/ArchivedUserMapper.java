package com.newroutes.models.mappers.user;


import com.newroutes.entities.user.ArchivedUser;
import com.newroutes.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserServiceMap.class})
public interface ArchivedUserMapper {

    void createFromUser(User source, @MappingTarget ArchivedUser target);
}
