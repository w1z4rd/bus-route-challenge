package com.goeuro.challenge.route;

public abstract class Route {
    protected int origin;
    protected int destination;
    protected boolean direct;

    public Route() {
    }

    public Route(int origin, int destination, boolean direct) {
        this.origin = origin;
        this.destination = destination;
        this.direct = direct;
    }

    public abstract int getOrigin();

    public abstract int getDestination();

    public abstract boolean isDirect();

    @Override
    public abstract boolean equals(Object that);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
