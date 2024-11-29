package org.example;

public class Node {
    private final String code;
    private final double distance;

    public Node(String code, double distance) {
        this.code = code;
        this.distance = distance;
    }

    public String getCode() {
        return code;
    }

    public double getDistance() {
        return distance;
    }
}
