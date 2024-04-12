package com.example.usermanagementapi.dtos;

import com.example.usermanagementapi.entities.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class PhoneDto {

    private UUID id;

    @NotNull
    private String number;

    @NotNull
    private String cityCode;

    @NotNull
    private String countryCode;

    @NotNull
    private String userId;

    private User user;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime lastUpdate;
}
