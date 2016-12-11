package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.RouteChallengeException;

import static java.lang.String.format;

public class BusStationNotFoundException extends RouteChallengeException {
    public BusStationNotFoundException(int stationId) {
        super(format("Station with ID: %d not found.", stationId));
    }
}
