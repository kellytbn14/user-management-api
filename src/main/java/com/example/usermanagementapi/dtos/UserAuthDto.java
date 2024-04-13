package com.example.usermanagementapi.dtos;

import lombok.Data;

@Data
public class UserAuthDto {
    private String token;
    private String tokenType;
}
