package com.goeuro.challenge.route.bus

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.DefaultApplicationArguments
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@Profile("repository-test")
class BusRouteTestConfiguration {

    @Bean
    @Primary
    ApplicationArguments springApplicationArguments() {
        return new DefaultApplicationArguments(["src/test/resources/example"] as String[])
    }
}
