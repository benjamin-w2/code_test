package com.reply.mobilityondemand.demand.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "DESIRED_CAR_FEATURES")
public class DesiredCarFeatures {

    @Id
    @Column(name = "DESIRED_CAR_FEATURES_ID")
    private UUID desiredCarFeaturesId;

    @Column(name = "HAS_AIR_CONDITION")
    private Boolean hasAirCondition;

    @Column(name = "HAS_SEAT_HEATING")
    private Boolean hasSeatHeating;

    @Column(name = "HAS_NAVIGATION_SYSTEM")
    private Boolean hasNavigationSystem;

    public UUID getDesiredCarFeaturesId() {
        return desiredCarFeaturesId;
    }

    public void setDesiredCarFeaturesId(UUID desiredCarFeaturesId) {
        this.desiredCarFeaturesId = desiredCarFeaturesId;
    }

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

    public Boolean getHasNavigationSystem() {
        return hasNavigationSystem;
    }

    public void setHasNavigationSystem(Boolean hasNavigationSystem) {
        this.hasNavigationSystem = hasNavigationSystem;
    }

    @Override
    public String toString() {
        return "DesiredCarFeatures{" +
                "desiredCarFeaturesId=" + desiredCarFeaturesId +
                ", hasAirCondition=" + hasAirCondition +
                ", hasSeatHeating=" + hasSeatHeating +
                ", hasNavigationSystem=" + hasNavigationSystem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesiredCarFeatures that = (DesiredCarFeatures) o;
        return Objects.equals(desiredCarFeaturesId, that.desiredCarFeaturesId) &&
                Objects.equals(hasAirCondition, that.hasAirCondition) &&
                Objects.equals(hasSeatHeating, that.hasSeatHeating) &&
                Objects.equals(hasNavigationSystem, that.hasNavigationSystem);
    }

    @Override
    public int hashCode() {

        return Objects.hash(desiredCarFeaturesId, hasAirCondition, hasSeatHeating, hasNavigationSystem);
    }
}
