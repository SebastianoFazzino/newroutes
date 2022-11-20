package com.newroutes.controllers.integrations.sendinblue;


import com.newroutes.models.sendinblue.WebHook;
import com.newroutes.services.integrations.sendinblue.SendinblueService;
import com.newroutes.services.integrations.sendinblue.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/sendinblue"})
public class WebHookController {

    private final WebHookService webHookService;

    @PostMapping("/hook")
    public ResponseEntity<Object> sendWelcomeEmail(@RequestBody WebHook webHook) {
        webHookService.manageWebHook(webHook);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
