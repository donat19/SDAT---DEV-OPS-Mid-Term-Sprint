package com.example.flightcli.model;

import java.util.List;

public record PassengerAircraftView(Long passengerId, String passengerName, List<String> aircraftTypes) {
}
