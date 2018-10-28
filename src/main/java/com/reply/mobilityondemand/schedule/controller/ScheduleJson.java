package com.reply.mobilityondemand.schedule.controller;

import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.demand.domain.Demand;

import java.util.Map;

public class ScheduleJson {

    private String message;

    private Map<Demand, Car> demandCarAssignment;

    public ScheduleJson(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Demand, Car> getDemandCarAssignment() {
        return demandCarAssignment;
    }

    public void setDemandCarAssignment(Map<Demand, Car> demandCarAssignment) {
        this.demandCarAssignment = demandCarAssignment;
    }

    @Override
    public String toString() {
        return "ScheduleJson{" +
                "message='" + message + '\'' +
                ", demandCarAssignment=" + demandCarAssignment +
                '}';
    }
}
