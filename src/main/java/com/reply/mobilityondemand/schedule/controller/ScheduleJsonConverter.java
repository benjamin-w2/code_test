package com.reply.mobilityondemand.schedule.controller;

import org.springframework.stereotype.Component;

@Component
public class ScheduleJsonConverter {

    public ScheduleJson toScheduleJson(Schedule schedule, String message) {
        ScheduleJson scheduleJson = new ScheduleJson(message);

        scheduleJson.setDemandCarAssignment(schedule.getScheduleAsMap());

        return scheduleJson;
    }
}
