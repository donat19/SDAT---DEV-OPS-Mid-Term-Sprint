package com.example.flightcli.cli;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import com.example.flightcli.model.AircraftAirportsView;
import com.example.flightcli.model.CityAirportsView;
import com.example.flightcli.model.PassengerAircraftView;
import com.example.flightcli.model.PassengerAirportsView;
import com.example.flightcli.service.FlightApiClient;

public class FlightCli {

    private final FlightApiClient client;
    private final Scanner scanner;
    private final PrintStream out;

    public FlightCli(FlightApiClient client, Scanner scanner, PrintStream out) {
        this.client = client;
        this.scanner = scanner;
        this.out = out;
    }

    public void run() {
        out.println("==== Flight Connections CLI ====");
        out.println("Type 1-4 to pick a question or q to quit.");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "1" -> showAirportsByCity();
                case "2" -> showAircraftByPassenger();
                case "3" -> showAirportsByAircraft();
                case "4" -> showAirportsUsedByPassengers();
                case "q" -> running = false;
                default -> out.println("Please choose 1-4 or q.");
            }
        }
        out.println("Goodbye!");
    }

    private void printMenu() {
        out.println();
        out.println("1) Airports in each city");
        out.println("2) Aircraft flown by each passenger");
        out.println("3) Airports used by each aircraft");
        out.println("4) Airports used by passengers");
        out.print("Your choice: ");
    }

    private void showAirportsByCity() {
        try {
            List<CityAirportsView> rows = client.fetchAirportsByCity();
            if (rows.isEmpty()) {
                out.println("No cities found.");
                return;
            }
            rows.forEach(row -> out.printf("%s: %s%n", row.cityName(), String.join(", ", row.airports())));
        } catch (IOException | InterruptedException e) {
            out.println("Could not load data: " + e.getMessage());
        }
    }

    private void showAircraftByPassenger() {
        try {
            List<PassengerAircraftView> rows = client.fetchAircraftByPassenger();
            if (rows.isEmpty()) {
                out.println("No passengers found.");
                return;
            }
            rows.forEach(row -> out.printf("%s: %s%n", row.passengerName(), String.join(", ", row.aircraftTypes())));
        } catch (IOException | InterruptedException e) {
            out.println("Could not load data: " + e.getMessage());
        }
    }

    private void showAirportsByAircraft() {
        try {
            List<AircraftAirportsView> rows = client.fetchAirportsByAircraft();
            if (rows.isEmpty()) {
                out.println("No aircraft found.");
                return;
            }
            rows.forEach(row -> out.printf("%s: %s%n", row.aircraftType(), String.join(", ", row.airportCodes())));
        } catch (IOException | InterruptedException e) {
            out.println("Could not load data: " + e.getMessage());
        }
    }

    private void showAirportsUsedByPassengers() {
        try {
            List<PassengerAirportsView> rows = client.fetchAirportsUsedByPassengers();
            if (rows.isEmpty()) {
                out.println("No passengers found.");
                return;
            }
            rows.forEach(row -> out.printf("%s: %s%n", row.passengerName(), String.join(", ", row.airportCodes())));
        } catch (IOException | InterruptedException e) {
            out.println("Could not load data: " + e.getMessage());
        }
    }
}
