package com.example.flightapi.dto;

import java.util.List;

public record AircraftAirportsResponse(Long aircraftId, String aircraftType, List<String> airportCodes) {
}
