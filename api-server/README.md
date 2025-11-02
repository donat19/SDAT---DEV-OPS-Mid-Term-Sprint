# Flight API

Spring Boot REST API for the Sprint Week travel project. It exposes CRUD endpoints for cities, airports, passengers, and aircraft, and includes query routes that match the CLI requirements.

## Getting Started

1. Install Java 17 and Maven.
2. Create a MySQL database named `flight_api` and a user with privileges.
3. Update `src/main/resources/application.properties` if you choose different credentials.
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. The API runs on `http://localhost:8080` by default.

## Key Endpoints

- `GET /cities`, `POST /cities`, `PUT /cities/{id}`, `DELETE /cities/{id}`
- `GET /airports`, `POST /airports`, `PUT /airports/{id}`, `DELETE /airports/{id}`
- `GET /passengers`, `POST /passengers`, `PUT /passengers/{id}`, `DELETE /passengers/{id}`
- `GET /aircraft`, `POST /aircraft`, `PUT /aircraft/{id}`, `DELETE /aircraft/{id}`
- Aggregates for the CLI:
  - `GET /cities/airports`
  - `GET /passengers/aircraft`
  - `GET /aircraft/airports`
  - `GET /passengers/airports`

Use Postman to hit the endpoints. The project ships with sample data thanks to `DataInitializer` so you can test immediately.

## Tests

Run the unit tests with:

```bash
mvn test
```

The test profile relies on an in-memory H2 database to keep runs fast.
