package com.reply.mobilityondemand.car.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "CAR")
public class Car {

    @Id
    @Column(name = "CAR_ID")
    private UUID carId;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "ENGINE", nullable = false)
    private String engine;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INFOTAINMENT_SYSTEM_ID")
    private InfotainmentSystem infotainmentSystem;

    @Embedded
    private InteriorDesign interiorDesign;

    @Column(name = "CURRENT_LOCATION", nullable = false)
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

    public InfotainmentSystem getInfotainmentSystem() {
        return infotainmentSystem;
    }

    public void setInfotainmentSystem(InfotainmentSystem infotainmentSystem) {
        this.infotainmentSystem = infotainmentSystem;
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
        return "Car{" +
                "carId=" + carId +
                ", model='" + model + '\'' +
                ", engine='" + engine + '\'' +
                ", infotainmentSystem=" + infotainmentSystem +
                ", interiorDesign=" + interiorDesign +
                ", currentLocation=" + currentLocation +
                '}';
    }
}
