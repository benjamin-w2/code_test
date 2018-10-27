package com.reply.mobilityondemand.car.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class InteriorDesign {

    @NotNull
    @Column(name = "HAS_AIR_CONDITION", nullable = false)
    private Boolean hasAirCondition;

    @NotNull
    @Column(name = "HAS_SEAT_HEATING", nullable = false)
    private Boolean hasSeatHeating;

    public Boolean getHasAirCondition() {
        return hasAirCondition;
    }

    public void setHasAirCondition(Boolean hasAirCondition) {
        this.hasAirCondition = hasAirCondition;
    }

    public Boolean getHasSeatHeating() {
        return hasSeatHeating;
    }

    public void setHasSeatHeating(Boolean hasSeatHeating) {
        this.hasSeatHeating = hasSeatHeating;
    }

    @Override
    public String toString() {
        return "InteriorDesign{" +
                "hasAirCondition=" + hasAirCondition +
                ", hasSeatHeating=" + hasSeatHeating +
                '}';
    }
}
