package com.reply.mobilityondemand.car.repository;

import com.reply.mobilityondemand.car.domain.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {

    List<Car> findByInfotainmentSystemInfotainmentSystemId(UUID infotainmentSystemId);
}
