package com.goeuro.challenge.route;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class Route {
    protected int origin;
    protected int destination;
    protected boolean direct;

    protected Route() {
    }

    protected Route(int origin, int destination, boolean direct) {
        this.origin = origin;
        this.destination = destination;
        this.direct = direct;
    }

    public abstract int getOrigin();

    public abstract int getDestination();

    public abstract boolean isDirect();
}
