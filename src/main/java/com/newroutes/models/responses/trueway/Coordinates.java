package com.newroutes.models.responses.trueway;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    @JsonAlias({"lat"})
    private Double latitude;

    @JsonAlias({"lng"})
    private Double longitude;
}
