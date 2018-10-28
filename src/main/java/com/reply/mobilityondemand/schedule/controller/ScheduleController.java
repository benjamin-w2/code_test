package com.reply.mobilityondemand.schedule.controller;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.repository.CarRepository;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.repository.DemandRepository;
import com.reply.mobilityondemand.schedule.Schedule;
import com.reply.mobilityondemand.schedule.converter.ScheduleJsonConverter;
import com.reply.mobilityondemand.schedule.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("schedule")
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private ScheduleJsonConverter scheduleJsonConverter;

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public ScheduleJson getSchedule() {

        List<Demand> demandsToBeAssigned = demandRepository.findAll();
        List<Car> cars = carRepository.findAll();

        Schedule schedule = scheduleService.findSchedule(demandsToBeAssigned, cars);

        if (!demandsToBeAssigned.isEmpty() && schedule.isEmpty()) {
            logger.info("Unable to find schedule for these cars and demands.");
            scheduleJsonConverter.toScheduleJson(schedule, "Unable to find schedule for these cars and demands.");
        }

        return scheduleJsonConverter.toScheduleJson(schedule, "Determined the following schedule successfully.");
    }
}
