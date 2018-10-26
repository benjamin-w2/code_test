package com.reply.mobilityondemand.car.repository;

import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InfotainmentSystemRepository extends CrudRepository<InfotainmentSystem, UUID> {
}
