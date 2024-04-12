package com.example.usermanagementapi.handlers.exceptions;


import com.example.usermanagementapi.utils.MessageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private String description;

    public BusinessException(String exception) {
        super(exception);
    }

    public BusinessException(String message, Exception exception) {
        super(message, exception);
    }

    public BusinessException(MessageResponse message) {
        super(message.getMessage());
        this.description = message.getDescription();
    }
}
