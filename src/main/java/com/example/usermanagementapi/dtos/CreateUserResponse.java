package com.example.usermanagementapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateUserResponse {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String token;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
    private LocalDateTime lastLogin;
}
