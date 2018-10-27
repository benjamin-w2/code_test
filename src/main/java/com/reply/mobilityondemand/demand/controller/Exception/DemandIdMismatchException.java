package com.reply.mobilityondemand.demand.controller.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DemandIdMismatchException extends RuntimeException {
    public DemandIdMismatchException(UUID demandIdPath, UUID demandIdBody) {
        super("DemandId '" + demandIdPath + "' provided in the path does not match with body demandId '" + demandIdBody + "'");
    }
}
