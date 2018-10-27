package com.reply.mobilityondemand.car.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InfotainmentSystemNotFoundException extends RuntimeException {
    public InfotainmentSystemNotFoundException(UUID infotainmentSystemId) {
        super("No infotainment system found with infotainmentSystemId: " + infotainmentSystemId);
    }
}
