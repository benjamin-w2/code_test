package com.reply.mobilityondemand.demand.controller;

import com.reply.mobilityondemand.demand.controller.Exception.DemandIdMismatchException;
import com.reply.mobilityondemand.demand.controller.Exception.DemandNotFoundException;
import com.reply.mobilityondemand.demand.converter.DemandJsonConverter;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import com.reply.mobilityondemand.demand.repository.DemandRepository;
import com.reply.mobilityondemand.demand.repository.DesiredCarFeaturesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("demands")
public class DemandController {

    private static final Logger logger = LoggerFactory.getLogger(DemandController.class);

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private DemandJsonConverter demandJsonConverter;

    @Autowired
    private DesiredCarFeaturesRepository desiredCarFeaturesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<DemandJson> getDemands() {

        List<DemandJson> demandJsonList = new ArrayList<>();

        for (Demand demand : demandRepository.findAll()) {
            demandJsonList.add(demandJsonConverter.toDemandJson(demand));
        }

        return demandJsonList;
    }

    @RequestMapping(value = "/{demandId}", method = RequestMethod.GET)
    public DemandJson getDemand(@PathVariable UUID demandId) {

        Optional<Demand> optionalDemand = demandRepository.findById(demandId);

        if (optionalDemand.isPresent()) {
            Demand demand = optionalDemand.get();
            return demandJsonConverter.toDemandJson(demand);
        } else {
            logger.info("No demand found with demandId: {}", demandId);
            throw new DemandNotFoundException(demandId);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> createDemand(@Valid @RequestBody DemandJson demandJson,
                                           @Value("#{request.requestURL}") String url) {
        
        Demand demand = demandJsonConverter.toDemand(demandJson);

        UUID demandId = UUID.randomUUID();
        demand.setDemandId(demandId);

        persistDesiredCarFeatures(demand.getDesiredCarFeatures());

        demandRepository.save(demand);
        logger.info("Created a new demand with demandId: {}", demandId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url + "/" + demandId));
        return new HttpEntity<>(headers);
    }

    @RequestMapping(value = "/{demandId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<String> updateDemand(@PathVariable UUID demandId,
                                           @Valid @RequestBody DemandJson demandJson,
                                           @Value("#{request.requestURL}") String url) {

        if (demandJson.getDemandId() != null && !demandJson.getDemandId().equals(demandId)) {
            logger.info("DemandId '{}' provided in the path does not match with body demandId '{}'", demandId, demandJson.getDemandId());
            throw new DemandIdMismatchException(demandId, demandJson.getDemandId());
        }

        Demand demand = demandJsonConverter.toDemand(demandJson);
        demand.setDemandId(demandId);

        persistDesiredCarFeatures(demand.getDesiredCarFeatures());

        demandRepository.save(demand);
        logger.info("Created a new demand with demandId: {}", demandId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new HttpEntity<>(headers);
    }

    private void persistDesiredCarFeatures(DesiredCarFeatures desiredCarFeatures) {
        UUID desiredCarFeaturesId = UUID.randomUUID();
        desiredCarFeatures.setDesiredCarFeaturesId(desiredCarFeaturesId);
        desiredCarFeaturesRepository.save(desiredCarFeatures);
    }

    @RequestMapping(value = "/{demandId}", method = RequestMethod.DELETE)
    public void deleteDemand(@PathVariable UUID demandId) {

        Optional<Demand> optionalDemand = demandRepository.findById(demandId);

        if (optionalDemand.isPresent()) {
            Demand demand = optionalDemand.get();
            DesiredCarFeatures desiredCarFeatures = demand.getDesiredCarFeatures();

            demandRepository.deleteById(demandId);
            desiredCarFeaturesRepository.deleteById(desiredCarFeatures.getDesiredCarFeaturesId());
        } else {
            logger.info("No demand found with demandId: {}", demandId);
            throw new DemandNotFoundException(demandId);
        }
    }
}