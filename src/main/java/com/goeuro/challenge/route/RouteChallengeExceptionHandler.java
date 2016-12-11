package com.goeuro.challenge.route;

import com.goeuro.challenge.route.bus.BusStationNotFoundException;
import com.goeuro.challenge.route.bus.SameBusStationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class RouteChallengeExceptionHandler {

    @ExceptionHandler(SameBusStationException.class)
    public ResponseEntity<String> handleSameBusStationException(SameBusStationException sbse) {
        log.info(sbse.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(sbse.getMessage());
    }

    @ExceptionHandler(BusStationNotFoundException.class)
    public ResponseEntity<String> handleBusStationNotFoundException(BusStationNotFoundException bsnfe) {
        log.info(bsnfe.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(bsnfe.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme) {
        log.info(matme.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(matme.getMessage());
    }

}
