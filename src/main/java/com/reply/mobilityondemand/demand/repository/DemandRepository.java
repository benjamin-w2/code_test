package com.reply.mobilityondemand.demand.repository;

import com.reply.mobilityondemand.demand.domain.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DemandRepository extends JpaRepository<Demand, UUID> {

    List<Demand> findByUserUserId(UUID userId);
}
