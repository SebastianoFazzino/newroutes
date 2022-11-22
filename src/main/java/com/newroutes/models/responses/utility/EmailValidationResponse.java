package com.newroutes.models.responses.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailValidationResponse {

    public String email;

    public String autocorrect;

    public Deliverability deliverability;

    @JsonProperty("quality_score")
    public Double qualityScore;

    @JsonProperty("is_valid_format")
    public ValidationProperty validFormat;

    @JsonProperty("is_free_email")
    public ValidationProperty freeEmail;

    @JsonProperty("is_disposable_email")
    public ValidationProperty disposableEmail;

    @JsonProperty("is_role_email")
    public ValidationProperty roleEmail;

    @JsonProperty("is_catchall_email")
    public ValidationProperty catchallEmail;

    @JsonProperty("is_mx_found")
    public ValidationProperty mxFound;

    @JsonProperty("is_smtp_valid")
    public ValidationProperty smtpValid;

}
