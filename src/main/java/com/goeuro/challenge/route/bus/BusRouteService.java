package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.Route;
import com.goeuro.challenge.route.RouteRepository;
import com.goeuro.challenge.route.RouteService;
import com.goeuro.challenge.route.StationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BusRouteService implements RouteService {

    private final RouteRepository busRouteRepository;

    @Override
    public Route getRoute(int origin, int destination) {
        Map<Integer, Set<Integer>> routes = busRouteRepository.routes();
        if(!routes.containsKey(origin)) {
            throw new StationNotFoundException(origin);
        }
        if(!routes.containsKey(destination)){
            throw new StationNotFoundException(destination);
        }
        return new BusRoute(origin, destination, true);
    }
}
