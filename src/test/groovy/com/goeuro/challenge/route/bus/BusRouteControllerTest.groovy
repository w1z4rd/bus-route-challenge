package com.goeuro.challenge.route.bus

import spock.lang.Specification
import spock.lang.Subject

class BusRouteControllerTest extends Specification {

    def busRouteService = Mock(BusRouteService)

    @Subject
    BusRouteController busRouteController = new BusRouteController(busRouteService)

    def "get direct route success response"() {
        when: "a direct route request is received"
        def actual = busRouteController.directRoute 1, 2
        then: "the bus service get route is called"
        1 * busRouteService.getRoute(1, 2) >> new BusRoute(1, 2, true)
        then: "a route response is returned"
        actual == new BusRoute(1, 2, true)
    }

}