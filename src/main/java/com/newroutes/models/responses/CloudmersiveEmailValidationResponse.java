package com.newroutes.models.responses;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudmersiveEmailValidationResponse {

    @JsonAlias({"IsValid"})
    private boolean valid;

    @JsonAlias({"Domain"})
    private String domain;

    @JsonAlias({"IsFreeEmailProvider"})
    private boolean freeProvider;

    @JsonAlias({"IsDisposable"})
    private boolean disposable;

}
