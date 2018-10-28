package com.reply.mobilityondemand.car.repository;

import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InfotainmentSystemRepository extends JpaRepository<InfotainmentSystem, UUID> {
}
