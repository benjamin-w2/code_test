package com.reply.mobilityondemand.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.domain.InfotainmentSystem;
import com.reply.mobilityondemand.car.domain.InteriorDesign;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import com.reply.mobilityondemand.schedule.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScheduleServiceTest {

    private static final LocalDateTime TIME_1 = LocalDateTime.of(2018, 10, 21, 14, 0);
    private static final LocalDateTime TIME_2 = TIME_1.plusSeconds(1);
    public static final UUID CAR_ID = UUID.randomUUID();
    public static final UUID DEMAND_ID = UUID.randomUUID();

    @Mock
    private ScheduleValidator scheduleValidator;

    @InjectMocks
    private ScheduleService scheduleService = new ScheduleService();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findSchedule() {

        List<Demand> demands = getOneDemand();
        List<Car> cars = getOneCarWithAllFeatures();
        Demand demand = demands.get(0);
        Car car = cars.get(0);

        when(scheduleValidator.isValidAssigment(eq(car), eq(demand), any())).thenReturn(true);

        Schedule schedule = scheduleService.findSchedule(demands, cars);

        Map<Demand, Car> scheduleAsMap = schedule.getScheduleAsMap();
        assertThat(scheduleAsMap.get(demand)).isEqualTo(car);
    }

    private List<Car> getOneCarWithAllFeatures() {
        Car car = new Car();
        car.setCarId(CAR_ID);
        car.setCurrentLocation(1F);

        InfotainmentSystem infotainmentSystem = new InfotainmentSystem();
        infotainmentSystem.setHasNavigationSystem(true);
        car.setInfotainmentSystem(infotainmentSystem);

        InteriorDesign interiorDesign = new InteriorDesign();
        interiorDesign.setHasSeatHeating(true);
        interiorDesign.setHasAirCondition(true);
        car.setInteriorDesign(interiorDesign);

        List<Car> cars = new ArrayList<>();
        cars.add(car);

        return cars;
    }

    private List<Demand> getOneDemand() {
        Demand demand = new Demand();
        demand.setDemandId(DEMAND_ID);
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_2);
        demand.setPickUpLocation(1F);
        demand.setDropOffLocation(3F);
        demand.setDesiredCarFeatures(new DesiredCarFeatures());

        List<Demand> demands = new ArrayList<>();
        demands.add(demand);

        return demands;
    }

    @Test
    public void cantFindScheduleIfNoCarsExist() {

        when(scheduleValidator.isValidAssigment(any(), any(), any())).thenReturn(true);

        Schedule schedule = scheduleService.findSchedule(getOneDemand(), new ArrayList<>());

        assertThat(schedule.isEmpty()).isTrue();
    }

    @Test
    public void cantFindScheduleIfDemandsCanNotBeSatisfiedByTheCars() {

        List<Demand> demands = getOneDemand();
        List<Car> cars = getOneCarWithAllFeatures();

        when(scheduleValidator.isValidAssigment(any(), any(), any())).thenReturn(false);

        Schedule schedule = scheduleService.findSchedule(demands, cars);

        assertThat(schedule.isEmpty()).isTrue();
    }
}
