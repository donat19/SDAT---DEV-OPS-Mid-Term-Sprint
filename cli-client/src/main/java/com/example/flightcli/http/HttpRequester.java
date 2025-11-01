package com.example.flightcli.http;

import java.io.IOException;

public interface HttpRequester {

    String get(String path) throws IOException, InterruptedException;
}
