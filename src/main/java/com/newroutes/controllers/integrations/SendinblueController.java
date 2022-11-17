package com.newroutes.controllers.integrations;

import com.newroutes.services.integrations.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sibModel.CreateContact;
import sibModel.CreateUpdateContactModel;
import sibModel.GetAccount;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/sendinblue"})
public class SendinblueController {

    private final SendinblueService sendinblueService;

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
}

