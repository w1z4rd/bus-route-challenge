package com.goeuro.challenge.route.bus

import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

class BusRouteControllerTest extends Specification {

    def busRouteService = Mock(BusRouteService)
    private static def validOrigin = 5
    private static def validDestination = 11

    @Subject
    BusRouteController busRouteController = new BusRouteController(busRouteService)

    def "get direct route success response for different origin/destination stations"() {
        when: "a direct route request is received"
        def actual = busRouteController.directRoute validOrigin, validDestination
        then: "the bus service get route is called"
        1 * busRouteService.getRoute(validOrigin, validDestination) >> new BusRoute(validOrigin, validDestination, true)
        then: "a route response is returned"
        actual == ResponseEntity.ok(new BusRoute(validOrigin, validDestination, true))
    }

    def "a request with the same origin and destination throws a SameBusStationException"() {
        when: "a direct route request is received"
        busRouteController.directRoute validOrigin, validOrigin
        then: "a SameStationException is thrown"
        def actual = thrown(SameBusStationException)
        actual.message == "Origin: $validOrigin and Destination: $validOrigin must not be the same!".toString()
    }
}
