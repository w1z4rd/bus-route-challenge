package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcollections.HashTreePMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FileBusRouteRepository implements RouteRepository {

    private final ApplicationArguments applicationArguments;

    private Map<Integer, Set<Integer>> routes;

    @Override
    public Map<Integer, Set<Integer>> routes() {
        log.info("applicationArguments: ", applicationArguments);
        return routes;
    }

    @PostConstruct
    private void loadRoutes() {
        log.info("applicationArguments: ", applicationArguments);
        routes = HashTreePMap.empty();
    }
}
