package com.reply.mobilityondemand.schedule;


import com.reply.mobilityondemand.car.domain.Car;
import com.reply.mobilityondemand.demand.domain.Demand;

public class AssignmentDemandCar {

    private Demand demand;
    private Car car;

    public AssignmentDemandCar(Demand demand, Car car) {
        this.demand = demand;
        this.car = car;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
