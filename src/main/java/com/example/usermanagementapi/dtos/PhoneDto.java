package com.example.usermanagementapi.dtos;

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

    private UUID userId;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
}
