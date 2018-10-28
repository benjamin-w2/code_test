package com.reply.mobilityondemand.schedule;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.demand.domain.Demand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Schedule {

    private List<ScheduleStep> scheduleSteps = new ArrayList<>();

    public Map<Demand, Car> getScheduleAsMap() {
        Map<Demand, Car> scheduleMap = new HashMap<>();

        for (ScheduleStep scheduleStep : scheduleSteps) {
            AssignmentDemandCar currentAssignment = scheduleStep.getCurrentAssignment();
            scheduleMap.put(currentAssignment.getDemand(), currentAssignment.getCar());
        }

        return scheduleMap;
    }

    public void add(ScheduleStep scheduleStep) {
        scheduleSteps.add(scheduleStep);
    }

    public Optional<ScheduleStep> getLastScheduleStep() {
        return scheduleSteps.isEmpty() ? Optional.empty() :
                Optional.of(scheduleSteps.get(scheduleSteps.size() - 1));
    }

    public boolean isEmpty() {
        return scheduleSteps.isEmpty();
    }

    public void removeLastStep() {
        if (!scheduleSteps.isEmpty()) {
            scheduleSteps.remove(scheduleSteps.get(scheduleSteps.size() - 1));
        }
    }
}
