package com.example.flightapi.controller;

import java.util.List;

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

import com.example.flightapi.model.Airport;
import com.example.flightapi.model.City;
import com.example.flightapi.repository.AirportRepository;
import com.example.flightapi.repository.CityRepository;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirportController(AirportRepository airportRepository, CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @GetMapping("/{id}")
    public Airport findOne(@PathVariable Long id) {
        return airportRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Airport create(@RequestBody Airport request) {
        attachCity(request);
        return airportRepository.save(request);
    }

    @PutMapping("/{id}")
    public Airport update(@PathVariable Long id, @RequestBody Airport request) {
        Airport airport = airportRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found"));
        airport.setName(request.getName());
        airport.setCode(request.getCode());
        if (request.getCity() != null) {
            attachCity(request);
            airport.setCity(request.getCity());
        }
        return airportRepository.save(airport);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!airportRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found");
        }
        airportRepository.deleteById(id);
    }

    private void attachCity(Airport airport) {
        City city = airport.getCity();
        if (city != null && city.getId() != null) {
            City managed = cityRepository.findById(city.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found"));
            airport.setCity(managed);
        }
    }
}
