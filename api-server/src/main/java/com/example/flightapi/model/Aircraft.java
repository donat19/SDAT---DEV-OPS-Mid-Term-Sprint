package com.example.flightapi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String airlineName;

    private Integer numberOfPassengers;

    @ManyToMany(mappedBy = "aircraft")
    @JsonBackReference("passenger-aircraft")
    private Set<Passenger> passengers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "aircraft_airports",
        joinColumns = @JoinColumn(name = "aircraft_id"),
        inverseJoinColumns = @JoinColumn(name = "airport_id"))
    @JsonManagedReference("aircraft-airports")
    private Set<Airport> airports = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Set<Airport> getAirports() {
        return airports;
    }

    public void setAirports(Set<Airport> airports) {
        this.airports = airports;
    }
}
