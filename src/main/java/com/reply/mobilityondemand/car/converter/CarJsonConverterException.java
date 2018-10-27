package com.reply.mobilityondemand.car.converter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarJsonConverterException extends RuntimeException {
    public CarJsonConverterException(String message) {
        super(message);
    }
}
