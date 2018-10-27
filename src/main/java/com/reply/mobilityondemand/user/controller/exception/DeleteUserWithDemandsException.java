package com.reply.mobilityondemand.user.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class DeleteUserWithDemandsException extends RuntimeException {
    public DeleteUserWithDemandsException(UUID userId, UUID demandId) {
        super("Impossible to delete user with id '" + userId
                + "'. One or more demands, e. g. the demand with id '" + demandId
                + "' still belongs to this user.");
    }
}
