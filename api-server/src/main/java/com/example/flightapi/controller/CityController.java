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

import com.example.flightapi.dto.CityAirportsResponse;
import com.example.flightapi.model.City;
import com.example.flightapi.repository.CityRepository;
import com.example.flightapi.service.FlightQueryService;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;
    private final FlightQueryService flightQueryService;

    public CityController(CityRepository cityRepository, FlightQueryService flightQueryService) {
        this.cityRepository = cityRepository;
        this.flightQueryService = flightQueryService;
    }

    @GetMapping
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public City findOne(@PathVariable Long id) {
        return cityRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City create(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody City request) {
        City city = cityRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        city.setName(request.getName());
        city.setState(request.getState());
        city.setPopulation(request.getPopulation());
        return cityRepository.save(city);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found");
        }
        cityRepository.deleteById(id);
    }

    @GetMapping("/airports")
    public List<CityAirportsResponse> airportsByCity() {
        return flightQueryService.getAirportsByCity();
    }
}
