package com.newroutes.controllers.integrations;

import com.newroutes.services.integrations.sendinblue.EmailService;
import com.newroutes.services.integrations.sendinblue.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sibModel.CreateContact;
import sibModel.CreateUpdateContactModel;
import sibModel.GetAccount;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/account-get")
    public ResponseEntity<GetAccount> getAccount() {
        return ResponseEntity.ok(sendinblueService.getAccount());
    }

    //*************************************************************************
    // Contacts API

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/contact-create")
    public ResponseEntity<CreateUpdateContactModel> createContact(@RequestParam CreateContact createContact) {
        return ResponseEntity.ok(sendinblueService.createContact(createContact));
    }

    //*************************************************************************
    // Transactional emails

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/transactional/welcome/{userId}")
    public ResponseEntity<Object> sendWelcomeEmail(@PathVariable("userId") UUID userId) {
        emailService.sendWelcomeEmail(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

