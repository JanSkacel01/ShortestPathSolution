package org.example;

import java.util.*;

public class AStar {
    public static List<String> findShortestPath(String start, String end, List<Airport> airports, double heuristicDistance) {
        // Map of airport codes to Airport objects
        Map<String, Airport> airportMap = new HashMap<>();
        for (Airport airport : airports) {
            airportMap.put(airport.getName(), airport);
        }

        // Priority queue for A* (sorted by fScore)
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(AStarNode::getFScore));
        openSet.add(new AStarNode(start, 0, heuristicDistance));

        // Map to store gScores (cost from start to each node)
        Map<String, Double> gScores = new HashMap<>();
        gScores.put(start, 0.0);

        // Map to store shortest path tree
        Map<String, String> cameFrom = new HashMap<>();

        // Set to track visited nodes
        Set<String> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            AStarNode currentNode = openSet.poll();
            String currentAirport = currentNode.getCode();

            // Stop if we reach the destination
            if (currentAirport.equals(end)) {
                break;
            }

            // Skip if already visited
            if (closedSet.contains(currentAirport)) {
                continue;
            }
            closedSet.add(currentAirport);

            // Get current airport
            Airport airport = airportMap.get(currentAirport);
            if (airport == null) continue;

            // Process connections
            for (Connection connection : airport.getConnections()) {
                String neighbor = connection.getCode();
                if (closedSet.contains(neighbor)) continue;

                double distanceInKm = toKilometers(
                        connection.getDistance().getValue(),
                        connection.getDistance().getUnit()
                );

                double tentativeGScore = gScores.getOrDefault(currentAirport, Double.MAX_VALUE) + distanceInKm;

                if (tentativeGScore < gScores.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    gScores.put(neighbor, tentativeGScore);
                    cameFrom.put(neighbor, currentAirport);

                    double heuristic = neighbor.equals(end) ? 0 : heuristicDistance;
                    double fScore = tentativeGScore + heuristic;

                    openSet.add(new AStarNode(neighbor, tentativeGScore, fScore));
                }
            }
        }

        // Reconstruct path
        List<String> path = new ArrayList<>();
        String step = end;
        while (step != null) {
            path.add(step);
            step = cameFrom.get(step);
        }
        Collections.reverse(path);

        // Return path if it leads to the start, otherwise return empty
        return path.get(0).equals(start) ? path : Collections.emptyList();
    }

    public static double toKilometers(double value, String unit) {
        if ("miles".equalsIgnoreCase(unit)) {
            return value * 1.60934; // Convert miles to kilometers
        }
        return value; // Already in kilometers
    }
}



