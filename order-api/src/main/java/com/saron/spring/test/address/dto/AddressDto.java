package com.saron.spring.test.address.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressDto {

    @NotNull
    private String city;
    @NotNull
    private String county;
    @NotNull
    private String country;
    private String street;
    private short streetNumber;
    private String extraDetails;

}
