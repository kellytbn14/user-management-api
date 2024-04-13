package com.example.usermanagementapi.utils;

import lombok.Getter;

@Getter
public enum MessageResponse {
    MISSING_REQUIRED_FIELD("MISSING_REQUIRED_FIELD", "Required field is missing"),
    INVALID_EMAIL("INVALID_EMAIL", "Email is invalid"),
    INVALID_PASS("INVALID_PASS", "The password does not meet the minimum requirements."),


    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "The email is already registered"),
    USER_NOT_FOUND_EXCEPTION("USER_NOT_FOUND_EXCEPTION", "User could not be found"),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", "The user is not active."),
    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", "Authentication error");

    private final String message;
    private final String description;

    MessageResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
