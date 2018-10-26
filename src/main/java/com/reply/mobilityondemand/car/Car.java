package com.reply.mobilityondemand.car;

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
}
