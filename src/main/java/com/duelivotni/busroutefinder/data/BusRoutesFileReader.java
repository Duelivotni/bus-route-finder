package com.duelivotni.busroutefinder.data;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Component
public class BusRoutesFileReader {
    private static final String BUS_ROUTES_FILE = "src/main/resources/static/routes/routes.txt";
    private static final Logger logger = Logger.getLogger(BusRoutesFileReader.class.getName());
    private Map<Integer, Set<Integer>> routes = new HashMap<>();

    /**
     * Reading data from file
     * Putting data into the Map
     * Key is a station and value is a set of all stations that possible to
     * travel to from the key station
     */
    @PostConstruct
    public void readData() {
        logger.info("Reading routes data from file: " + BUS_ROUTES_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(BUS_ROUTES_FILE))) {
            parseAndSaveRoutes(reader);
        } catch (IOException e) {
            handleError(e);
        }
    }

    /**
     * @param routes map
     * Builds routes map from an argument. Used for testing
     */
    public void buildRoutesMapForTesting(HashMap<Integer, Set<Integer>> routes) {
        this.routes = routes;
    }

    public Map<Integer, Set<Integer>> getRoutes() {
        return routes;
    }

    private void parseAndSaveRoutes(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] routeData = line.split(" ");
            extractConnectedStations(routeData);
        }
        if (getRoutes().isEmpty()) {
            logger.warning("Routes data is empty. Check if the routes text file is correct.");
        } else {
            logger.info("Finished reading from routes file and prepared data\nroutes map is " + routes);
        }
    }

    /**
     * @param routeData
     *
     * Get all existing routes for each station from the map
     * Add new routes to each station and update the map
     */
    private void extractConnectedStations(String[] routeData) {
        if (routeData.length > 2) {
            for (int currentStIndex = 1; currentStIndex < routeData.length; currentStIndex++) {
                Integer currentStId = Integer.parseInt(routeData[currentStIndex]);
                Set<Integer> connectedStationIds = new HashSet<>(
                        routes.get(currentStId) == null ? Collections.emptySet() : routes.get(currentStId));
                for (int followingStIndex = currentStIndex + 1; followingStIndex < routeData.length; followingStIndex++) {
                    Integer cennectedtId = Integer.parseInt(routeData[followingStIndex]);
                    connectedStationIds.add(cennectedtId);
                }
                routes.put(currentStId, connectedStationIds);
            }
        } else {
            logger.warning("No connected stations for route id: " + routeData[0]);
        }
    }

    private void handleError(IOException e) {
        logger.warning("ERROR unable to read routes data from file because\n" + e.getMessage());
        throw new RuntimeException("Application is not ready for use");
    }
}

