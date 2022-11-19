package com.newroutes.repositories;

import com.newroutes.entities.sendinblue.SendinBlueUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SendinblueUserRepository extends JpaRepository<SendinBlueUserEntity, UUID> {

    Optional<SendinBlueUserEntity> findByUserId(UUID userId);
}
