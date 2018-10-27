package com.reply.mobilityondemand.car.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarIdMismatchException extends RuntimeException {
    public CarIdMismatchException(UUID carIdPath, UUID carIdBody) {
        super("CarId '" + carIdPath + "' provided in the path does not match with body carId '" + carIdBody + "'");
    }
}
