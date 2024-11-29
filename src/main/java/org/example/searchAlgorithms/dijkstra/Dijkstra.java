package org.example;

import java.util.*;

public class Dijkstra {
    public static List<String> findShortestPath(String start, String end, List<Airport> airports) {
        // Map of airport codes to Airport objects
        Map<String, Airport> airportMap = new HashMap<>();
        for (Airport airport : airports) {
            airportMap.put(airport.getName(), airport);
        }

        // Priority queue for Dijkstra's algorithm
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        queue.add(new Node(start, 0));

        // Map to store the shortest distances from the start node
        Map<String, Double> distances = new HashMap<>();
        distances.put(start, 0.0);

        // Map to store the shortest path tree
        Map<String, String> previous = new HashMap<>();

        // Set to track visited nodes
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentAirport = currentNode.getCode();

            // Skip if already visited
            if (visited.contains(currentAirport)) continue;
            visited.add(currentAirport);

            // Stop if we reach the destination
            if (currentAirport.equals(end)) break;

            // Get current airport
            Airport airport = airportMap.get(currentAirport);
            if (airport == null) continue;

            // Process all connections
            for (Connection connection : airport.getConnections()) {
                String neighbor = connection.getCode();
                if (visited.contains(neighbor)) continue;

                double distanceInKm = toKilometers(
                        connection.getDistance().getValue(),
                        connection.getDistance().getUnit()
                );

                double newDistance = distances.getOrDefault(currentAirport, Double.MAX_VALUE) + distanceInKm;

                // If a shorter path is found
                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentAirport);
                    queue.add(new Node(neighbor, newDistance));
                }
            }
        }

        // Reconstruct the path
        List<String> path = new ArrayList<>();
        String step = end;
        while (step != null) {
            path.add(step);
            step = previous.get(step);
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
