package com.newroutes.services.integrations.sendinblue;

import com.google.gson.Gson;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.models.sendinblue.SendinBlueUser;
import com.newroutes.models.sendinblue.SendinblueEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final SendinblueService sendinblueService;
    private final Gson gson = new Gson();

    public void sendWelcomeEmail(UUID userId) {
        SendinBlueUser sendinBlueUser = sendinblueService.getById(userId);
        this.sendWelcomeEmail(sendinBlueUser);
    }

    private void sendWelcomeEmail(SendinBlueUser user) {

        log.info("Sending welcome email to User {}", user.getEmail());

        HashMap<String,String> params = new HashMap<>();
        params.put("url", "https://newroutes-develop.up.railway.app/v1/authentication/public/email-confirm/" + user.getUserId());

        SendinblueEvent event = new SendinblueEvent();
        event.setEmail(user.getEmail());
        event.setTemplate(Template.WELCOME);
        event.setParams(gson.toJson(params));

        sendinblueService.sendTransactionalEmail(event);
    }
}
