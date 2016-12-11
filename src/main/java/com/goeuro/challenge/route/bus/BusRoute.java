package com.goeuro.challenge.route.bus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goeuro.challenge.route.Route;

public class BusRoute extends Route {

    public BusRoute() {
        super();
    }

    public BusRoute(int origin, int destination, boolean direct) {
        super(origin, destination, direct);
    }

    @Override
    @JsonProperty("dep_sid")
    public int getOrigin() {
        return super.origin;
    }

    @Override
    @JsonProperty("arr_sid")
    public int getDestination() {
        return super.destination;
    }

    @Override
    @JsonProperty("direct_bus_route")
    public boolean isDirect() {
        return super.direct;
    }
}
