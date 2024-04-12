package com.example.usermanagementapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class FilterUserRequest {

    private String name;
    private String email;
    private Boolean isActive;
}
