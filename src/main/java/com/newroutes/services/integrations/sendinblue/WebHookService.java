package com.newroutes.services.integrations.sendinblue;

import com.newroutes.entities.sendinblue.WebHookEntity;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.enums.sendinblue.WebHookEvent;
import com.newroutes.models.mappers.sendinblue.WebHookMapper;
import com.newroutes.models.sendinblue.WebHook;
import com.newroutes.models.user.User;
import com.newroutes.repositories.sendinblue.WebHookRepository;
import com.newroutes.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookService {

    private final WebHookMapper mapper;
    private final WebHookRepository repository;
    private final UserService userService;


    /**
     * Save or update existing Web Hook
     * @param webHook
     */
    public void manageWebHook(WebHook webHook) {

        log.info("[SIB] - Received Web Hook hook {}", webHook);

        Optional<WebHookEntity> optionalWebHook = repository.findByEmailAndTemplateIdAndEvent(
                webHook.getEmail(), webHook.getTemplateId(), webHook.getEvent());

        if (optionalWebHook.isEmpty() ) {

            log.info("Mapping Web Hook to entity");
            WebHookEntity entity = mapper.convertToEntity(webHook);
            User user = userService.getByUsername(entity.getEmail());
            entity.setUserId(user.getId());
            repository.save(entity);

        } else {

            log.info("Updating existing Web Hook of event type {}", webHook.getEvent());
            WebHookEntity entity = optionalWebHook.get();
            mapper.mergeInfo(mapper.convertToEntity(webHook), entity);
            repository.save(entity);
        }
    }

    /**
     * Get all by Web Hook id
     * @param webHookId
     * @return
     */
    public List<WebHookEntity> getAllByWebHookId(Long webHookId) {
        log.info("Requested get all Web Hooks by id {}", webHookId);
        return repository.getAllByWebHookId(webHookId);
    }

    /**
     * Get all by Template
     * @param template
     * @return
     */
    public List<WebHookEntity> getAllByTemplate(Template template) {
        log.info("Requested get all Web Hooks for Template {}", template);
        return repository.getAllByTemplate(template);
    }

    /**
     * Get all by User
     * @param userId
     * @return
     */
    public List<WebHookEntity> getAllByUserId(UUID userId) {
        log.info("Requested get all Web Hooks for User {}", userId);
        return repository.getAllByUserId(userId);
    }

    /**
     * Get all by event type
     * @param event
     * @return
     */
    public List<WebHookEntity> getAllByEvent(WebHookEvent event) {
        log.info("Requested get all Web Hooks of type {}", event);
        return repository.getAllByEvent(event);
    }

}
