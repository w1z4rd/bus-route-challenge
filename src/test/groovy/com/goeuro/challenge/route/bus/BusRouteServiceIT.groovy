package com.goeuro.challenge.route.bus

import org.springframework.boot.ApplicationArguments
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class BusRouteServiceIT extends Specification {
    @Shared
    ApplicationArguments applicationArguments

    @Shared
    FileBusRouteRepository routeRepository

    @Subject
    BusRouteService busRouteService = new BusRouteService(routeRepository)

    def setupSpec() {
        applicationArguments = Mock(ApplicationArguments)
        applicationArguments.getSourceArgs() >> ["src/test/resources/example"]
        System.getProperties().setProperty("dataFile", "src/test/resources/example")
        routeRepository = new FileBusRouteRepository(applicationArguments)
    }

    @Unroll
    def "a valid origin #origin and destination #destination on the same route yields a direct route response"() {
        when: "a get route call is made"
        def actual = busRouteService.getRoute(origin, destination);
        then: "a direct route is returned"
        actual == new BusRoute(origin, destination, true)
        where:
        origin | destination
        5      | 11
        16     | 148
    }
}
