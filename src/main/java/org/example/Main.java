package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.graph.Airport;
import org.example.searchAlgorithms.aStar.AStar;
import org.example.searchAlgorithms.dijkstra.Dijkstra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Load airports from JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Airport> airports = mapper.readValue(
                    new File("src/main/java/org/example/airports.json"),
                    new TypeReference<List<Airport>>() {}
            );

            List<TestCase> testCases = new ArrayList<>();
            testCases.add(new TestCase("PRG", "DEL", 5711.0));
            testCases.add(new TestCase("BRE", "FNJ", 7831.0));
            testCases.add(new TestCase("JFK", "CAI", 9011.0));
            testCases.add(new TestCase("DUB", "DME", 2828.0));
            testCases.add(new TestCase("OKA", "EVX", 10105.0));

            for (TestCase testCase : testCases) {
                String start = testCase.start;
                String end = testCase.end;

                System.out.println("-------------------------------------------------");
                System.out.println("From " + start + " to " + end);

                // Dijkstra
                String shortestPathDijkstra = Dijkstra.findShortestPath(start, end, airports);
                System.out.println("Dijkstra Shortest path: " + shortestPathDijkstra);

                // A*
                String shortestPathAStar = AStar.findShortestPath(start, end, airports, testCase.heuristicDistance);
                System.out.println("AStar Shortest path: " + shortestPathAStar);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class TestCase {
        String start;
        String end;
        double heuristicDistance;

        public TestCase(String start, String end, double heuristicDistance) {
            this.start = start;
            this.end = end;
            this.heuristicDistance = heuristicDistance;
        }
    }
}
