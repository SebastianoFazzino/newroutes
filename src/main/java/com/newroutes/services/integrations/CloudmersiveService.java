package com.newroutes.services.integrations;

import com.newroutes.models.responses.CloudmersiveEmailValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CloudmersiveService {

    @Value("${integrations.cloudmersive.baseurl}")
    public String baseUrl;

    @Value("${integrations.cloudmersive.apikey}")
    public String apiKey;

    private String API_KEY_HEADER = "Apikey";

    private WebClient webClient;


    public CloudmersiveService() {
        this.webClient = WebClient.builder().build();
    }

    /**
     * Validate email addresses to avoid disposable email registration
     * @param email
     * @return
     */
    public CloudmersiveEmailValidationResponse validateEmail(String email) {

        log.info("[I] - Getting validation response for email {}", email);
        String uri = baseUrl + "/validate/email/address/syntaxOnly";

        try {

            return webClient.post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(API_KEY_HEADER, apiKey)
                    .body(Mono.just(email), CloudmersiveEmailValidationResponse.class)
                    .retrieve()
                    .bodyToMono(CloudmersiveEmailValidationResponse.class)
                    .block();

        } catch (WebClientException ex) {

            log.error("Error on validating email address '{}' => {}", email, ex.getMessage());
            return null;
        }
    }
}
