package com.reply.mobilityondemand.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDemandsOverlapException extends RuntimeException {
    public UserDemandsOverlapException(String message) {
        super(message);
    }
}
