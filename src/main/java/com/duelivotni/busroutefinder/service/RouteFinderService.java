package com.duelivotni.busroutefinder.service;

import com.duelivotni.busroutefinder.data.BusRoutesFileReader;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RouteFinderService {
    private final BusRoutesFileReader busRouteFileReader;

    public RouteFinderService(BusRoutesFileReader busRouteFileReader) {
        this.busRouteFileReader = busRouteFileReader;
    }

    /**
     * @param departureStationId
     * @param arrivalStationId
     * @return True if a bus route between two stations is possible. 
     *         Otherwise, false.
     * 
     * Finding the departure station key in Routes Map
     * Checking if a value set of stations contains the arrival station
     */
    public boolean isRoutePossible(Integer departureStationId, Integer arrivalStationId) {
        Map<Integer, Set<Integer>> routes = busRouteFileReader.getRoutes();
        Set<Integer> connectedStations = routes.get(departureStationId);
        return connectedStations != null && !connectedStations.isEmpty() && connectedStations.contains(arrivalStationId);
    }
}
