package com.example.flightapi.dto;

import java.util.List;

public record PassengerAircraftResponse(Long passengerId, String passengerName, List<String> aircraftTypes) {
}
