package com.reply.mobilityondemand.car.controller;

import com.reply.mobilityondemand.car.controller.exception.CarIdMismatchException;
import com.reply.mobilityondemand.car.controller.exception.CarNotFoundException;
import com.reply.mobilityondemand.car.converter.CarJsonConverter;
import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.repository.CarRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("cars")
public class CarController {
    
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarJsonConverter carJsonConverter;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<CarJson> getCars() {

        List<CarJson> carJsonList = new ArrayList<>();

        for (Car car : carRepository.findAll()) {
            carJsonList.add(carJsonConverter.toCarJson(car));
        }

        return carJsonList;
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.GET)
    public CarJson getCar(@PathVariable UUID carId) {

        Optional<Car> optionalCar = carRepository.findById(carId);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            return carJsonConverter.toCarJson(car);
        } else {
            logger.info("No car found with carId: {}", carId);
            throw new CarNotFoundException(carId);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> createCar(@Valid @RequestBody CarJson carJson,
                                        @Value("#{request.requestURL}") String url) {

        logger.info(carJson.toString());

        Car car = carJsonConverter.toCar(carJson);

        UUID carId = UUID.randomUUID();
        car.setCarId(carId);

        carRepository.save(car);
        logger.info("Created a new car with carId: {}", carId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url + "/" + carId));
        return new HttpEntity<>(headers);
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> updateCar(@PathVariable UUID carId,
                                        @Valid @RequestBody CarJson carJson,
                                        @Value("#{request.requestURL}") String url) {

        if (carJson.getCarId() != null && !carJson.getCarId().equals(carId)) {
            logger.info("CarId '{}' provided in the path does not match with body carId '{}'", carId, carJson.getCarId());
            throw new CarIdMismatchException(carId, carJson.getCarId());
        }

        Car car = carJsonConverter.toCar(carJson);
        car.setCarId(carId);

        carRepository.save(car);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new HttpEntity<>(headers);
    }


    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    public void deleteCar(@PathVariable UUID carId) {
        try {
            carRepository.deleteById(carId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("No car found with carId: {}", carId);
            throw new CarNotFoundException(carId);
        }
    }

}
