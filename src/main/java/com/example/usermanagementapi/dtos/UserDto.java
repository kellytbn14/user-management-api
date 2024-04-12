package com.example.usermanagementapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private Boolean isActive;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime lastUpdate;

    @NotNull
    private LocalDateTime lastLogin;
}
