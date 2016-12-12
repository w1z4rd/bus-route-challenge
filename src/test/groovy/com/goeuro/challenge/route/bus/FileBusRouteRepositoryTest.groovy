package com.goeuro.challenge.route.bus

import org.pcollections.HashTreePMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.nio.file.Files

import static com.goeuro.challenge.route.bus.DataFileUtils.*
import static java.lang.System.lineSeparator
import static java.nio.file.Paths.get
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles("repository-test")
@ContextConfiguration
@SpringBootTest(webEnvironment = NONE)
class FileBusRouteRepositoryTest extends Specification {

    @Subject
    @Autowired
    FileBusRouteRepository fileBusRouteRepository

    private static final def EXPECTED_ROUTES = HashTreePMap.from([16 : [148, 140, 19] as Set,
                                                                  148: [16, 140, 19] as Set,
                                                                  140: [148, 16, 19] as Set,
                                                                  19 : [148, 140, 16] as Set,
                                                                  5  : [114, 153, 11, 169] as Set,
                                                                  114: [5, 153, 11, 169] as Set,
                                                                  153: [5, 114, 11, 169] as Set,
                                                                  11 : [5, 114, 153, 169] as Set,
                                                                  169: [5, 114, 153, 11] as Set])

    def "given a correct data file get routes returns a correct routes map"() {
        given:
        writeDataFile(CORRECT_DATA)
        when: "get routes is called"
        def actual = fileBusRouteRepository.routes()
        then: "a correct routes map is returned"
        actual == EXPECTED_ROUTES
    }

    @Unroll
    def "given a #badDataFile data file an #exception is thrown"() {
        given: "a #badDataFile data fle "
        badDataFile == 'missing' ? Files.delete(get(DATA_FILE_PATH_NAME)) : writeDataFile(data)
        when: "a the repository loads routes"
        fileBusRouteRepository.loadRoutes()
        then: "an #exception is thrown"
        thrown(exception)
        where:
        badDataFile             | exception              | data
        'missing'               | FileNotFoundException  | ''
        'empty'                 | NoSuchElementException | ''
        'insufficient_stations' | NoSuchElementException | '2' + lineSeparator() + '1 2 3' + lineSeparator() + '9 3'
        'missing_routes'        | NoSuchElementException | '3' + lineSeparator() + '1 2 3' + lineSeparator() + '6 15 20'
        'non_number_route'      | NumberFormatException  | '2' + lineSeparator() + '1 2 3' + lineSeparator() + 'a 5 7'
        'non_number_station'    | NumberFormatException  | '2' + lineSeparator() + '1 2 3' + lineSeparator() + '2 x 7'
    }


}
