package com.goeuro.challenge.route.bus;

import lombok.Value;

@Value
public class DirectRoute {
    int origin;
    int destination;
    boolean direct;
}
