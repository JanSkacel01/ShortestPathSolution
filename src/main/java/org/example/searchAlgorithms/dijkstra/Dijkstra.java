package org.example.searchAlgorithms.dijkstra;

import org.example.graph.Airport;
import org.example.graph.Connection;

import java.util.*;

public class Dijkstra {
    public static String findShortestPath(String start, String end, List<Airport> airports) {
        Map<String, Airport> airportMap = new HashMap<>();
        for (Airport airport : airports) {
            airportMap.put(airport.getName(), airport);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        queue.add(new Node(start, 0, 0));

        Map<String, Double> distances = new HashMap<>();
        distances.put(start, 0.0);

        Map<String, String> previous = new HashMap<>();
        Map<String, Double> relativeDistances = new HashMap<>();

        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentAirport = currentNode.getCode();

            if (visited.contains(currentAirport)) continue;
            visited.add(currentAirport);

            if (currentAirport.equals(end)) break;

            Airport airport = airportMap.get(currentAirport);
            if (airport == null) continue;

            for (Connection connection : airport.getConnections()) {
                String neighbor = connection.getCode();
                if (visited.contains(neighbor)) continue;

                double distanceInKm = toKilometers(
                        connection.getDistance().getValue(),
                        connection.getDistance().getUnit()
                );

                double newDistance = distances.getOrDefault(currentAirport, Double.MAX_VALUE) + distanceInKm;

                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    relativeDistances.put(neighbor, distanceInKm);
                    previous.put(neighbor, currentAirport);
                    queue.add(new Node(neighbor, newDistance, distanceInKm));
                }
            }
        }

        // Build the path
        List<String> path = new ArrayList<>();
        String step = end;
        while (step != null) {
            path.add(step);
            step = previous.get(step);
        }
        Collections.reverse(path);

        if (!path.get(0).equals(start)) return "No path found";

        // Format output
        StringBuilder result = new StringBuilder();
        double cumulativeDistance = 0;
        for (int i = 0; i < path.size(); i++) {
            String current = path.get(i);
            double relativeDistance = i == 0 ? 0 : relativeDistances.getOrDefault(current, 0.0);
            cumulativeDistance += relativeDistance;

            if (i > 0) {
                result.append(" -> ");
            }
            result.append(current).append("(")
                    .append(String.format("%.2f", cumulativeDistance)).append(",")
                    .append(String.format("%.2f", relativeDistance)).append(")");
        }

        return result.toString();
    }

    public static double toKilometers(double value, String unit) {
        if ("miles".equalsIgnoreCase(unit)) {
            return value * 1.60934;
        }
        return value;
    }
}
