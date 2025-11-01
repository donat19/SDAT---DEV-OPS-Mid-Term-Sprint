package com.example.flightcli.model;

import java.util.List;

public record PassengerAirportsView(Long passengerId, String passengerName, List<String> airportCodes) {
}
