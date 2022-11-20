package com.newroutes.services.integrations.sendinblue;

import com.newroutes.models.sendinblue.WebHook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookService {

    private final SendinblueService sendinblueService;

    public void manageWebHook(WebHook webHook) {

        log.info(webHook.toString());
    }
}
