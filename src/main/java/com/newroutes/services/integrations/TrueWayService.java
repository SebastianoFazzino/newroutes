package com.newroutes.services.integrations;


import com.newroutes.models.responses.trueway.GeocodingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class TrueWayService {

    @Value("${integrations.true_way.baseurl}")
    public String baseUrl;

    @Value("${integrations.true_way.apikey}")
    public String apiKey;

    private final String API_KEY_HEADER = "X-RapidAPI-Key";

    private final WebClient webClient;

    public TrueWayService() {
        this.webClient = WebClient.builder().build();
    }


    public GeocodingResponse geocode(boolean reverse, String address) {

        log.info("[TW] - Requested{} geocode address {}", reverse ? " reverse" : "", address);

        String uri;

        if ( reverse ) {
            uri = "%s/ReverseGeocode?location=%s&language=en";
        } else {
            uri = "%s/Geocode?address=%s&language=en";
        }

        uri = String.format(uri, baseUrl, this.urlEncode(address));

        try {

            return webClient.get()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(API_KEY_HEADER, apiKey)
                    .retrieve()
                    .bodyToMono(GeocodingResponse.class)
                    .block();

        } catch (WebClientException ex) {

            log.error("Error on geocoding address '{}' => {}", address, ex.getMessage());
            return null;
        }
    }

    private String urlEncode(String address) {
        return URLEncoder.encode(address, StandardCharsets.UTF_8);
    }
}
