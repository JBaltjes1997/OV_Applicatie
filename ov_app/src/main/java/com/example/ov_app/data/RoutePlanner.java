package com.example.ov_app.data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class RoutePlanner {

    public static final List<Route> TRAIN_TIMETABLE = generateTrainRoutes();
    public static final List<Route> BUS_TIMETABLE = generateBusRoutes();
    public static final List<String> TRAIN_STATIONS = getStations(TRAIN_TIMETABLE);
    public static final List<String> BUS_STATIONS = getStations(BUS_TIMETABLE);

    private RoutePlanner() {}

    public static List<Route> generateTimeTable(List<Node> first, long intervalMinutes) {
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(first));
        while (true) {
            List<Node> latest = routes.get(routes.size() - 1).getStops();
            LocalTime departure = latest.get(0).getDeparture();
            if (departure.plusMinutes(intervalMinutes).isAfter(LocalTime.of(21, 45))) { // departure.plusMinutes(intervalMinutes).isBefore(departure)
                break;
            }
            List<Node> nodes = new ArrayList<>();
            for (Node node : latest) {
                nodes.add(new Node(
                        node.getStation(),
                        node.getDeparture().plusMinutes(intervalMinutes),
                        node.getPlatform()));
            }
            routes.add(new Route(nodes));
        }
        return routes;
    }

    public static List<String> getStations(List<Route> timetable) {
        return timetable.stream()
                .flatMap(route -> route.getStops().stream())
                .map(Node::getStation)
                .distinct()
                .sorted()
                .toList();
    }

    public static List<String> getDestinations(List<Route> timetable, String location) {
        return timetable.stream()
                .map(route -> route.getStops().stream()
                        .map(Node::getStation)
                        .toList())
                .filter(stations -> stations.contains(location))
                .flatMap(Collection::stream)
                .distinct()
                .filter(station -> !station.equals(location))
                .sorted()
                .toList();
    }

    public static Optional<Journey> planJourney(List<Route> timetable, String location, String destination, LocalTime departure) {
        Route best = null;
        long shortestTravelTime = 99999999;
        for (Route route : timetable) {
            List<Node> nodes = route.getStops();
            boolean foundSource = false;
            for (Node node : nodes) {
                String station = node.getStation();
                if (destination.equals(station)) {
                    long travelTime = Math.abs(Duration.between(departure, node.getDeparture()).toMinutes());
                    if (foundSource && (travelTime < shortestTravelTime)) {
                        best = route;
                        shortestTravelTime = travelTime;
                    }
                    break;
                }
                if (location.equals(station)) {
                    foundSource = true;
                    if (departure.isAfter(node.getDeparture())) {
                        break;
                    }
                }
            }
        }
        if (best != null) {
            List<Node> trimmedNodes = new ArrayList<>();
            boolean passedStart = false;
            for (Node node : best.getStops()) {
                if (node.getStation().equals(location)) {
                    passedStart = true;
                }
                if (passedStart) {
                    trimmedNodes.add(node);
                }
                if (node.getStation().equals(destination)) {
                    break;
                }
            }
            long travelTime = Math.abs(Duration.between(trimmedNodes.get(0).getDeparture(),
                    trimmedNodes.get(trimmedNodes.size() - 1).getDeparture()).toMinutes());
            LocalTime actualDeparture = trimmedNodes.get(0).getDeparture();
            LocalTime arrival = trimmedNodes.get(trimmedNodes.size() - 1).getDeparture();
            String platform = trimmedNodes.get(0).getPlatform();
            String arrivalPlatform = trimmedNodes.get(trimmedNodes.size() - 1).getPlatform();
            return Optional.of(new Journey(actualDeparture, arrival, platform, arrivalPlatform, travelTime, trimmedNodes));
        }
        return Optional.empty();
    }

    private static List<Route> generateTrainRoutes() {
        List<Route> routes = new ArrayList<>();
        // treinserie 1100 - traject tussen Eindhoven Centraal en Den Haag Central
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Eindhoven Centraal", LocalTime.of(5, 14), "2"),
                new Node("Tilburg", LocalTime.of(5, 39), "2"),
                new Node("Breda", LocalTime.of(5, 53), "7"),
                new Node("Rotterdam Centraal", LocalTime.of(6, 18), "12"),
                new Node("Delft", LocalTime.of(6, 29), "1"),
                new Node("Den Haag HS", LocalTime.of(6, 38), "5"),
                new Node("Den Haag Centraal", LocalTime.of(6, 41), "2")
        ), 30));
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Den Haag Centraal", LocalTime.of(5, 49), "3"),
                new Node("Den Haag HS", LocalTime.of(5, 54), "3"),
                new Node("Delft", LocalTime.of(6, 1), "2"),
                new Node("Rotterdam Centraal", LocalTime.of(6, 14), "6"),
                new Node("Breda", LocalTime.of(6, 38), "3"),
                new Node("Tilburg", LocalTime.of(6, 54), "1"),
                new Node("Eindhoven Centraal", LocalTime.of(7, 16), "1")
        ), 30));
        // treinserie 1500 - traject tussen Deventer en Amsterdam Centraal
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Deventer", LocalTime.of(6, 48), "1"),
                new Node("Apeldoorn", LocalTime.of(7, 0), "1"),
                new Node("Amersfoort Centraal", LocalTime.of(7, 26), "7"),
                new Node("Hilversum", LocalTime.of(7, 40), "5"),
                new Node("Amsterdam Centraal", LocalTime.of(8, 0), "13b")
        ), 30));
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Amsterdam Centraal", LocalTime.of(6, 30), "11b"),
                new Node("Hilversum", LocalTime.of(6, 53), "3"),
                new Node("Amersfoort Centraal", LocalTime.of(7, 6), "1"),
                new Node("Apeldoorn", LocalTime.of(7, 32), "4"),
                new Node("Deventer", LocalTime.of(7, 42), "1")
        ), 30));
        // treinserie 1600 - traject tussen Enschede en Schiphol Airport
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Enschede", LocalTime.of(5, 16), "1"),
                new Node("Hengelo", LocalTime.of(5, 24), "2a"),
                new Node("Almelo", LocalTime.of(5, 36), "4b"),
                new Node("Deventer", LocalTime.of(6, 3), "3"),
                new Node("Apeldoorn", LocalTime.of(6, 14), "1"),
                new Node("Amersfoort Centraal", LocalTime.of(6, 41), "7"),
                new Node("Hilversum", LocalTime.of(6, 54), "5"),
                new Node("Duivendrecht", LocalTime.of(7, 10), "4"),
                new Node("Amsterdam Zuid", LocalTime.of(7, 16), "3"),
                new Node("Schiphol Airport", LocalTime.of(7, 22), "4")
        ), 60));
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Schiphol Airport", LocalTime.of(5, 38), "3"),
                new Node("Amsterdam Zuid", LocalTime.of(5, 45), "2"),
                new Node("Duivendrecht", LocalTime.of(5, 50), "1"),
                new Node("Hilversum", LocalTime.of(6, 7), "2"),
                new Node("Amersfoort Centraal", LocalTime.of(6, 22), "1"),
                new Node("Apeldoorn", LocalTime.of(6, 47), "4"),
                new Node("Deventer", LocalTime.of(7, 1), "3"),
                new Node("Almelo", LocalTime.of(7, 25), "2b"),
                new Node("Hengelo", LocalTime.of(7, 37), "3"),
                new Node("Enschede", LocalTime.of(7, 44), "1")
        ), 60));
        // treinserie 1700 - traject tussen Enschede en Den Haag Centraal
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Enschede", LocalTime.of(4, 46), "1"),
                new Node("Hengelo", LocalTime.of(4, 54), "3a"),
                new Node("Almelo", LocalTime.of(5, 6), "4b"),
                new Node("Deventer", LocalTime.of(5, 33), "4"),
                new Node("Apeldoorn", LocalTime.of(5, 44), "1"),
                new Node("Amersfoort Centraal", LocalTime.of(6, 9), "6"),
                new Node("Utrecht Centraal", LocalTime.of(6, 28), "8"),
                new Node("Gouda", LocalTime.of(6, 47), "8"),
                new Node("Den Haag Centraal", LocalTime.of(7, 6), "5")
        ), 60));
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Den Haag Centraal", LocalTime.of(5, 55), "5"),
                new Node("Gouda", LocalTime.of(6, 14), "8"),
                new Node("Utrecht Centraal", LocalTime.of(6, 37), "8"),
                new Node("Amersfoort Centraal", LocalTime.of(6, 52), "6"),
                new Node("Apeldoorn", LocalTime.of(7, 17), "1"),
                new Node("Deventer", LocalTime.of(7, 31), "4"),
                new Node("Almelo", LocalTime.of(7, 55), "4b"),
                new Node("Hengelo", LocalTime.of(8, 7), "3a"),
                new Node("Enschede", LocalTime.of(8, 14), "1")
        ), 60));
        // treinserie 1800 - traject tussen Leeuwarden en Den Haag Centraal
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Leeuwarden", LocalTime.of(5, 6), "3"),
                new Node("Heerenveen", LocalTime.of(5, 27), "3"),
                new Node("Steenwijk", LocalTime.of(5, 46), "2"),
                new Node("Meppel", LocalTime.of(5, 56), "2"),
                new Node("Zwolle", LocalTime.of(6, 17), "3"),
                new Node("Lelystad Centrum", LocalTime.of(6, 43), "1"),
                new Node("Almere Centrum", LocalTime.of(6, 58), "1"),
                new Node("Amsterdam Zuid", LocalTime.of(7, 21), "4"),
                new Node("Schiphol Airport", LocalTime.of(7, 28), "5-6"),
                new Node("Leiden Centraal", LocalTime.of(7, 45), "9b"),
                new Node("Den Haag Centraal", LocalTime.of(7, 56), "9")
        ), 60));
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Den Haag Centraal", LocalTime.of(6, 3), "9"),
                new Node("Leiden Centraal", LocalTime.of(6, 17), "4b"),
                new Node("Schiphol Airport", LocalTime.of(6, 34), "4"),
                new Node("Amsterdam Zuid", LocalTime.of(6, 42), "2"),
                new Node("Almere Centrum", LocalTime.of(7, 2), "4"),
                new Node("Lelystad Centrum", LocalTime.of(7, 17), "3"),
                new Node("Zwolle", LocalTime.of(7, 48), "7"),
                new Node("Meppel", LocalTime.of(8, 4), "3"),
                new Node("Steenwijk", LocalTime.of(8, 13), "3"),
                new Node("Heerenveen", LocalTime.of(8, 26), "2"),
                new Node("Leeuwarden", LocalTime.of(8, 43), "5")
        ), 60));
        return routes;
    }

    private static List<Route> generateBusRoutes() {
        List<Route> routes = new ArrayList<>();
        // Buslijn 156 tussen 's-Hertogenbosch en Eindhoven
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("'s-Hertogenbosch", LocalTime.of(5, 40), ""),
                new Node("Den Dungen", LocalTime.of(6, 0), ""),
                new Node("Maaskantje", LocalTime.of(6, 10), ""),
                new Node("Sint-Michielsgestel", LocalTime.of(6, 15), ""),
                new Node("Schijndel", LocalTime.of(6, 45), ""),
                new Node("Sint-Oedenrode", LocalTime.of(7, 0), ""),
                new Node("Nijnsel", LocalTime.of(7, 15), ""),
                new Node("Son", LocalTime.of(7, 20), ""),
                new Node("Eindhoven", LocalTime.of(7, 30), "")
        ), 60));
        routes.addAll(generateTimeTable(Arrays.asList(
                new Node("Eindhoven", LocalTime.of(5, 36), ""),
                new Node("Son", LocalTime.of(5, 46), ""),
                new Node("Nijnsel", LocalTime.of(5, 51), ""),
                new Node("Sint-Oedenrode", LocalTime.of(6, 6), ""),
                new Node("Schijndel", LocalTime.of(6, 21), ""),
                new Node("Sint-Michielsgestel", LocalTime.of(6, 42), ""),
                new Node("Maaskantje", LocalTime.of(6, 56), ""),
                new Node("Den Dungen", LocalTime.of(7, 6), ""),
                new Node("'s-Hertogenbosch", LocalTime.of(7, 26), "")
        ), 60));
        return routes;
    }

    public static String planRoute(String source, String destination, LocalTime departure) {
        Route best = null;
        long shortestTravelTime = 99999999;
        for (Route route : TRAIN_TIMETABLE) {
            List<Node> nodes = route.getStops();
            boolean foundSource = false;
            for (Node node : nodes) {
                String station = node.getStation();
                if (destination.equals(station)) {
                    long travelTime = Math.abs(Duration.between(departure, node.getDeparture()).toMinutes());
                    if (foundSource && (travelTime < shortestTravelTime)) {
                        best = route;
                        shortestTravelTime = travelTime;
                    }
                    break;
                }
                if (source.equals(station)) {
                    foundSource = true;
                    if (departure.isAfter(node.getDeparture())) {
                        break;
                    }
                }
            }
        }
        // String result = String.format("planRoute(%s, %s, %s)\n", source, destination, departure);
        String result = "";
        if (best != null) {
            List<Node> trimmedNodes = new ArrayList<>();
            boolean passedStart = false;
            for (Node node : best.getStops()) {
                if (node.getStation().equals(source)) {
                    passedStart = true;
                }
                if (passedStart) {
                    trimmedNodes.add(node);
                }
                if (node.getStation().equals(destination)) {
                    break;
                }
            }
            for (Node n : trimmedNodes) {
                result += String.format("%s | %s | spoor %s \n", n.getStation(), n.getDeparture(), n.getPlatform());
            }
            long mins = Math.abs(Duration.between(trimmedNodes.get(0).getDeparture(),
                    trimmedNodes.get(trimmedNodes.size() - 1).getDeparture()).toMinutes());
            result += String.format("totale reistijd = %d minuten", mins);
        } else {
            result += "NO ROUTE";
        }
        return result;
    }

    public static List<String> getStations() {
        return TRAIN_TIMETABLE.stream()
                .flatMap(route -> route.getStops().stream())
                .map(Node::getStation)
                .distinct()
                .toList();
    }

    public static List<String> getDestinations(String location) {
        return TRAIN_TIMETABLE.stream()
                .map(route -> route.getStops().stream()
                        .map(Node::getStation)
                        .toList())
                .filter(stations -> stations.contains(location))
                .flatMap(Collection::stream)
                .distinct()
                .filter(station -> !station.equals(location))
                .toList();
    }
}
