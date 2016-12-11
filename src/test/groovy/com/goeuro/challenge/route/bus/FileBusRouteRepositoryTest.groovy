package com.goeuro.challenge.route.bus

import org.pcollections.HashTreePMap
import org.springframework.boot.ApplicationArguments
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Subject(FileBusRouteRepository)
class FileBusRouteRepositoryTest extends Specification {

    @Shared
    ApplicationArguments applicationArguments = Mock(ApplicationArguments)

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

    def setup() {
        applicationArguments.getSourceArgs() >> ["src/test/resources/example"]
        System.getProperties().setProperty("dataFile", "src/test/resources/example")
    }

    def "get routes returns a correct routes map"() {
        given: "a file bus route repository"
        def fileBusRouteRepository = new FileBusRouteRepository(applicationArguments)
        when: "get routes is called"
        def actual = fileBusRouteRepository.routes()
        then: "a correct routes map is returned"
        actual == expectedRoutes
    }

    @Unroll
    def "given a #badDataFile data file an #exception is thrown"() {
        given: "a #badDataFile data file"
        System.getProperties().setProperty("dataFile", "src/test/resources/$badDataFile")
        when: "a repository is created"
        new FileBusRouteRepository(applicationArguments)
        then: "an #exception is thrown"
        thrown(exception)
        where:
        badDataFile             | exception
        'missing'               | FileNotFoundException
        'empty'                 | NoSuchElementException
        'insufficient_stations' | NoSuchElementException
        'missing_routes'        | NoSuchElementException
        'non_number_route'      | NumberFormatException
        'non_number_station'    | NumberFormatException
    }
}
