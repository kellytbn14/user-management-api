package com.example.usermanagementapi.handlers.exceptions;


import com.example.usermanagementapi.utils.MessageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequiredException extends RuntimeException {

    private String description;

    public RequiredException(String exception) {
        super(exception);
    }

    public RequiredException(String message, Exception exception) {
        super(message, exception);
    }

    public RequiredException(MessageResponse message) {
        super(message.getMessage());
        this.description = message.getDescription();
    }
}
