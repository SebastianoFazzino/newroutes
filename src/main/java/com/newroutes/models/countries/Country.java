package com.newroutes.models.countries;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.newroutes.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
public class Country extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CountryCode countryCode;

    @Column(name = "alpha_3_code")
    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Alpha3CountryCode alpha3Code;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String countryName;

    @Enumerated(EnumType.STRING)
    private CountryGroup countryGroup;

    private String prefix;

}
