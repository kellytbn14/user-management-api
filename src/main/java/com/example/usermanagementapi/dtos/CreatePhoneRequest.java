package com.example.usermanagementapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreatePhoneRequest {

    @NotNull
    private String number;

    @NotNull
    private String cityCode;

    @NotNull
    private String countryCode;
}
