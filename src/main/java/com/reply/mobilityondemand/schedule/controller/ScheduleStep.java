package com.reply.mobilityondemand.schedule.controller;

import java.util.List;

public class ScheduleStep {

    private List<AssignmentDemandCar> possibleAssignments;

    private int currentAssignmentIndex;

    public void setPossibleAssignments(List<AssignmentDemandCar> possibleAssignments) {
        this.possibleAssignments = possibleAssignments;
    }

    public int getCurrentAssignmentIndex() {
        return currentAssignmentIndex;
    }

    public void setCurrentAssignmentIndex(int currentAssignmentIndex) {
        this.currentAssignmentIndex = currentAssignmentIndex;
    }

    public AssignmentDemandCar getCurrentAssignment() {
        return possibleAssignments.get(currentAssignmentIndex);
    }

    public int getNumberOfPossibleAssignments() {
        return possibleAssignments.size();
    }
}
