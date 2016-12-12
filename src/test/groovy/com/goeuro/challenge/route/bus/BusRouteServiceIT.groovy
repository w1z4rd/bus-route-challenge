package com.goeuro.challenge.route.bus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles("repository-test")
@ContextConfiguration
@SpringBootTest(webEnvironment = NONE)
class BusRouteServiceIT extends Specification {

    @Subject
    @Autowired
    BusRouteService busRouteService;

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
