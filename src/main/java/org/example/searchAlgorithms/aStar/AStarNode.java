package org.example.searchAlgorithms.aStar;

class AStarNode {
    private final String code;
    private final double gScore; // Cumulative distance from start
    private final double fScore; // Estimated total cost (gScore + heuristic)
    private final double relativeDistance; // Distance from the previous node

    public AStarNode(String code, double gScore, double fScore, double relativeDistance) {
        this.code = code;
        this.gScore = gScore;
        this.fScore = fScore;
        this.relativeDistance = relativeDistance;
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

    public double getRelativeDistance() {
        return relativeDistance;
    }
}


