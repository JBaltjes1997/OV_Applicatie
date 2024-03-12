package com.example.ov_app.data;

import java.time.LocalTime;

public class Node {

    private final String station;
    private final LocalTime departure;
    private final String platform;

    public Node(String station, LocalTime departure, String platform) {
        this.station = station;
        this.departure = departure;
        this.platform = platform;
    }

    public String getStation() {
        return station;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public String getPlatform() {
        return platform;
    }
}
