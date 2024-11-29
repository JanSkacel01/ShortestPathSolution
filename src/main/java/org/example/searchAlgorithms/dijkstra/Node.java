package org.example.searchAlgorithms.dijkstra;

public class Node {
    private final String code;
    private final double distance; // Cumulative distance
    private final double relativeDistance; // Distance from the previous node

    public Node(String code, double distance, double relativeDistance) {
        this.code = code;
        this.distance = distance;
        this.relativeDistance = relativeDistance;
    }

    public String getCode() {
        return code;
    }

    public double getDistance() {
        return distance;
    }

    public double getRelativeDistance() {
        return relativeDistance;
    }
}

