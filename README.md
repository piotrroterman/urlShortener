# URL Shortener

---

A Spring Boot application that shortens long URLs into compact, auto-generated UUID short codes. Provides a REST API, automatic redirects, scheduled cleanup of expired links, and a simple Thymeleaf web UI.

## Features

---

- Generate a short link for any URL (UUID-based)
- Instant redirect from `/`+short code to the original link
- CRUD via REST: create, view info, list all, delete
- Thymeleaf web interface  `/`
- Scheduled cleanup job deletes links older than 1 day (runs every 12h)
- Manual cleanup endpoint `DELETE /deleteOutdated`
- Exception handlers returning proper 404 / 400 responses
- Database schema created and versioned with Flyway, validated by Hibernate (`ddl-auto=validate`)
- Time logic driven by an injectable `Clock` bean, enabling deterministic integration tests (auditing + cleanup)
- Integration tests running simultaneously against PostgreSQL via TestContainers using MockMvc

## Tech stack

---

| Area | Technology |
|------|------------|
| Language | Java 25 |
| Framework | Spring Boot 4.1.1 |
| Web | Spring MVC, Thymeleaf |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL (+ Flyway migrations) |
| Testing | JUnit 5, MockMvc, TestContainers, Mockito |

Other dependencies: Lombok.

## API reference

---

| Method | Path | Description | Response |
|--------|------|-------------|----------|
| `POST` | `/saveLink` | Create a short link (JSON body with `originalLink`) | `201` + Link |
| `GET` | `/info/{id}` | Get info about a short link | `200` / `404` |
| `GET` | `/{id}` | Redirect to the original link | Redirect |
| `GET` | `/all` | List all short links | `200` |
| `DELETE` | `/{id}` | Delete a short link | `200` / `404` |
| `DELETE` | `/deleteOutdated` | Delete links older than the cleanup threshold | `200` |

Example request:

```bash
curl -X POST http://localhost:8080/saveLink \
  -H "Content-Type: application/json" \
  -d '{"originalLink":"https://example.com/very/long/url"}'
```


## Tests

```bash
mvn test
```

Tests use TestContainers to spin up a `postgres:16-alpine` container, so Docker
must be running. Integration tests mock the `Clock` bean to test time-sensitive
behavior (cleanup of outdated links) deterministically.
