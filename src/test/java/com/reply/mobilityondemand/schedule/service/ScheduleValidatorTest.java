package com.reply.mobilityondemand.schedule.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import com.reply.mobilityondemand.car.domain.InteriorDesign;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ScheduleValidatorTest {

    private ScheduleValidator scheduleValidator = new ScheduleValidator();

    private static final LocalDateTime TIME_1 = LocalDateTime.of(2018, 10, 21, 14, 0);
    private static final LocalDateTime TIME_2 = TIME_1.plusSeconds(1);
    private static final LocalDateTime TIME_3 = TIME_2.plusMinutes(1);
    private static final LocalDateTime TIME_4 = TIME_3.plusDays(1);


    private static Stream<Arguments> timesOverlapping() {
        return Stream.of(
                Arguments.of(TIME_1, TIME_3, TIME_2, TIME_4, true),
                Arguments.of(TIME_1, TIME_2, TIME_3, TIME_4, false),
                Arguments.of(TIME_1, TIME_2, TIME_2, TIME_4, false),
                Arguments.of(TIME_1, TIME_2, TIME_1, TIME_2, true)
        );
    }

    @ParameterizedTest
    @MethodSource("timesOverlapping")
    public void timesOverlapping(LocalDateTime pickUpTime1, LocalDateTime dropOffTime1,
                                 LocalDateTime pickUpTime2, LocalDateTime dropOffTime2,
                                 boolean timesOverlapping) {
        Demand demand1 = new Demand();
        demand1.setEarliestPickUpTime(pickUpTime1);
        demand1.setLatestDropOffTime(dropOffTime1);

        Demand demand2 = new Demand();
        demand2.setEarliestPickUpTime(pickUpTime2);
        demand2.setLatestDropOffTime(dropOffTime2);

        assertThat(scheduleValidator.areBookingTimesOverlapping(demand1, demand2)).isEqualTo(timesOverlapping);
        assertThat(scheduleValidator.areBookingTimesOverlapping(demand2, demand1)).isEqualTo(timesOverlapping);
    }

    private static Stream<Arguments> desiredCarFeautures() {
        return Stream.of(
                Arguments.of(true, true, true,
                        true, true, true,
                        true),
                Arguments.of(false, false, false,
                        false, false, false,
                        true),
                Arguments.of(false, true, true,
                        true, true, true,
                        false),
                Arguments.of(true, true, true,
                        true, false, false,
                        false),
                Arguments.of(null, true, true,
                        false, true, true,
                        true),
                Arguments.of(null, null, null,
                        false, true, false,
                        true)
        );
    }

    @ParameterizedTest
    @MethodSource("desiredCarFeautures")
    public void carHasDesiredFeatures(Boolean airConditionDesired, Boolean seatHeatingDesired, Boolean navigationSystemDesired,
                                      boolean carHasAirCondition, boolean carHasSeatHeating, boolean carHasNavigationSystem,
                                      boolean carHasDesiredCarFeatures) {

        DesiredCarFeatures desiredCarFeatures = new DesiredCarFeatures();
        desiredCarFeatures.setHasAirCondition(airConditionDesired);
        desiredCarFeatures.setHasSeatHeating(seatHeatingDesired);
        desiredCarFeatures.setHasNavigationSystem(navigationSystemDesired);

        InteriorDesign interiorDesign = new InteriorDesign();
        interiorDesign.setHasAirCondition(carHasAirCondition);
        interiorDesign.setHasSeatHeating(carHasSeatHeating);

        InfotainmentSystem infotainmentSystem = new InfotainmentSystem();
        infotainmentSystem.setHasNavigationSystem(carHasNavigationSystem);

        Car car = new Car();
        car.setInfotainmentSystem(infotainmentSystem);
        car.setInteriorDesign(interiorDesign);

        assertThat(scheduleValidator.carHasDesiredFeatures(car, desiredCarFeatures)).isEqualTo(carHasDesiredCarFeatures);
    }

    @Test
    public void noDemandsScheduled() {

        Car car = new Car();

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_2);

        Map<Demand, Car> currentSchedule = new HashMap<>();

        assertThat(scheduleValidator.carIsAvailableAtThisTime(car, demand, currentSchedule)).isTrue();
    }

    @Test
    public void scheduleOverlapsWithCar() {

        Car car = new Car();

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_3);

        Demand scheduledDemand = new Demand();
        scheduledDemand.setEarliestPickUpTime(TIME_2);
        scheduledDemand.setLatestDropOffTime(TIME_3);

        Map<Demand, Car> currentSchedule = new HashMap<>();
        currentSchedule.put(scheduledDemand, car);

        assertThat(scheduleValidator.carIsAvailableAtThisTime(car, demand, currentSchedule)).isFalse();
    }

    @Test
    public void scheduleOverlapsDoesNotOverlapForDifferentCars() {

        Car car = new Car();

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_3);

        Demand scheduledDemand = new Demand();
        scheduledDemand.setEarliestPickUpTime(TIME_2);
        scheduledDemand.setLatestDropOffTime(TIME_3);

        Map<Demand, Car> currentSchedule = new HashMap<>();
        currentSchedule.put(scheduledDemand, new Car());

        assertThat(scheduleValidator.carIsAvailableAtThisTime(car, demand, currentSchedule)).isTrue();
    }

    @Test
    public void scheduleOverlapsMultipleCarsScheduled() {

        Car car = new Car();

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_3);

        Demand scheduledDemand = new Demand();
        scheduledDemand.setEarliestPickUpTime(TIME_2);
        scheduledDemand.setLatestDropOffTime(TIME_3);

        Map<Demand, Car> currentSchedule = new HashMap<>();
        currentSchedule.put(scheduledDemand, new Car());
        currentSchedule.put(scheduledDemand, car);

        assertThat(scheduleValidator.carIsAvailableAtThisTime(car, demand, currentSchedule)).isFalse();
    }

    @Test
    public void carIsAtRightLocationIfCurrentPositionFitsAndCarNotScheduled() {
        Car car = new Car();
        car.setCurrentLocation(1F);

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_3);
        demand.setPickUpLocation(1F);
        demand.setDropOffLocation(3F);

        Demand scheduledDemand = new Demand();
        scheduledDemand.setEarliestPickUpTime(TIME_2);
        scheduledDemand.setLatestDropOffTime(TIME_3);
        scheduledDemand.setPickUpLocation(1F);
        scheduledDemand.setPickUpLocation(2F);

        Map<Demand, Car> currentSchedule = new HashMap<>();
        currentSchedule.put(scheduledDemand, new Car());

        assertThat(scheduleValidator.carIsAtRightLocation(car, demand, currentSchedule)).isTrue();
    }

    @Test
    public void carIsAtWrongLocationIfCurrentPositionDoesntFitAndCarNotScheduled() {
        Car car = new Car();
        car.setCurrentLocation(2F);

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_3);
        demand.setPickUpLocation(1F);
        demand.setDropOffLocation(3F);

        Map<Demand, Car> currentSchedule = new HashMap<>();

        assertThat(scheduleValidator.carIsAtRightLocation(car, demand, currentSchedule)).isFalse();
    }

    private static Stream<Arguments> oneScheduledDemand() {
        return Stream.of(
                Arguments.of(1F, TIME_1, TIME_2, 1F, 2F, false),
                Arguments.of(1F, TIME_3, TIME_4, 1F, 2F, false),
                Arguments.of(0F, TIME_1, TIME_2, 0F, 1F, true),
                Arguments.of(1F, TIME_3, TIME_4, 3F, 1F, true)
        );
    }

    @ParameterizedTest
    @MethodSource("oneScheduledDemand")
    public void validateCarLocationOneScheduledDemand(Float currentLocation,
                                                      LocalDateTime scheduledDemandPickUpTime, LocalDateTime scheduledDemandDropOffTime,
                                                      Float scheduledDemandPickUpLocation, Float scheduledDemandDropOffLocation,
                                                      boolean isAtRightLocation) {
        Car car = new Car();
        car.setCurrentLocation(currentLocation);

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_2);
        demand.setLatestDropOffTime(TIME_3);
        demand.setPickUpLocation(1F);
        demand.setDropOffLocation(3F);

        Map<Demand, Car> currentSchedule = new HashMap<>();
        Demand scheduledDemand = new Demand();
        scheduledDemand.setEarliestPickUpTime(scheduledDemandPickUpTime);
        scheduledDemand.setLatestDropOffTime(scheduledDemandDropOffTime);
        scheduledDemand.setPickUpLocation(scheduledDemandPickUpLocation);
        scheduledDemand.setDropOffLocation(scheduledDemandDropOffLocation);
        currentSchedule.put(scheduledDemand, car);

        assertThat(scheduleValidator.carIsAtRightLocation(car, demand, currentSchedule)).isEqualTo(isAtRightLocation);
    }

    @Test
    public void validateCarLocationTwoScheduledDemands() {
        Car car = new Car();
        car.setCurrentLocation(1F);

        Demand demand = new Demand();
        demand.setEarliestPickUpTime(TIME_3);
        demand.setLatestDropOffTime(TIME_4);
        demand.setPickUpLocation(1F);
        demand.setDropOffLocation(3F);

        Map<Demand, Car> currentSchedule = new HashMap<>();
        Demand scheduledDemand1 = new Demand();
        scheduledDemand1.setEarliestPickUpTime(TIME_1);
        scheduledDemand1.setLatestDropOffTime(TIME_2);
        scheduledDemand1.setPickUpLocation(1F);
        scheduledDemand1.setDropOffLocation(23F);

        Demand scheduledDemand2 = new Demand();
        scheduledDemand2.setEarliestPickUpTime(TIME_2);
        scheduledDemand2.setLatestDropOffTime(TIME_3);
        scheduledDemand2.setPickUpLocation(23F);
        scheduledDemand2.setDropOffLocation(1F);

        currentSchedule.put(scheduledDemand1, car);
        currentSchedule.put(scheduledDemand2, car);

        assertThat(scheduleValidator.carIsAtRightLocation(car, demand, currentSchedule)).isTrue();
    }
}
