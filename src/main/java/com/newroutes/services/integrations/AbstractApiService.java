package com.newroutes.services.integrations;


import com.newroutes.models.responses.utility.EmailValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Slf4j
@Service
public class AbstractApiService {

    @Value("${integrations.abstract-api.email-validator.baseurl}")
    public String baseUrl;

    @Value("${integrations.abstract-api.email-validator.apikey}")
    public String apiKey;

    private final WebClient webClient;

    public AbstractApiService() {
        this.webClient = WebClient.builder().build();
    }


    /**
     * Validate email address
     * @param email
     * @return
     */
    public EmailValidationResponse validateEmail(String email) {

        log.info("[AAPI] - Validating email address {}", email);

        String uri = String.format("%s/?api_key=%s&email=%s", baseUrl, apiKey, email);

        try {
            return webClient.get()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(EmailValidationResponse.class)
                    .block();

        } catch (WebClientException ex) {

            log.error("Error on validating email '{}' => {}", email, ex.getMessage());
            return null;
        }
    }

}
