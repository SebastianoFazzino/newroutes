package com.newroutes.repositories.sendinblue;

import com.newroutes.entities.sendinblue.WebHookEntity;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.enums.sendinblue.WebHookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WebHookRepository extends JpaRepository<WebHookEntity, UUID> {

    Optional<WebHookEntity> findByWebHookIdAndEvent(Long id, WebHookEvent event);

    List<WebHookEntity> getAllByWebHookId(Long id);

    List<WebHookEntity> getAllByTemplate(Template template);

    List<WebHookEntity> getAllByUserId(UUID userId);

    List<WebHookEntity> getAllByEvent(WebHookEvent event);

}
