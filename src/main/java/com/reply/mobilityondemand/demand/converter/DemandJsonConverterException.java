package com.reply.mobilityondemand.demand.converter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DemandJsonConverterException extends RuntimeException {
    public DemandJsonConverterException(String message) {
        super(message);
    }
}
