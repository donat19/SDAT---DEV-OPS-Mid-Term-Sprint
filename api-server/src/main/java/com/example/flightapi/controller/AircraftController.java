package com.example.flightapi.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.flightapi.dto.AircraftAirportsResponse;
import com.example.flightapi.model.Aircraft;
import com.example.flightapi.model.Airport;
import com.example.flightapi.repository.AircraftRepository;
import com.example.flightapi.repository.AirportRepository;
import com.example.flightapi.service.FlightQueryService;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final FlightQueryService flightQueryService;

    public AircraftController(AircraftRepository aircraftRepository,
                              AirportRepository airportRepository,
                              FlightQueryService flightQueryService) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.flightQueryService = flightQueryService;
    }

    @GetMapping
    public List<Aircraft> findAll() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aircraft findOne(@PathVariable Long id) {
        return aircraftRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aircraft create(@RequestBody Aircraft request) {
        attachAirports(request);
        return aircraftRepository.save(request);
    }

    @PutMapping("/{id}")
    public Aircraft update(@PathVariable Long id, @RequestBody Aircraft request) {
        Aircraft aircraft = aircraftRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found"));
        aircraft.setType(request.getType());
        aircraft.setAirlineName(request.getAirlineName());
        aircraft.setNumberOfPassengers(request.getNumberOfPassengers());
        if (request.getAirports() != null) {
            attachAirports(request);
            aircraft.setAirports(request.getAirports());
        }
        return aircraftRepository.save(aircraft);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found");
        }
        aircraftRepository.deleteById(id);
    }

    @GetMapping("/airports")
    public List<AircraftAirportsResponse> airportsForAircraft() {
        return flightQueryService.getAirportsByAircraft();
    }

    private void attachAirports(Aircraft aircraft) {
        Set<Airport> airports = aircraft.getAirports();
        if (airports != null && !airports.isEmpty()) {
            Set<Airport> managed = airports.stream()
                .map(port -> airportRepository.findById(port.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airport not found")))
                .collect(Collectors.toSet());
            aircraft.setAirports(managed);
        }
    }
}
