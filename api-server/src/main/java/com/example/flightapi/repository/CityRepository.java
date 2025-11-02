package com.example.flightapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flightapi.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
