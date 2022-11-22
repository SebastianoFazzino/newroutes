package com.newroutes.controllers.integrations;


import com.newroutes.models.responses.trueway.GeocodingResponse;
import com.newroutes.models.responses.utility.EmailValidationResponse;
import com.newroutes.services.integrations.AbstractApiService;
import com.newroutes.services.integrations.TrueWayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/utility"})
public class UtilityController {

    private final TrueWayService trueWayService;
    private final AbstractApiService abstractApiService;

    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/geocode")
    public ResponseEntity<GeocodingResponse> geocode(
            @RequestParam boolean reverse,
            @RequestParam String address
    ) {
        return ResponseEntity.ok(trueWayService.geocode(reverse, address));
    }


    @PreAuthorize("hasAuthority(@securityConfig.ADMIN)")
    @GetMapping("/validate/{email}")
    public ResponseEntity<EmailValidationResponse> validateEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(abstractApiService.validateEmail(email));
    }
}
