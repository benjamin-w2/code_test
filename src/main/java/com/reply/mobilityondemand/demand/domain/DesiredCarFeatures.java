package com.reply.mobilityondemand.demand.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "DESIRED_CAR_FEATURES")
public class DesiredCarFeatures {

    @Id
    @Column(name = "DESIRED_CAR_FEATURES_ID")
    private UUID desiredCarFeaturesId;

    @Column(name = "HAS_AIR_CONDITION", nullable = false)
    private Boolean hasAirCondition;

    @Column(name = "HAS_SEAT_HEATING", nullable = false)
    private Boolean hasSeatHeating;

    @Column(name = "HAS_NAVIGATION_SYSTEM", nullable = false)
    private Boolean hasNavigationSystem;
}
