package com.goeuro.challenge.route.bus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(path = "/api")
public class BusRouteController {
    @RequestMapping(path= "/direct", produces = APPLICATION_JSON_UTF8_VALUE)
    public DirectRoute directRoute(@RequestParam("dep_sid") int origin, @RequestParam("arr_sid") int destination) {
        return new DirectRoute(origin, destination, false);
    }
}
