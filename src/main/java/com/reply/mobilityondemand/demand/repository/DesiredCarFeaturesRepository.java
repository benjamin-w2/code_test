package com.reply.mobilityondemand.demand.repository;

import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DesiredCarFeaturesRepository extends JpaRepository<DesiredCarFeatures, UUID> {
}
