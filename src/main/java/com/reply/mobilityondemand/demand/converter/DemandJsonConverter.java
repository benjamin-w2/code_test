package com.reply.mobilityondemand.demand.converter;

import com.reply.mobilityondemand.demand.controller.DemandJson;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.user.domain.User;
import com.reply.mobilityondemand.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DemandJsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(DemandJsonConverter.class);

    @Autowired
    private UserRepository userRepository;

    public Demand toDemand(DemandJson demandJson) {

        Demand demand = new Demand();
        demand.setDemandId(demandJson.getDemandId());
        demand.setPickUpLocation(demandJson.getPickUpLocation());
        demand.setDropOffLocation(demandJson.getDropOffLocation());
        demand.setEarliestPickUpTime(demandJson.getEarliestPickUpTime());
        demand.setLatestDropOffTime(demandJson.getLatestDropOffTime());
        demand.setDesiredCarFeatures(demandJson.getDesiredCarFeatures());

        demand.setUser(getUser(demandJson.getUserId()));

        return demand;
    }

    private User getUser(UUID userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent())
            return optionalUser.get();
        else {
            logger.info("No user found with id: {}", userId);
            throw new DemandJsonConverterException("No user found with id: " + userId);
        }
    }

    public DemandJson toDemandJson(Demand demand) {

        DemandJson demandJson = new DemandJson();

        demandJson.setDemandId(demand.getDemandId());
        demandJson.setPickUpLocation(demand.getPickUpLocation());
        demandJson.setDropOffLocation(demand.getDropOffLocation());
        demandJson.setEarliestPickUpTime(demand.getEarliestPickUpTime());
        demandJson.setLatestDropOffTime(demand.getLatestDropOffTime());
        demandJson.setDesiredCarFeatures(demand.getDesiredCarFeatures());

        User user = demand.getUser();
        demandJson.setUserId(user.getUserId());

        return demandJson;
    }
}
