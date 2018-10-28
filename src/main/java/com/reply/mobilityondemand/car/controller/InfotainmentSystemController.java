package com.reply.mobilityondemand.car.controller;

import com.reply.mobilityondemand.car.controller.exception.DeleteInfotainmentSystemWithCarsException;
import com.reply.mobilityondemand.car.controller.exception.InfotainmentSystemNotFoundException;
import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import com.reply.mobilityondemand.car.repository.CarRepository;
import com.reply.mobilityondemand.car.repository.InfotainmentSystemRepository;
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
import java.util.UUID;

@RestController
@RequestMapping("infotainment-systems")
public class InfotainmentSystemController {

    private static final Logger logger = LoggerFactory.getLogger(InfotainmentSystemController.class);

    @Autowired
    private InfotainmentSystemRepository infotainmentSystemRepository;

    @Autowired
    private CarRepository carRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<InfotainmentSystem> getInfotainmentSystems() {
        return infotainmentSystemRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> createInfotainmentSystem(@Valid @RequestBody InfotainmentSystem infotainmentSystem,
                                                       @Value("#{request.requestURL}") String url) {

        UUID infotainmentSystemId = UUID.randomUUID();
        infotainmentSystem.setInfotainmentSystemId(infotainmentSystemId);

        infotainmentSystemRepository.save(infotainmentSystem);
        logger.info("Created a new infotainment system with infotainmentSystemId: {}", infotainmentSystemId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url + "/" + infotainmentSystemId));
        return new HttpEntity<>(headers);
    }

    @RequestMapping(value = "/{infotainmentSystemId}", method = RequestMethod.DELETE)
    public void deleteInfotainmentSystem(@PathVariable UUID infotainmentSystemId) {

        List<Car> cars = carRepository.findByInfotainmentSystemInfotainmentSystemId(infotainmentSystemId);
        if (!cars.isEmpty()) {
            throw new DeleteInfotainmentSystemWithCarsException(infotainmentSystemId, cars.get(0).getCarId());
        }

        try {
            infotainmentSystemRepository.deleteById(infotainmentSystemId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("No infotainment system found with infotainmentSystemId: {}", infotainmentSystemId);
            throw new InfotainmentSystemNotFoundException(infotainmentSystemId);
        }
    }

}
