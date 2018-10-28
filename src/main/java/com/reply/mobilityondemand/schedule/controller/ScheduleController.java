package com.reply.mobilityondemand.schedule.controller;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.car.repository.CarRepository;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.repository.DemandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("schedule")
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private ScheduleValidator scheduleValidator;

    @Autowired
    private ScheduleJsonConverter scheduleJsonConverter;


    @RequestMapping(method = RequestMethod.GET)
    public ScheduleJson getSchedule() {

        List<Demand> demandsToBeAssigned = demandRepository.findAll();

        scheduleValidator.throwIfUserWithDemandTimeOverlapExist(demandsToBeAssigned);

        List<Car> cars = carRepository.findAll();

        Schedule schedule = new Schedule();

        while (!demandsToBeAssigned.isEmpty()) {
            List<AssignmentDemandCar> nextPossibleAssignments = determineNextPossibleAssignments(demandsToBeAssigned, cars, schedule.getScheduleAsMap());

            if (nextPossibleAssignments.isEmpty()) {
                if (schedule.isEmpty()) {
                    logger.info("Unable to find schedule for these cars and demands.");
                    return scheduleJsonConverter.toScheduleJson(schedule, "Unable to find schedule for these cars and demands.");
                } else {
                    ScheduleStep lastScheduleStep = schedule.getLastScheduleStep().get();
                    AssignmentDemandCar currentAssignment = lastScheduleStep.getCurrentAssignment();
                    demandsToBeAssigned.add(currentAssignment.getDemand());

                    int currentAssignmentIndex = lastScheduleStep.getCurrentAssignmentIndex();

                    while (currentAssignmentIndex == lastScheduleStep.getNumberOfPossibleAssignments() - 1) {
                        schedule.removeLastStep();
                        if (schedule.isEmpty()) {
                            logger.info("Unable to find schedule for these cars and demands.");
                            return scheduleJsonConverter.toScheduleJson(schedule, "Unable to find schedule for these cars and demands.");
                        }
                    }
                    lastScheduleStep.setCurrentAssignmentIndex(currentAssignmentIndex + 1);

                    AssignmentDemandCar newAssignment = lastScheduleStep.getCurrentAssignment();
                    demandsToBeAssigned.remove(newAssignment.getDemand());

                }
            } else {
                ScheduleStep scheduleStep = new ScheduleStep();

                scheduleStep.setPossibleAssignments(nextPossibleAssignments);
                scheduleStep.setCurrentAssignmentIndex(0);

                schedule.add(scheduleStep);

                AssignmentDemandCar currentAssignment = scheduleStep.getCurrentAssignment();
                demandsToBeAssigned.remove(currentAssignment.getDemand());
            }
        }

        return scheduleJsonConverter.toScheduleJson(schedule, "Determined the following schedule successfully.");
    }

    private List<AssignmentDemandCar> determineNextPossibleAssignments(List<Demand> demands, List<Car> cars, Map<Demand, Car> currentSchedule) {

        List<AssignmentDemandCar> possibleAssignments = new ArrayList<>();

        for (Demand demand : demands) {
            for (Car car : cars) {
                if (scheduleValidator.isValidAssigment(car, demand, currentSchedule)) {

                    AssignmentDemandCar assignment = new AssignmentDemandCar(demand, car);
                    possibleAssignments.add(assignment);
                }
            }
        }
        return possibleAssignments;
    }
}
