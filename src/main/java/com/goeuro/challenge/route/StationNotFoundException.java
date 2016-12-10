package com.goeuro.challenge.route;

import static java.lang.String.format;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(int stationId) {
        super(format("Station with ID: %d not found", stationId));
    }
}
