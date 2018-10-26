package com.reply.mobilityondemand.demand;

import com.reply.mobilityondemand.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "DEMAND")
public class Demand {

    @Id
    @Column(name = "DEMAND_ID")
    private UUID demandId;

    @Column(name = "PICK_UP_LOCATION")
    private Float pickUpLocation;

    @Column(name = "DROP_OFF_LOCATION")
    private Float dropOffLocation;

    @Column(name = "EARLIEST_PICK_UP_TIME")
    private LocalDateTime earliestPickUpTime;

    @Column(name = "LATEST_DROP_OFF_TIME")
    private LocalDateTime latestDropOffTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DESIRED_CAR_FEATURES_ID")
    private DesiredCarFeatures desiredCarFeatures;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
