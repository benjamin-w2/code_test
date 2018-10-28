package com.reply.mobilityondemand.schedule.service;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import com.reply.mobilityondemand.car.domain.InteriorDesign;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import com.reply.mobilityondemand.schedule.exception.UserDemandsOverlapException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ScheduleValidator {

    public void throwIfUserWithDemandTimeOverlapExist(List<Demand> demands) {

        for (int i = 0; i < demands.size(); i++) {
            for (int j = i + 1; j < demands.size(); j++) {

                Demand demand1 = demands.get(i);
                Demand demand2 = demands.get(j);

                if (demand1.getUser().equals(demand2.getUser()) &&
                        areBookingTimesOverlapping(demand1, demand2)) {

                    throw new UserDemandsOverlapException("Times of demand '" + demand1.getDemandId() + "' and demand '" +
                            demand2.getDemandId() + "' of user '" + demand1.getUser().getUserId() + "' are overlapping");
                }
            }
        }
    }

    public boolean areBookingTimesOverlapping(Demand demand1, Demand demand2) {
        return (!demand2.getEarliestPickUpTime().isAfter(demand1.getEarliestPickUpTime()) &&
                demand1.getEarliestPickUpTime().isBefore(demand2.getLatestDropOffTime()))
                ||
                (!demand1.getEarliestPickUpTime().isAfter(demand2.getEarliestPickUpTime()) &&
                        demand2.getEarliestPickUpTime().isBefore(demand1.getLatestDropOffTime()));
    }

    public boolean isValidAssigment(Car car, Demand demand, Map<Demand, Car> currentSchedule) {

        return carHasDesiredFeatures(car, demand.getDesiredCarFeatures()) &&
                carIsAvailableAtThisTime(car, demand, currentSchedule) &&
                carIsAtRightLocation(car, demand, currentSchedule);
    }

    public boolean carIsAvailableAtThisTime(Car car, Demand demand, Map<Demand, Car> currentSchedule) {

        for (Demand scheduledDemand : currentSchedule.keySet()) {
            if (car.equals(currentSchedule.get(scheduledDemand)) && areBookingTimesOverlapping(demand, scheduledDemand)) {
                return false;
            }
        }

        return true;
    }

    public boolean carIsAtRightLocation(Car car, Demand demand, Map<Demand, Car> currentSchedule) {

        Float carLocationAtPickupTime = getCarLocationAtDateTime(car, demand.getEarliestPickUpTime(), currentSchedule);
        Optional<Float> carLocationAfterDropOffTime = getCarLocationAfterDateTime(car, demand.getLatestDropOffTime(), currentSchedule);

        return carLocationAtPickupTime.equals(demand.getPickUpLocation()) &&
                (carLocationAfterDropOffTime.isEmpty() || carLocationAfterDropOffTime.get().equals(demand.getDropOffLocation()));
    }

    private Optional<Float> getCarLocationAfterDateTime(Car car, LocalDateTime dateTime, Map<Demand, Car> currentSchedule) {

        Optional<Float> carLocationAfterDateTime = Optional.empty();
        LocalDateTime firstPickUpTimeAfterDateTime = null;

        for (Demand scheduledDemand : currentSchedule.keySet()) {
            if (car.equals(currentSchedule.get(scheduledDemand)) &&
                    !scheduledDemand.getEarliestPickUpTime().isBefore(dateTime) &&
                    (firstPickUpTimeAfterDateTime == null || firstPickUpTimeAfterDateTime.isAfter(scheduledDemand.getEarliestPickUpTime()))) {

                firstPickUpTimeAfterDateTime = scheduledDemand.getLatestDropOffTime();
                carLocationAfterDateTime = Optional.of(scheduledDemand.getPickUpLocation());
            }
        }

        return carLocationAfterDateTime;
    }

    private Float getCarLocationAtDateTime(Car car, LocalDateTime dateTime, Map<Demand, Car> currentSchedule) {

        Float carLocationAtDateTime = car.getCurrentLocation();
        LocalDateTime lastDropOffTimeBeforeDateTime = null;

        for (Demand scheduledDemand : currentSchedule.keySet()) {
            if (car.equals(currentSchedule.get(scheduledDemand)) &&
                    !scheduledDemand.getLatestDropOffTime().isAfter(dateTime) &&
                    (lastDropOffTimeBeforeDateTime == null || lastDropOffTimeBeforeDateTime.isBefore(scheduledDemand.getLatestDropOffTime()))) {

                lastDropOffTimeBeforeDateTime = scheduledDemand.getLatestDropOffTime();
                carLocationAtDateTime = scheduledDemand.getDropOffLocation();
            }
        }
        return carLocationAtDateTime;
    }

    public boolean carHasDesiredFeatures(Car car, DesiredCarFeatures desiredCarFeatures) {

        InteriorDesign interiorDesign = car.getInteriorDesign();
        InfotainmentSystem infotainmentSystem = car.getInfotainmentSystem();

        Boolean airConditionDesired = desiredCarFeatures.getHasAirCondition();
        Boolean seatHeatingDesired = desiredCarFeatures.getHasSeatHeating();
        Boolean navigationSystemDesired = desiredCarFeatures.getHasNavigationSystem();

        return (airConditionDesired == null || airConditionDesired.equals(interiorDesign.getHasAirCondition())) &&
                (seatHeatingDesired == null || seatHeatingDesired.equals(interiorDesign.getHasSeatHeating())) &&
                (navigationSystemDesired == null || navigationSystemDesired.equals(infotainmentSystem.getHasNavigationSystem()));
    }
}
