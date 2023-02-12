package com.duelivotni.busroutefinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.duelivotni.busroutefinder.data.BusRoutesFileReader;
import com.duelivotni.busroutefinder.service.RouteFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusRouteFinderApplicationTest {

    @Autowired
    private RouteFinderService routeFinderService;
    @Autowired
    private BusRoutesFileReader busRoutesFileReader;

    @BeforeEach
    public void setUp() {
        var stationToStationsRoutesMap = new HashMap<Integer, Set<Integer>>();

        Set<Integer> stations1 = new HashSet<>();
        stations1.add(1);
        stations1.add(2);
        stations1.add(3);
        stationToStationsRoutesMap.put(1, stations1);

        Set<Integer> stations2 = new HashSet<>();
        stations2.add(2);
        stations2.add(3);
        stations2.add(4);
        stationToStationsRoutesMap.put(2, stations2);

        Set<Integer> stations3 = new HashSet<>();
        stations3.add(3);
        stations3.add(4);
        stations3.add(5);
        stationToStationsRoutesMap.put(3, stations3);

        busRoutesFileReader.buildRoutesMapForTesting(stationToStationsRoutesMap);
        routeFinderService = new RouteFinderService(busRoutesFileReader);
    }

    @Test
    public void isRoutePossible_routeExist_shouldReturnTrue() {
        boolean result = routeFinderService.isRoutePossible(1, 3);
        assertTrue(result);
    }

    @Test
    public void isRoutePossible_routeDoesntExist_shouldReturnFalse() {
        boolean result = routeFinderService.isRoutePossible(1, 5);
        assertFalse(result);
    }

    @Test
    public void isRoutePossible_departureAndArrival_is_sameStation_tripIsNotPossible_shouldReturnFalse() {
        boolean result = routeFinderService.isRoutePossible(4, 4);
        assertFalse(result);
    }
}

