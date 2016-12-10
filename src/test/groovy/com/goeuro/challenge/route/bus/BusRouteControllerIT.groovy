package com.goeuro.challenge.route.bus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.util.UriTemplate
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.RequestEntity.get

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Subject(BusRouteController)
class BusRouteControllerIT extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @LocalServerPort
    def localServerPort

    UriTemplate uriTemplate

    def setup() {
        uriTemplate = new UriTemplate("http://localhost:$localServerPort/api/direct?dep_sid={origin}&arr_sid={destination}")
    }

    @Unroll
    def "a direct route request with an existing origin: #origin and destination: #destination returns a direct route response"() {
        when: "a direct route request is made"
        def actual = restTemplate.exchange get(uriTemplate.expand(origin, destination)).build(), BusRoute
        then: "a direct route response is received"
        actual.statusCode == OK
        actual.body == new BusRoute(origin, destination, direct)
        where:
        origin | destination | direct
        1      | 2           | true
        1      | 3           | false
    }

    @Unroll
    def "a bad direct route request returns a client error"() {
        when: "a bad direct route request is made"
        def actual = restTemplate.exchange get(uriTemplate.expand(origin, destination)).build(), BusRoute
        then: "a error code #errorCode is returned"
        actual.statusCode == errorCode
        where:
        origin         | destination    | errorCode
        9999           | 2              | NOT_FOUND
        3              | 9999           | NOT_FOUND
        null           | 2              | BAD_REQUEST
        1              | null           | BAD_REQUEST
        'a'            | 2              | BAD_REQUEST
        3              | 'b'            | BAD_REQUEST
        Long.MAX_VALUE | 2              | BAD_REQUEST
        3              | Long.MIN_VALUE | BAD_REQUEST
    }
}
