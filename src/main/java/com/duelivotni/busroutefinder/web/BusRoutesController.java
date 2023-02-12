package com.duelivotni.busroutefinder.web;

import com.duelivotni.busroutefinder.service.RouteFinderService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BusRoutesController {
    private final RouteFinderService routeFinderService;

    public BusRoutesController(RouteFinderService routeFinderService) {
        this.routeFinderService = routeFinderService;
    }

    @GetMapping("/direct")
    public String isRoutePossible(@RequestParam("from") Integer departureStationId,
                                  @RequestParam("to") Integer arrivalStationId) {
        boolean directBusRoute = routeFinderService.isRoutePossible(departureStationId, arrivalStationId);
        DirectRouteResponse directRouteResponse = new DirectRouteResponse(departureStationId, arrivalStationId, directBusRoute);
        return new Gson().toJson(directRouteResponse);
    }
}