package com.goeuro.challenge.route.bus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.util.UriTemplate
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.RequestEntity.get

@ActiveProfiles("repository-test")
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
        16     | 148         | true
        140    | 11          | false
    }

    @Unroll
    def "a bad origin: #origin or destination: #destination request returns a client error: #errorCode"() {
        when: "a bad direct route request is made"
        def actual = restTemplate.exchange get(uriTemplate.expand(origin, destination)).build(), String
        then: "a error code #errorCode is returned"
        actual.statusCode == errorCode
        actual.body == errorMessage
        where:
        origin         | destination    | errorCode   | errorMessage
        null           | 148            | BAD_REQUEST | "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"\""
        140            | null           | BAD_REQUEST | "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"\""
        'a'            | 19             | BAD_REQUEST | "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"a\""
        153            | 'b'            | BAD_REQUEST | "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"b\""
        Long.MAX_VALUE | 114            | BAD_REQUEST | "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"9223372036854775807\""
        169            | Long.MIN_VALUE | BAD_REQUEST | "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"-9223372036854775808\""
        11             | 11             | BAD_REQUEST | "Origin: 11 and Destination: 11 must not be the same!"
    }
}
