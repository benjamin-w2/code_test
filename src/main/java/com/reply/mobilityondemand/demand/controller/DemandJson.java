package com.reply.mobilityondemand.demand.controller;

import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class DemandJson {

    private UUID demandId;

    @NotNull
    private Float pickUpLocation;

    @NotNull
    private Float dropOffLocation;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime earliestPickUpTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime latestDropOffTime;

    @Valid
    @NotNull
    private DesiredCarFeatures desiredCarFeatures;

    @NotNull
    private UUID userId;

    public UUID getDemandId() {
        return demandId;
    }

    public void setDemandId(UUID demandId) {
        this.demandId = demandId;
    }

    public Float getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Float pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Float getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(Float dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public LocalDateTime getEarliestPickUpTime() {
        return earliestPickUpTime;
    }

    public void setEarliestPickUpTime(LocalDateTime earliestPickUpTime) {
        this.earliestPickUpTime = earliestPickUpTime;
    }

    public LocalDateTime getLatestDropOffTime() {
        return latestDropOffTime;
    }

    public void setLatestDropOffTime(LocalDateTime latestDropOffTime) {
        this.latestDropOffTime = latestDropOffTime;
    }

    public DesiredCarFeatures getDesiredCarFeatures() {
        return desiredCarFeatures;
    }

    public void setDesiredCarFeatures(DesiredCarFeatures desiredCarFeatures) {
        this.desiredCarFeatures = desiredCarFeatures;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DemandJson{" +
                "demandId=" + demandId +
                ", pickUpLocation=" + pickUpLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", earliestPickUpTime=" + earliestPickUpTime +
                ", latestDropOffTime=" + latestDropOffTime +
                ", desiredCarFeatures=" + desiredCarFeatures +
                ", userId=" + userId +
                '}';
    }
}
