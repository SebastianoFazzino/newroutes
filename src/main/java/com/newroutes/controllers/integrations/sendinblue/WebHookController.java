package com.newroutes.controllers.integrations.sendinblue;


import com.newroutes.entities.sendinblue.WebHookEntity;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.enums.sendinblue.WebHookEvent;
import com.newroutes.models.sendinblue.WebHook;
import com.newroutes.services.integrations.sendinblue.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/sendinblue/hook"})
public class WebHookController {

    private final WebHookService webHookService;

    @PostMapping("/receive")
    public ResponseEntity<Object> manageWebHook(@RequestBody WebHook webHook) {
        webHookService.manageWebHook(webHook);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/all/by-id")
    public ResponseEntity<List<WebHookEntity>> getByTemplate(@RequestParam Long id) {
        return ResponseEntity.ok(webHookService.getAllByWebHookId(id));
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/all/by-template")
    public ResponseEntity<List<WebHookEntity>> getByTemplate(@RequestParam Template template) {
        return ResponseEntity.ok(webHookService.getAllByTemplate(template));
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/all/by-user")
    public ResponseEntity<List<WebHookEntity>> getByTemplate(@RequestParam UUID userId) {
        return ResponseEntity.ok(webHookService.getAllByUserId(userId));
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/all/by-event")
    public ResponseEntity<List<WebHookEntity>> getByTemplate(@RequestParam WebHookEvent event) {
        return ResponseEntity.ok(webHookService.getAllByEvent(event));
    }

}
