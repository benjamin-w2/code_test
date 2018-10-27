package com.reply.mobilityondemand.demand.repository;

import com.reply.mobilityondemand.demand.domain.Demand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface DemandRepository extends CrudRepository<Demand, UUID> {

    public List<Demand> findByUserUserId(UUID userId);
}
