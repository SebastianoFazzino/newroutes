package com.newroutes.services.integrations.sendinblue;

import com.newroutes.enums.sendinblue.Template;
import com.newroutes.models.sendinblue.SendinBlueUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final SendinblueService sendinblueService;

    public void sendWelcomeEmail(UUID userId, Long delay) {

        if (delay != null) {

            log.info("Scheduled sending Welcome Email in {} seconds", delay);

            ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
            ex.schedule(() -> {
                this.sendWelcomeEmail(userId);
            }, delay, TimeUnit.SECONDS);
        }
    }

    public void sendWelcomeEmail(UUID userId) {
        SendinBlueUser user = sendinblueService.getById(userId);
        this.sendWelcomeEmail(user);
    }

    private void sendWelcomeEmail(SendinBlueUser user) {

        log.info("Sending welcome email to User {}", user.getEmail());
        sendinblueService.sendTransactionalEmail(user.getEmail(), Template.WELCOME);
    }
}
