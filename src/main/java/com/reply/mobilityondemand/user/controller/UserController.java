package com.reply.mobilityondemand.user.controller;


import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.repository.DemandRepository;
import com.reply.mobilityondemand.user.controller.exception.DeleteUserWithDemandsException;
import com.reply.mobilityondemand.user.controller.exception.UserIdMismatchException;
import com.reply.mobilityondemand.user.controller.exception.UserNotFoundException;
import com.reply.mobilityondemand.user.domain.User;
import com.reply.mobilityondemand.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DemandRepository demandRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable UUID userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent())
            return optionalUser.get();
        else {
            logger.info("No user found with userId: {}", userId);
            throw new UserNotFoundException(userId);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> createUser(@Valid @RequestBody User user,
                                         @Value("#{request.requestURL}") String url) {

        UUID userId = UUID.randomUUID();
        user.setUserId(userId);

        userRepository.save(user);
        logger.info("Created a new user with userId: {}", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url + "/" + userId));
        return new HttpEntity<>(headers);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> updateUser(@PathVariable UUID userId,
                                         @Valid @RequestBody User user,
                                         @Value("#{request.requestURL}") String url) {

        if (user.getUserId() != null && !user.getUserId().equals(userId)) {
            logger.info("UserId '{}' provided in the path does not match with body userId '{}'", userId, user.getUserId());
            throw new UserIdMismatchException(userId, user.getUserId());
        }

        user.setUserId(userId);
        userRepository.save(user);
        logger.info("Created a new user with userId: {}", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new HttpEntity<>(headers);
    }


    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable UUID userId) {

        List<Demand> demands = demandRepository.findByUserUserId(userId);
        if (!demands.isEmpty()) {
            throw new DeleteUserWithDemandsException(userId, demands.get(0).getDemandId());
        }

        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("No user found with userId: {}", userId);
            throw new UserNotFoundException(userId);
        }
    }

}
