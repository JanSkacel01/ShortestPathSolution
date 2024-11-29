package org.example;

public class AStarNode {
    private final String code;
    private final double gScore; // Cost from start to current node
    private final double fScore; // Estimated total cost (gScore + heuristic)

    public AStarNode(String code, double gScore, double fScore) {
        this.code = code;
        this.gScore = gScore;
        this.fScore = fScore;
    }

    public String getCode() {
        return code;
    }

    public double getGScore() {
        return gScore;
    }

    public double getFScore() {
        return fScore;
    }
}

