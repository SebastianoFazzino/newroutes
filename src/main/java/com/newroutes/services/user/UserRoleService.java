package com.newroutes.services.user;

import com.newroutes.enums.user.Role;
import com.newroutes.enums.user.UserClass;
import com.newroutes.models.mappers.user.UserRoleMapper;
import com.newroutes.models.user.User;
import com.newroutes.models.user.UserRole;
import com.newroutes.repositories.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository repository;
    private final UserRoleMapper mapper;


    public void addRole(User user) {

        UserRole userRole = new UserRole();
        List<UserRole> roles = new ArrayList<>();
        userRole.setRole(user.getUserClass().equals(UserClass.STANDARD) ? Role.USER : Role.ADMIN);
        userRole.setUserId(user.getId());

        roles.add(userRole);
        user.setRoles(roles);

        log.info("Adding Role '{}' to User {}", userRole.getRole(), user.getEmail());
        repository.save(mapper.convertToEntity(userRole));
    }
}
