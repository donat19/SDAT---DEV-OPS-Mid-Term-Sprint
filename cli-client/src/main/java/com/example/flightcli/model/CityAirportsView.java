package com.example.flightcli.model;

import java.util.List;

public record CityAirportsView(Long cityId, String cityName, List<String> airports) {
}
