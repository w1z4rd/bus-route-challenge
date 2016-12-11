package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.RouteChallengeException;

public class SameBusStationException extends RouteChallengeException {
    public SameBusStationException(int originId, int destinationId) {
        super(String.format("Origin: %d and Destination: %d must not be the same!", originId, destinationId));
    }
}
