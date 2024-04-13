package com.example.usermanagementapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserAuthRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
