package com.newroutes.controllers;

import com.newroutes.models.countries.Country;
import com.newroutes.models.countries.CountryCode;
import com.newroutes.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/public/country"})
public class CountryController {

    private final CountryService service;

    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/code/{countryCode}")
    public ResponseEntity<Country> getByCode(@PathVariable("countryCode") CountryCode countryCode) {
        return ResponseEntity.ok(service.getByCode(countryCode));
    }

    @GetMapping("/name/{countryName}")
    public ResponseEntity<Country> getByName(@PathVariable("countryName") String countryName) {
        return ResponseEntity.ok(service.getByName(countryName));
    }
}
