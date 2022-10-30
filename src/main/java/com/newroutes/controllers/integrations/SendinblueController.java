package com.newroutes.controllers.integrations;

import com.newroutes.services.integrations.SendinblueService;
import com.newroutes.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;

    //*************************************************************************
    // Account API

    @GetMapping("/account-get")
    public ResponseEntity<GetAccount> getAccount() {
        return ResponseEntity.ok(sendinblueService.getAccount());
    }

    //*************************************************************************
    // Contacts API

    @PostMapping("/contact-create")
    public ResponseEntity<CreateUpdateContactModel> createContact(@RequestParam CreateContact createContact) {
        return ResponseEntity.ok(sendinblueService.createContact(createContact));
    }
}

