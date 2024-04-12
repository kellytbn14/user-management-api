package com.example.usermanagementapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ChangeActiveStatusUserRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private Boolean active;
}
