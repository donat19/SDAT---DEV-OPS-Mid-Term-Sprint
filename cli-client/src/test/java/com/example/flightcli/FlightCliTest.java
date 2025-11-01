package com.example.flightcli;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.flightcli.cli.FlightCli;
import com.example.flightcli.model.CityAirportsView;
import com.example.flightcli.service.FlightApiClient;

class FlightCliTest {

    @Test
    void printsAirportsByCity() throws Exception {
        FlightApiClient client = mock(FlightApiClient.class);
        when(client.fetchAirportsByCity()).thenReturn(List.of(
            new CityAirportsView(1L, "Seattle", List.of("SEA", "BFI"))
        ));
        when(client.fetchAircraftByPassenger()).thenReturn(List.of());
        when(client.fetchAirportsByAircraft()).thenReturn(List.of());
        when(client.fetchAirportsUsedByPassengers()).thenReturn(List.of());

        String input = "1\nq\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FlightCli cli = new FlightCli(client, scanner, new PrintStream(output, true, StandardCharsets.UTF_8));

        cli.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Seattle"));
    }
}
