package com.reply.mobilityondemand.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIdMismatchException extends RuntimeException {
    public UserIdMismatchException(UUID userIdPath, UUID userIdBody) {
        super("UserId '" + userIdPath + "' provided in the path does not match with body userId '" + userIdBody + "'");
    }
}
