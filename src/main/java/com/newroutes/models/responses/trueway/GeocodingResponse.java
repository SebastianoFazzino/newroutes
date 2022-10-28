package com.newroutes.models.responses.trueway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeocodingResponse {

    private List<Address> results;

}
