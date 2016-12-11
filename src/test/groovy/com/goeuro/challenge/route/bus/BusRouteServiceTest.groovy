package com.goeuro.challenge.route.bus

import com.goeuro.challenge.route.RouteRepository
import org.pcollections.HashTreePMap
import org.pcollections.HashTreePSet
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class BusRouteServiceTest extends Specification {

    @Shared
    def routes

    @Shared
    RouteRepository repository = Mock(RouteRepository)

    @Subject
    BusRouteService busRouteService = new BusRouteService(repository)

    def setupSpec() {
        routes = HashTreePMap.singleton(Integer.valueOf(1), HashTreePSet.singleton(Integer.valueOf(2)))
                .plus(Integer.valueOf(2), HashTreePSet.singleton(Integer.valueOf(1)))
        repository.routes() >> routes
    }

    @Unroll
    def "a valid origin #origin and destination #destination on the same route yields a direct route response"() {
        when: "a get route call is made"
        def actual = busRouteService.getRoute(origin, destination);
        then: "a direct route is returned"
        actual == new BusRoute(origin, destination, true)
        where:
        origin | destination
        1      | 2
        2      | 1
    }

    @Unroll
    def "a missing origin #origin or destination #destination throws a StationNotFoundException"() {
        when: "a get route call is made"
        busRouteService.getRoute(origin, destination);
        then: "a BusStationNotFoundException is thrown"
        def actual = thrown(BusStationNotFoundException)
        actual.message =~ /Station with ID: $origin|$destination not found./
        where:
        origin | destination
        10     | 2
        1      | 11
    }
}

