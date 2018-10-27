package com.reply.mobilityondemand.car.converter;

import com.reply.mobilityondemand.car.controller.CarJson;
import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import com.reply.mobilityondemand.car.repository.InfotainmentSystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CarJsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(CarJsonConverter.class);

    @Autowired
    private InfotainmentSystemRepository infotainmentSystemRepository;

    public Car toCar(CarJson carJson) {

        Car car = new Car();

        car.setCarId(carJson.getCarId());
        car.setCurrentLocation(carJson.getCurrentLocation());
        car.setEngine(carJson.getEngine());
        car.setModel(carJson.getModel());
        car.setInteriorDesign(carJson.getInteriorDesign());

        if (carJson.getInfotainmentSystemId() != null) {
            car.setInfotainmentSystem(getInfotainmentSystem(carJson.getInfotainmentSystemId()));
        }

        return car;
    }

    private InfotainmentSystem getInfotainmentSystem(UUID infotainmentSystemId) {

        Optional<InfotainmentSystem> optionalInfotainmentSystem = infotainmentSystemRepository.findById(infotainmentSystemId);

        if (optionalInfotainmentSystem.isPresent()) {
            return optionalInfotainmentSystem.get();
        } else {
            logger.info("No infotainment system found with id: {}", infotainmentSystemId);
            throw new CarJsonConverterException("No infotainment system found with id: " + infotainmentSystemId);
        }
    }


    public CarJson toCarJson(Car car) {

        CarJson carJson = new CarJson();
        carJson.setCarId(car.getCarId());
        carJson.setCurrentLocation(car.getCurrentLocation());
        carJson.setEngine(car.getEngine());
        carJson.setModel(car.getModel());
        carJson.setInteriorDesign(car.getInteriorDesign());

        InfotainmentSystem infotainmentSystem = car.getInfotainmentSystem();

        if (infotainmentSystem != null) {
            carJson.setInfotainmentSystemId(infotainmentSystem.getInfotainmentSystemId());
        }

        return carJson;
    }
}
