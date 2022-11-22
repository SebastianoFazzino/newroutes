package com.newroutes.models.responses.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationProperty {

    private Boolean value;

    private String text;
}
