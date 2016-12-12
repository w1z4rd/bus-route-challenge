package com.goeuro.challenge.route.bus

import org.pcollections.HashTreePMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles("repository-test")
@ContextConfiguration
@SpringBootTest(webEnvironment = NONE)
class FileBusRouteRepositoryTest extends Specification {

    @Subject
    @Autowired
    FileBusRouteRepository fileBusRouteRepository

    private static Map<Integer, Set<Integer>> expectedRoutes

    def setupSpec() {
        expectedRoutes = HashTreePMap.from([16 : [148, 140, 19] as Set,
                                            148: [16, 140, 19] as Set,
                                            140: [148, 16, 19] as Set,
                                            19 : [148, 140, 16] as Set,
                                            5  : [114, 153, 11, 169] as Set,
                                            114: [5, 153, 11, 169] as Set,
                                            153: [5, 114, 11, 169] as Set,
                                            11 : [5, 114, 153, 169] as Set,
                                            169: [5, 114, 153, 11] as Set])
    }

    def "get routes returns a correct routes map"() {
        when: "get routes is called"
        def actual = fileBusRouteRepository.routes()
        then: "a correct routes map is returned"
        actual == expectedRoutes
    }

}
