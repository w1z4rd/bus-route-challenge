package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.Route;
import com.goeuro.challenge.route.RouteService;
import org.springframework.stereotype.Service;

@Service
public class BusRouteService implements RouteService {

    @Override
    public Route getRoute(int origin, int destination) {
        return new BusRoute(origin, destination, true);
    }
}
