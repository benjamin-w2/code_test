package com.reply.mobilityondemand.car.controller;

import com.reply.mobilityondemand.car.domain.InteriorDesign;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CarJson {

    private UUID carId;

    @NotNull
    private String model;

    @NotNull
    private String engine;

    private UUID infotainmentSystemId;

    @NotNull
    private InteriorDesign interiorDesign;

    @NotNull
    private Float currentLocation;

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public UUID getInfotainmentSystemId() {
        return infotainmentSystemId;
    }

    public void setInfotainmentSystemId(UUID infotainmentSystemId) {
        this.infotainmentSystemId = infotainmentSystemId;
    }

    public InteriorDesign getInteriorDesign() {
        return interiorDesign;
    }

    public void setInteriorDesign(InteriorDesign interiorDesign) {
        this.interiorDesign = interiorDesign;
    }

    public Float getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Float currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public String toString() {
        return "CarJson{" +
                "carId=" + carId +
                ", model='" + model + '\'' +
                ", engine='" + engine + '\'' +
                ", infotainmentSystemId=" + infotainmentSystemId +
                ", interiorDesign=" + interiorDesign +
                ", currentLocation=" + currentLocation +
                '}';
    }
}
