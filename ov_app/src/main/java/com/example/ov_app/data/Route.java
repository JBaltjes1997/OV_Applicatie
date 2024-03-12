package com.example.ov_app.data;

import java.util.List;

public class Route {

    private final List<Node> nodes;

    public Route(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getStops() {
        return nodes;
    }
}
