package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.Route;
import com.goeuro.challenge.route.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BusRouteController {

    private final RouteService busRouteService;

    @RequestMapping(path= "/direct", produces = APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public Route directRoute(@RequestParam("dep_sid") int origin, @RequestParam("arr_sid") int destination) {
        return busRouteService.getRoute(origin,destination);
    }
}
