# Flight CLI

Simple Java CLI that consumes the Sprint Week Flight API using HTTP `GET` requests. It answers the four required questions directly from the menu.

## Running the CLI

1. Ensure the API server is running (default `http://localhost:8080`).
2. Build and run the CLI:
   ```bash
   mvn package
   java -cp target/flight-cli-0.0.1-SNAPSHOT.jar com.example.flightcli.FlightCliApplication
   ```
3. Pass a different base URL if needed:
   ```bash
   java -cp target/flight-cli-0.0.1-SNAPSHOT.jar com.example.flightcli.FlightCliApplication http://localhost:9090
   ```

Pick an option (1-4) from the menu to print the answers.

## Tests and CI

Run the tests locally with `mvn test`. GitHub Actions (`.github/workflows/ci.yml`) runs `mvn verify` automatically on pushes and pull requests targeting `main`.
