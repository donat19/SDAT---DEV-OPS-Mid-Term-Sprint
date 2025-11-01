package com.example.flightcli.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.example.flightcli.http.HttpRequester;
import com.example.flightcli.model.AircraftAirportsView;
import com.example.flightcli.model.CityAirportsView;
import com.example.flightcli.model.PassengerAircraftView;
import com.example.flightcli.model.PassengerAirportsView;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FlightApiClient {

    private final String baseUrl;
    private final HttpRequester requester;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FlightApiClient(String baseUrl, HttpRequester requester) {
        this.baseUrl = normalizeBaseUrl(baseUrl);
        this.requester = requester;
    }

    public List<CityAirportsView> fetchAirportsByCity() throws IOException, InterruptedException {
        return fetchList("/cities/airports", CityAirportsView[].class);
    }

    public List<PassengerAircraftView> fetchAircraftByPassenger() throws IOException, InterruptedException {
        return fetchList("/passengers/aircraft", PassengerAircraftView[].class);
    }

    public List<AircraftAirportsView> fetchAirportsByAircraft() throws IOException, InterruptedException {
        return fetchList("/aircraft/airports", AircraftAirportsView[].class);
    }

    public List<PassengerAirportsView> fetchAirportsUsedByPassengers() throws IOException, InterruptedException {
        return fetchList("/passengers/airports", PassengerAirportsView[].class);
    }

    private <T> List<T> fetchList(String path, Class<T[]> type) throws IOException, InterruptedException {
        String response = requester.get(baseUrl + path);
        T[] data = objectMapper.readValue(response, type);
        return Arrays.asList(data);
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            return baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl;
    }
}
