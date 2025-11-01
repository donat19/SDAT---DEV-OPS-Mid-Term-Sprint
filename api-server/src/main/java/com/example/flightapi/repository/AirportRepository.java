package com.example.flightapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flightapi.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
