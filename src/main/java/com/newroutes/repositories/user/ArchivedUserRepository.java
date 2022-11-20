package com.newroutes.repositories.user;

import com.newroutes.entities.user.ArchivedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArchivedUserRepository extends JpaRepository<ArchivedUser, UUID> {

    Optional<ArchivedUser> findByEmail(String email);
}
