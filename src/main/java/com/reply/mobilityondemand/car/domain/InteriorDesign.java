package com.reply.mobilityondemand.car.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InteriorDesign {

    @Column(name = "HAS_AIR_CONDITION", nullable = false)
    private Boolean hasAirCondition;

    @Column(name = "HAS_SEAT_HEATING", nullable = false)
    private Boolean hasSeatHeating;
}
