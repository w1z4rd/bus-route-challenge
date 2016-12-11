package com.goeuro.challenge.route.bus;

import com.goeuro.challenge.route.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FileBusRouteRepository implements RouteRepository {

    private final ApplicationArguments applicationArguments;

    private Map<Integer, Set<Integer>> routesMap = loadRoutes();

    @Override
    public Map<Integer, Set<Integer>> routes() {
        return routesMap;
    }

    @SneakyThrows
    private Map<Integer, Set<Integer>> loadRoutes() {
        Map<Integer, Set<Integer>> mutableRoutesMap = new HashMap<>();

        //TODO: replace with applicationArguments arg[0]
        String dataFileStringPath = System.getProperty("dataFile");

        Resource dataResource = new FileSystemResource(dataFileStringPath);

        try (Scanner scanner = new Scanner(dataResource.getInputStream(), UTF_8.name())) {
            int totalNumberOfRoutes = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < totalNumberOfRoutes; i++) {
                String line = scanner.nextLine();

                PVector<Integer> route = TreePVector.from(Arrays.stream(line
                        .split("\\s"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()));
                if (route.size() < 3) {
                    throw new NoSuchElementException("Route ID: " + route.get(0) + " has to few stations!");
                }
                PSet<Integer> stations = HashTreePSet.from(route.minus(0));

                stations.forEach(currentStation -> {
                    mutableRoutesMap.computeIfPresent(currentStation, (station, connections) -> HashTreePSet.from(connections).plusAll(stations.minus(station)));
                    mutableRoutesMap.computeIfAbsent(currentStation, station -> HashTreePSet.from(stations.minus(station)));
                });
            }
        }
        return HashTreePMap.from(mutableRoutesMap);
    }
}
