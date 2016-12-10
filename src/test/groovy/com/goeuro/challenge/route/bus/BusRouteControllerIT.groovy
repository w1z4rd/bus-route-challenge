package com.goeuro.challenge.route.bus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.util.UriTemplate
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
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

    @Shared
    def uriTemplate =  new UriTemplate("http://localhost:$localServerPort/api/direct?dep_sid={origin}&arr_sid={destination}")

    def "a direct route request with an existing origin: #origin and destination: #destination returns a direct route response"() {
      when: "a direct route request is made"
        def actual = restTemplate.exchange get(uriTemplate.expand(origin, destination)).build(), DirectRoute
      then: "a direct route response is received"
        actual.statusCode == OK
        actual.body.direct == expected
      where:
        origin | destination | expected
        1      | 2           | true
        1      | 3           | false
    }

}
