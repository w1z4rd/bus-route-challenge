package com.goeuro.challenge.route.bus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goeuro.challenge.route.Route;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
public class BusRoute extends Route {

    public BusRoute() {
    }

    public BusRoute(int origin, int destination, boolean direct) {
        super(origin, destination, direct);
    }

    @Override
    @JsonProperty("dep_sid")
    public int getOrigin() {
        return origin;
    }

    @Override
    @JsonProperty("arr_sid")
    public int getDestination() {
        return destination;
    }

    @Override
    @JsonProperty("direct_bus_route")
    public boolean isDirect() {
        return direct;
    }
}
