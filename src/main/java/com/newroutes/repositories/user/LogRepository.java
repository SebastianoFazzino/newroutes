package com.newroutes.repositories.user;

import com.newroutes.entities.user.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, UUID> {

}
