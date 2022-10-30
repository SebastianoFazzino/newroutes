package com.newroutes.services;

import com.newroutes.models.countries.Country;
import com.newroutes.models.countries.CountryCode;
import com.newroutes.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository repository;

    public List<Country> getAll() {
        log.info("Requested get all Countries");
        return repository.findAll();
    }

    public Country getByCode(CountryCode countryCode) {
        log.info("Requested get Country by code '{}'", countryCode);
        return repository.getByCountryCode(countryCode);
    }

    public Country getByName(String countryName) {
        log.info("Requested get Country by name '{}'", countryName);
        return repository.getByCountryName(countryName);
    }
}
