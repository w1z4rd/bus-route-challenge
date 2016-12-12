package com.goeuro.challenge.route.bus

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.DefaultApplicationArguments
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

import static com.goeuro.challenge.route.bus.DataFileUtils.DATA_FILE_PATH_NAME

@Configuration
@Profile("repository-test")
class BusRouteTestConfiguration {

    @Bean
    @Primary
    ApplicationArguments springApplicationArguments() {
        return new DefaultApplicationArguments([DATA_FILE_PATH_NAME] as String[])
    }
}
