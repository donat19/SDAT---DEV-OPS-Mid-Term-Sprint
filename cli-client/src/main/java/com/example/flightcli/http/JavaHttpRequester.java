package com.example.flightcli.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JavaHttpRequester implements HttpRequester {

    private final HttpClient client;

    public JavaHttpRequester() {
        this(HttpClient.newHttpClient());
    }

    public JavaHttpRequester(HttpClient client) {
        this.client = client;
    }

    @Override
    public String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .header("Accept", "application/json")
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        }
        throw new IOException("Request failed with status " + response.statusCode());
    }
}
