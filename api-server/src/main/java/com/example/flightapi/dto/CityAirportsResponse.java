package com.example.flightapi.dto;

import java.util.List;

public record CityAirportsResponse(Long cityId, String cityName, List<String> airports) {
}
