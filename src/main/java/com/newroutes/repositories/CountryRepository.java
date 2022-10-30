package com.newroutes.repositories;

import com.newroutes.models.countries.Country;
import com.newroutes.models.countries.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    Country getByCountryCode(CountryCode countryCode);

    Country getByCountryName(String CountryName);
}
