package com.reply.mobilityondemand.demand.domain;

import com.reply.mobilityondemand.user.domain.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "DEMAND")
public class Demand {

    @Id
    @Column(name = "DEMAND_ID")
    private UUID demandId;

    @Column(name = "PICK_UP_LOCATION", nullable = false)
    private Float pickUpLocation;

    @Column(name = "DROP_OFF_LOCATION", nullable = false)
    private Float dropOffLocation;

    @Column(name = "EARLIEST_PICK_UP_TIME", nullable = false)
    private LocalDateTime earliestPickUpTime;

    @Column(name = "LATEST_DROP_OFF_TIME", nullable = false)
    private LocalDateTime latestDropOffTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DESIRED_CAR_FEATURES_ID", nullable = false)
    private DesiredCarFeatures desiredCarFeatures;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "demandId=" + demandId +
                ", pickUpLocation=" + pickUpLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", earliestPickUpTime=" + earliestPickUpTime +
                ", latestDropOffTime=" + latestDropOffTime +
                ", desiredCarFeatures=" + desiredCarFeatures +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demand demand = (Demand) o;
        return Objects.equals(demandId, demand.demandId) &&
                Objects.equals(pickUpLocation, demand.pickUpLocation) &&
                Objects.equals(dropOffLocation, demand.dropOffLocation) &&
                Objects.equals(earliestPickUpTime, demand.earliestPickUpTime) &&
                Objects.equals(latestDropOffTime, demand.latestDropOffTime) &&
                Objects.equals(desiredCarFeatures, demand.desiredCarFeatures) &&
                Objects.equals(user, demand.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demandId, pickUpLocation, dropOffLocation, earliestPickUpTime, latestDropOffTime, desiredCarFeatures, user);
    }
}
