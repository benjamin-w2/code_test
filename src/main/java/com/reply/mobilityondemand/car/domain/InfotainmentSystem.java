package com.reply.mobilityondemand.car.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "INFOTAINMENT_SYSTEM")
public class InfotainmentSystem {

    @Id
    @Column(name = "INFOTAINMENT_SYSTEM_ID")
    private UUID infotainmentSystemId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "HAS_NAVIGATION_SYSTEM", nullable = false)
    private Boolean hasNavigationSystem;
}
