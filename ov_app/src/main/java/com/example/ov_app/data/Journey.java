package com.example.ov_app.data;

import java.time.LocalTime;
import java.util.List;

public class Journey {

    private final LocalTime departure;
    private final LocalTime arrival;
    private final String departurePlatform;
    private final String arrivalPlatform;
    private final long travelTime;
    private final List<Node> nodes;

    public Journey(LocalTime departure, LocalTime arrival, String departurePlatform, String arrivalPlatform, long travelTime, List<Node> nodes) {
        this.departure = departure;
        this.arrival = arrival;
        this.departurePlatform = departurePlatform;
        this.arrivalPlatform = arrivalPlatform;
        this.travelTime = travelTime;
        this.nodes = nodes;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public String getDeparturePlatform() {
        return departurePlatform;
    }

    public String getArrivalPlatform() {
        return arrivalPlatform;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}