package com.example.flightapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flightapi.model.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
