package com.example.flightapi.config;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.flightapi.model.Aircraft;
import com.example.flightapi.model.Airport;
import com.example.flightapi.model.City;
import com.example.flightapi.model.Passenger;
import com.example.flightapi.repository.AircraftRepository;
import com.example.flightapi.repository.CityRepository;
import com.example.flightapi.repository.PassengerRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleData(CityRepository cityRepository,
                                     AircraftRepository aircraftRepository,
                                     PassengerRepository passengerRepository) {
        return args -> {
            if (cityRepository.count() > 0) {
                return;
            }

            City seattle = new City();
            seattle.setName("Seattle");
            seattle.setState("WA");
            seattle.setPopulation(733919L);

            City newYork = new City();
            newYork.setName("New York");
            newYork.setState("NY");
            newYork.setPopulation(8467513L);

            City chicago = new City();
            chicago.setName("Chicago");
            chicago.setState("IL");
            chicago.setPopulation(2679000L);

            Airport sea = new Airport();
            sea.setName("Seattle-Tacoma International");
            sea.setCode("SEA");
            sea.setCity(seattle);
            seattle.getAirports().add(sea);

            Airport bfi = new Airport();
            bfi.setName("Boeing Field");
            bfi.setCode("BFI");
            bfi.setCity(seattle);
            seattle.getAirports().add(bfi);

            Airport jfk = new Airport();
            jfk.setName("John F. Kennedy International");
            jfk.setCode("JFK");
            jfk.setCity(newYork);
            newYork.getAirports().add(jfk);

            Airport lga = new Airport();
            lga.setName("LaGuardia");
            lga.setCode("LGA");
            lga.setCity(newYork);
            newYork.getAirports().add(lga);

            Airport ord = new Airport();
            ord.setName("O'Hare International");
            ord.setCode("ORD");
            ord.setCity(chicago);
            chicago.getAirports().add(ord);

            cityRepository.saveAll(List.of(seattle, newYork, chicago));

            Aircraft boeing737 = new Aircraft();
            boeing737.setType("Boeing 737");
            boeing737.setAirlineName("Alaska Airlines");
            boeing737.setNumberOfPassengers(180);
            boeing737.setAirports(Set.of(sea, jfk));

            Aircraft airbusA320 = new Aircraft();
            airbusA320.setType("Airbus A320");
            airbusA320.setAirlineName("Delta");
            airbusA320.setNumberOfPassengers(190);
            airbusA320.setAirports(Set.of(lga, ord));

            Aircraft embraer175 = new Aircraft();
            embraer175.setType("Embraer 175");
            embraer175.setAirlineName("SkyWest");
            embraer175.setNumberOfPassengers(76);
            embraer175.setAirports(Set.of(bfi, ord));

            aircraftRepository.saveAll(List.of(boeing737, airbusA320, embraer175));

            Passenger alex = new Passenger();
            alex.setFirstName("Alex");
            alex.setLastName("Chen");
            alex.setPhoneNumber("206-555-0100");
            alex.setCity(seattle);
            alex.getAircraft().addAll(Set.of(boeing737, embraer175));

            Passenger jordan = new Passenger();
            jordan.setFirstName("Jordan");
            jordan.setLastName("Diaz");
            jordan.setPhoneNumber("212-555-0145");
            jordan.setCity(newYork);
            jordan.getAircraft().addAll(Set.of(boeing737, airbusA320));

            Passenger maya = new Passenger();
            maya.setFirstName("Maya");
            maya.setLastName("Singh");
            maya.setPhoneNumber("312-555-0199");
            maya.setCity(chicago);
            maya.getAircraft().addAll(Set.of(airbusA320));

            passengerRepository.saveAll(List.of(alex, jordan, maya));
        };
    }
}
