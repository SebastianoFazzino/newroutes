package com.newroutes.controllers;


import com.newroutes.models.responses.trueway.GeocodingResponse;
import com.newroutes.services.integrations.TrueWayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/trueway"})
public class TrueWayController {

    private final TrueWayService trueWayService;

    @GetMapping("/geocode")
    public ResponseEntity<GeocodingResponse> getAccount(
            @RequestParam boolean reverse,
            @RequestParam String address
    ) {
        return ResponseEntity.ok(trueWayService.geocode(reverse, address));
    }
}
