package com.goeuro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        if (args == null || args.length != 1 || args[0] == null) {
            log.error("Missing mandatory application parameter data file path!");
            System.exit(1);
        }
        System.getProperties().setProperty("dataFile", args[0]);
        log.info("Bus Route Challenge Application Starting with data file path: {}", args[0]);
        SpringApplication.run(Application.class, args);
    }
}
