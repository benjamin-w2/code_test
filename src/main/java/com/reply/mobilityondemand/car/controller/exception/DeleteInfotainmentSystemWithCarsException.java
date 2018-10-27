package com.reply.mobilityondemand.car.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class DeleteInfotainmentSystemWithCarsException extends RuntimeException {
    public DeleteInfotainmentSystemWithCarsException(UUID infotainmentSystemId, UUID carId) {
        super("Impossible to delete infotainment system with id '" + infotainmentSystemId
                + "'. One or more cars, e. g. the car with id '" + carId
                + "' still has this infotainment system.");
    }
}
