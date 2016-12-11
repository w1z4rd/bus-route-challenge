package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.Route;
import com.goeuro.challenge.route.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BusRouteController {

    private final RouteService busRouteService;

    @RequestMapping(path = "/direct", produces = APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Route> directRoute(@RequestParam("dep_sid") int origin, @RequestParam("arr_sid") int destination) {
        log.info("Request received with parameters dep_sid: {}, arr_sid: {}", origin, destination);

        if (origin == destination) {
            throw new SameBusStationException(origin, destination);
        }

        Route response = busRouteService.getRoute(origin, destination);
        log.info("Response returned: {}", response);
        return ResponseEntity.ok(response);
    }
}
