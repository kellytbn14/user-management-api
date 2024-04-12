package com.example.usermanagementapi.utils;

import lombok.Getter;

@Getter
public enum MessageResponse {
    MISSING_REQUIRED_FIELD("MISSING_REQUIRED_FIELD", "Required field is missing")

    ;

    private final String message;
    private final String description;

    MessageResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
