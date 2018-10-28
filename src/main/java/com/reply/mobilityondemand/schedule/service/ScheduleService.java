package com.reply.mobilityondemand.schedule.service;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.schedule.AssignmentDemandCar;
import com.reply.mobilityondemand.schedule.Schedule;
import com.reply.mobilityondemand.schedule.ScheduleStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleService {

    @Autowired
    private ScheduleValidator scheduleValidator;

    public Schedule findSchedule(List<Demand> demandsToBeAssigned, List<Car> cars) {

        scheduleValidator.throwIfUserWithDemandTimeOverlapExist(demandsToBeAssigned);

        Schedule schedule = new Schedule();

        while (!demandsToBeAssigned.isEmpty()) {

            List<AssignmentDemandCar> nextPossibleAssignments = determineNextPossibleAssignments(demandsToBeAssigned, cars, schedule.getScheduleAsMap());

            if (nextPossibleAssignments.isEmpty()) {
                if (schedule.isEmpty()) {
                    return schedule;
                } else {
                    if (!useNextAssignmentOfPreviousStepsIfPossible(demandsToBeAssigned, schedule)) {
                        return schedule;
                    }
                }
            } else {
                ScheduleStep scheduleStep = getNextScheduleStep(nextPossibleAssignments);
                schedule.add(scheduleStep);

                AssignmentDemandCar currentAssignment = scheduleStep.getCurrentAssignment();
                demandsToBeAssigned.remove(currentAssignment.getDemand());
            }
        }

        return schedule;
    }

    private boolean useNextAssignmentOfPreviousStepsIfPossible(List<Demand> demandsToBeAssigned, Schedule schedule) {
        ScheduleStep lastScheduleStep = schedule.getLastScheduleStep().get();
        AssignmentDemandCar currentAssignment = lastScheduleStep.getCurrentAssignment();
        demandsToBeAssigned.add(currentAssignment.getDemand());

        int currentAssignmentIndex = lastScheduleStep.getCurrentAssignmentIndex();

        while (currentAssignmentIndex == lastScheduleStep.getNumberOfPossibleAssignments() - 1) {
            schedule.removeLastStep();
            if (schedule.isEmpty()) {
                return false;
            }
        }
        lastScheduleStep.setCurrentAssignmentIndex(currentAssignmentIndex + 1);

        AssignmentDemandCar newAssignment = lastScheduleStep.getCurrentAssignment();
        demandsToBeAssigned.remove(newAssignment.getDemand());
        return true;
    }

    private ScheduleStep getNextScheduleStep(List<AssignmentDemandCar> nextPossibleAssignments) {
        ScheduleStep scheduleStep = new ScheduleStep();
        scheduleStep.setPossibleAssignments(nextPossibleAssignments);
        scheduleStep.setCurrentAssignmentIndex(0);
        return scheduleStep;
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