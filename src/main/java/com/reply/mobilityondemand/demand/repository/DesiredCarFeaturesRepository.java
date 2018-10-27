package com.reply.mobilityondemand.demand.repository;

import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DesiredCarFeaturesRepository extends CrudRepository<DesiredCarFeatures, UUID> {
}
