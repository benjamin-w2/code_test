package com.reply.mobilityondemand.demand.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.reply.mobilityondemand.demand.controller.DemandJson;
import com.reply.mobilityondemand.demand.domain.Demand;
import com.reply.mobilityondemand.demand.domain.DesiredCarFeatures;
import com.reply.mobilityondemand.user.domain.User;
import com.reply.mobilityondemand.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class DemandJsonConverterTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID DEMAND_ID = UUID.randomUUID();
    private static final LocalDateTime TIME_1 = LocalDateTime.of(2018, 11, 30, 22, 0);
    private static final LocalDateTime TIME_2 = LocalDateTime.of(2018, 12, 5, 22, 0);
    private static final float PICK_UP_LOCATION = -1F;
    private static final float DROP_OFF_LOCATION = 2F;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DemandJsonConverter demandJsonConverter = new DemandJsonConverter();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void toDemand() {

        User user = new User();
        user.setUserId(USER_ID);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        DemandJson demandJson = getDemandJson(TIME_1, TIME_2);

        DesiredCarFeatures desiredCarFeatures = getDesiredCarFeatures();
        demandJson.setDesiredCarFeatures(desiredCarFeatures);

        Demand demand = demandJsonConverter.toDemand(demandJson);

        assertThat(demand.getDemandId()).isEqualTo(DEMAND_ID);
        assertThat(demand.getEarliestPickUpTime()).isEqualTo(TIME_1);
        assertThat(demand.getLatestDropOffTime()).isEqualTo(TIME_2);
        assertThat(demand.getPickUpLocation()).isEqualTo(PICK_UP_LOCATION);
        assertThat(demand.getDropOffLocation()).isEqualTo(DROP_OFF_LOCATION);
        assertThat(demand.getDesiredCarFeatures()).isEqualTo(desiredCarFeatures);
        assertThat(demand.getUser().getUserId()).isEqualTo(USER_ID);
    }

    private DemandJson getDemandJson(LocalDateTime earliestPickUpTime, LocalDateTime latestDropOffTime) {
        DemandJson demandJson = new DemandJson();
        demandJson.setDemandId(DEMAND_ID);
        demandJson.setUserId(USER_ID);
        demandJson.setEarliestPickUpTime(earliestPickUpTime);
        demandJson.setLatestDropOffTime(latestDropOffTime);
        demandJson.setPickUpLocation(PICK_UP_LOCATION);
        demandJson.setDropOffLocation(DROP_OFF_LOCATION);
        return demandJson;
    }

    private DesiredCarFeatures getDesiredCarFeatures() {
        DesiredCarFeatures desiredCarFeatures = new DesiredCarFeatures();
        desiredCarFeatures.setHasNavigationSystem(true);
        desiredCarFeatures.setHasSeatHeating(false);
        desiredCarFeatures.setHasAirCondition(false);
        return desiredCarFeatures;
    }

    @Test
    public void toDemandPickUpTimeAfterDropOffTime() {

        User user = new User();
        user.setUserId(USER_ID);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        DemandJson demandJson = getDemandJson(TIME_2, TIME_1);

        DesiredCarFeatures desiredCarFeatures = getDesiredCarFeatures();
        demandJson.setDesiredCarFeatures(desiredCarFeatures);

        assertThrows(DemandJsonConverterException.class, () -> demandJsonConverter.toDemand(demandJson),
                "Earliest pick up time has to be before latest drop off time");
    }

    @Test
    public void noUserFoundForUserId() {

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        DemandJson demandJson = getDemandJson(TIME_2, TIME_1);

        DesiredCarFeatures desiredCarFeatures = getDesiredCarFeatures();
        demandJson.setDesiredCarFeatures(desiredCarFeatures);

        assertThrows(DemandJsonConverterException.class, () -> demandJsonConverter.toDemand(demandJson),
                "No user found with id: " + USER_ID);
    }

    @Test
    public void toDemandJson() {

        Demand demand = getDemand();
        DesiredCarFeatures desiredCarFeatures = getDesiredCarFeatures();
        demand.setDesiredCarFeatures(desiredCarFeatures);

        demandJsonConverter.toDemandJson(demand);

        assertThat(demand.getDemandId()).isEqualTo(DEMAND_ID);
        assertThat(demand.getEarliestPickUpTime()).isEqualTo(TIME_1);
        assertThat(demand.getLatestDropOffTime()).isEqualTo(TIME_2);
        assertThat(demand.getPickUpLocation()).isEqualTo(PICK_UP_LOCATION);
        assertThat(demand.getDropOffLocation()).isEqualTo(DROP_OFF_LOCATION);
        assertThat(demand.getDesiredCarFeatures()).isEqualTo(desiredCarFeatures);
        assertThat(demand.getUser().getUserId()).isEqualTo(USER_ID);
    }

    private Demand getDemand() {
        Demand demand = new Demand();
        demand.setDemandId(DEMAND_ID);
        demand.setEarliestPickUpTime(TIME_1);
        demand.setLatestDropOffTime(TIME_2);
        demand.setPickUpLocation(PICK_UP_LOCATION);
        demand.setDropOffLocation(DROP_OFF_LOCATION);

        User user = new User();
        user.setUserId(USER_ID);
        demand.setUser(user);

        return demand;
    }
}
