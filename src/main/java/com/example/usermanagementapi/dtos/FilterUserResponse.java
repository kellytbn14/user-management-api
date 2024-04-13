package com.example.usermanagementapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class FilterUserResponse {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
    private LocalDateTime lastLogin;

    public FilterUserResponse() {

    }
}
