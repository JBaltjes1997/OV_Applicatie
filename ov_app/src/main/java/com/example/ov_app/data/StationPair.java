package com.example.ov_app.data;

import java.util.Objects;

public class StationPair {

    private String departureLocation;
    private String destination;

    // for jackson
    public StationPair() {}

    public StationPair(String departureLocation, String destination) {
        this.departureLocation = departureLocation;
        this.destination = destination;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationPair that = (StationPair) o;

        if (!Objects.equals(departureLocation, that.departureLocation))
            return false;
        return Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        int result = departureLocation != null ? departureLocation.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return departureLocation + " ➡ " + destination;
    }
}
