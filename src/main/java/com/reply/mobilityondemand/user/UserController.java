package com.reply.mobilityondemand.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getUsers() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String getUser(@PathVariable UUID userId) {
        return "My User " + userId;
    }
}
