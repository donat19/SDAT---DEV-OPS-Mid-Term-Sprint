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

import com.example.flightapi.dto.PassengerAircraftResponse;
import com.example.flightapi.dto.PassengerAirportsResponse;
import com.example.flightapi.model.Aircraft;
import com.example.flightapi.model.City;
import com.example.flightapi.model.Passenger;
import com.example.flightapi.repository.AircraftRepository;
import com.example.flightapi.repository.CityRepository;
import com.example.flightapi.repository.PassengerRepository;
import com.example.flightapi.service.FlightQueryService;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerRepository passengerRepository;
    private final CityRepository cityRepository;
    private final AircraftRepository aircraftRepository;
    private final FlightQueryService flightQueryService;

    public PassengerController(PassengerRepository passengerRepository,
                               CityRepository cityRepository,
                               AircraftRepository aircraftRepository,
                               FlightQueryService flightQueryService) {
        this.passengerRepository = passengerRepository;
        this.cityRepository = cityRepository;
        this.aircraftRepository = aircraftRepository;
        this.flightQueryService = flightQueryService;
    }

    @GetMapping
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Passenger findOne(@PathVariable Long id) {
        return passengerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Passenger create(@RequestBody Passenger request) {
        attachCity(request);
        attachAircraft(request);
        return passengerRepository.save(request);
    }

    @PutMapping("/{id}")
    public Passenger update(@PathVariable Long id, @RequestBody Passenger request) {
        Passenger passenger = passengerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found"));
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setPhoneNumber(request.getPhoneNumber());
        if (request.getCity() != null) {
            attachCity(request);
            passenger.setCity(request.getCity());
        }
        if (request.getAircraft() != null) {
            attachAircraft(request);
            passenger.setAircraft(request.getAircraft());
        }
        return passengerRepository.save(passenger);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found");
        }
        passengerRepository.deleteById(id);
    }

    @GetMapping("/aircraft")
    public List<PassengerAircraftResponse> aircraftByPassenger() {
        return flightQueryService.getAircraftByPassenger();
    }

    @GetMapping("/airports")
    public List<PassengerAirportsResponse> airportsUsedByPassengers() {
        return flightQueryService.getAirportsUsedByPassengers();
    }

    private void attachCity(Passenger passenger) {
        City city = passenger.getCity();
        if (city != null && city.getId() != null) {
            City managed = cityRepository.findById(city.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found"));
            passenger.setCity(managed);
        }
    }

    private void attachAircraft(Passenger passenger) {
        Set<Aircraft> aircraft = passenger.getAircraft();
        if (aircraft != null && !aircraft.isEmpty()) {
            Set<Aircraft> managed = aircraft.stream()
                .map(plane -> aircraftRepository.findById(plane.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aircraft not found")))
                .collect(Collectors.toSet());
            passenger.setAircraft(managed);
        }
    }
}
