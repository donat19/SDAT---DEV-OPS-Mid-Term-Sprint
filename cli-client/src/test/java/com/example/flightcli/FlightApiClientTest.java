package com.example.flightcli;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.flightcli.http.HttpRequester;
import com.example.flightcli.service.FlightApiClient;

@ExtendWith(MockitoExtension.class)
class FlightApiClientTest {

    @Mock
    private HttpRequester requester;

    private FlightApiClient client;

    @BeforeEach
    void setUp() {
        client = new FlightApiClient("http://localhost:8080/", requester);
    }

    @Test
    void fetchAirportsByCityParsesResponse() throws Exception {
        String payload = "[{\"cityId\":1,\"cityName\":\"Seattle\",\"airports\":[\"SEA\",\"BFI\"]}]";
        when(requester.get("http://localhost:8080/cities/airports")).thenReturn(payload);

        List<?> result = client.fetchAirportsByCity();

        assertEquals(1, result.size());
    }

    @Test
    void trailingSlashRemovedOnce() throws Exception {
        String payload = "[]";
        when(requester.get("http://localhost:8080/passengers/aircraft")).thenReturn(payload);

        assertTrue(client.fetchAircraftByPassenger().isEmpty());
    }
}
