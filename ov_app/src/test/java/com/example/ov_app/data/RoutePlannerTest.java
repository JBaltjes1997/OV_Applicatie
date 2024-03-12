package com.example.ov_app.data;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoutePlannerTest {

    @Test
    void planRoute() {
        String knownResult = """
                Deventer | 11:18 | spoor 1\s
                Apeldoorn | 11:30 | spoor 1\s
                Amersfoort Centraal | 11:56 | spoor 7\s
                totale reistijd = 38 minuten""";
        assertEquals(knownResult, RoutePlanner.planRoute("Deventer", "Amersfoort Centraal", LocalTime.of(11, 5)));
    }

    @Test
    void getDestinations() {
        List<String> knownDestinations = List.of("Apeldoorn", "Amersfoort Centraal", "Enschede");
        assertTrue(RoutePlanner.getDestinations("Deventer").containsAll(knownDestinations));
    }
}