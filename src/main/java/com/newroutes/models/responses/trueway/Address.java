package com.newroutes.models.responses.trueway;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String address;

    @JsonAlias({"postal_code"})
    private String zipCode;

    private String country;

    private String region;

    private String locality;

    @JsonAlias({"street"})
    private String streetName;

    @JsonAlias({"house"})
    private String houseNumber;

    @JsonAlias({"location"})
    private Coordinates coordinates;

    @JsonAlias({"location_type"})
    private String locationType;

    private String type;

}
