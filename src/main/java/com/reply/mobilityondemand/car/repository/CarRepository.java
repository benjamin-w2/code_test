package com.reply.mobilityondemand.car.repository;

import com.reply.mobilityondemand.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    List<Car> findByInfotainmentSystemInfotainmentSystemId(UUID infotainmentSystemId);
}
