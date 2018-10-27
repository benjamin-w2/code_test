package com.reply.mobilityondemand.demand.controller.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DemandNotFoundException extends RuntimeException {
    public DemandNotFoundException(UUID demandId) {
        super("No demand found with demandId: " + demandId);
    }
}
