package com.example.flightapi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.flightapi.dto.AircraftAirportsResponse;
import com.example.flightapi.dto.CityAirportsResponse;
import com.example.flightapi.dto.PassengerAircraftResponse;
import com.example.flightapi.dto.PassengerAirportsResponse;
import com.example.flightapi.model.Aircraft;
import com.example.flightapi.model.Airport;
import com.example.flightapi.repository.AircraftRepository;
import com.example.flightapi.repository.CityRepository;
import com.example.flightapi.repository.PassengerRepository;

@Service
@Transactional(readOnly = true)
public class FlightQueryService {

    private final CityRepository cityRepository;
    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;

    public FlightQueryService(CityRepository cityRepository,
                              PassengerRepository passengerRepository,
                              AircraftRepository aircraftRepository) {
        this.cityRepository = cityRepository;
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
    }

    public List<CityAirportsResponse> getAirportsByCity() {
        return cityRepository.findAll().stream()
            .map(city -> new CityAirportsResponse(
                city.getId(),
                city.getName(),
                city.getAirports().stream()
                    .map(Airport::getName)
                    .sorted()
                    .toList()
            ))
            .toList();
    }

    public List<PassengerAircraftResponse> getAircraftByPassenger() {
        return passengerRepository.findAll().stream()
            .map(passenger -> new PassengerAircraftResponse(
                passenger.getId(),
                passenger.getFirstName() + " " + passenger.getLastName(),
                passenger.getAircraft().stream()
                    .map(Aircraft::getType)
                    .sorted()
                    .toList()
            ))
            .toList();
    }

    public List<AircraftAirportsResponse> getAirportsByAircraft() {
        return aircraftRepository.findAll().stream()
            .map(aircraft -> new AircraftAirportsResponse(
                aircraft.getId(),
                aircraft.getType(),
                aircraft.getAirports().stream()
                    .map(Airport::getCode)
                    .sorted()
                    .toList()
            ))
            .toList();
    }

    public List<PassengerAirportsResponse> getAirportsUsedByPassengers() {
        return passengerRepository.findAll().stream()
            .map(passenger -> {
                Set<String> airportCodes = passenger.getAircraft().stream()
                    .flatMap(a -> a.getAirports().stream())
                    .map(Airport::getCode)
                    .collect(Collectors.toCollection(java.util.TreeSet::new));
                return new PassengerAirportsResponse(
                    passenger.getId(),
                    passenger.getFirstName() + " " + passenger.getLastName(),
                    airportCodes.stream().toList()
                );
            })
            .toList();
    }
}
