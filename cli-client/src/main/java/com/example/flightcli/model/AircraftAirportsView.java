package com.example.flightcli.model;

import java.util.List;

public record AircraftAirportsView(Long aircraftId, String aircraftType, List<String> airportCodes) {
}
