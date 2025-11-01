package com.example.flightapi;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.flightapi.service.FlightQueryService;

@SpringBootTest
@ActiveProfiles("test")
class FlightQueryServiceTest {

    @Autowired
    private FlightQueryService flightQueryService;

    @Test
    void airportsGroupedByCity() {
        var results = flightQueryService.getAirportsByCity();
        assertThat(results).isNotEmpty();
        assertThat(results.stream()
            .filter(row -> "Seattle".equals(row.cityName()))
            .findFirst()
            .orElseThrow()
            .airports()).contains("Seattle-Tacoma International", "Boeing Field");
    }

    @Test
    void passengersHaveAircraft() {
        var results = flightQueryService.getAircraftByPassenger();
        assertThat(results).anyMatch(row -> row.passengerName().equals("Alex Chen") && row.aircraftTypes().contains("Embraer 175"));
    }

    @Test
    void passengersHaveUsedAirports() {
        var results = flightQueryService.getAirportsUsedByPassengers();
        assertThat(results).anyMatch(row -> row.passengerName().equals("Jordan Diaz") && row.airportCodes().contains("JFK"));
    }
}
