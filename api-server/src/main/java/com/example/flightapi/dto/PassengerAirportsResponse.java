package com.example.flightapi.dto;

import java.util.List;

public record PassengerAirportsResponse(Long passengerId, String passengerName, List<String> airportCodes) {
}
