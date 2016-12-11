package com.goeuro.challenge.route;

import java.util.Map;
import java.util.Set;

public interface RouteRepository {
    Map<Integer, Set<Integer>> routes();
}
