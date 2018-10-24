package com.reply.mobilityondemand.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUsers() {
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
    public HttpEntity<String> createAccount(
            @RequestBody User newUser,
            @Value("#{request.requestURL}") StringBuffer url) {

        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setUserId(userId);
        user.setName(newUser.getName());
        user.setAge(newUser.getAge());
        user.setGender(newUser.getGender());

        logger.debug("Created a new user with userId: {}", userId);

        userRepository.save(user);
        return entityWithLocation(url, user.getUserId());
    }

    private HttpEntity<String> entityWithLocation(StringBuffer url, Object resourceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(getLocationForChildResource(url, resourceId));
        return new HttpEntity<>(headers);
    }

    private URI getLocationForChildResource(StringBuffer url, Object childIdentifier) {
        UriTemplate template = new UriTemplate(url.append("/{childId}").toString());
        return template.expand(childIdentifier);
    }
}
