package com.newroutes.controllers.integrations.sendinblue;

import com.newroutes.enums.sendinblue.Template;
import com.newroutes.services.integrations.sendinblue.EmailService;
import com.newroutes.services.integrations.sendinblue.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sibModel.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/sendinblue"})
public class SendinblueController {

    private final SendinblueService sendinblueService;
    private final EmailService emailService;

    //*************************************************************************
    // Account API

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/account-get")
    public ResponseEntity<GetAccount> getAccount() {
        return ResponseEntity.ok(sendinblueService.getAccount());
    }

    //*************************************************************************
    // Contacts API

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @PostMapping("/contact-create")
    public ResponseEntity<CreateUpdateContactModel> createContact(@RequestParam CreateContact createContact) {
        return ResponseEntity.ok(sendinblueService.createContact(createContact));
    }

    //*************************************************************************
    // Transactional emails

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @PostMapping("/transactional/welcome/{userId}")
    public ResponseEntity<Object> sendWelcomeEmail(@PathVariable("userId") UUID userId) {
        emailService.sendWelcomeEmail(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //*************************************************************************
    // Templates

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/templates-all")
    public ResponseEntity<GetSmtpTemplates> getTemplates() {
        return ResponseEntity.ok(sendinblueService.getTemplates());
    }

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/template-get")
    public ResponseEntity<GetSmtpTemplateOverview> getTemplate(@RequestParam Template template) {
        return ResponseEntity.ok(sendinblueService.getTemplate(template));
    }

}

