# Online Store: Product Service

A RESTful product catalog service built with Spring Boot. It started from a
Spring Boot course project (Java 11, Spring Boot 2.2), which I migrated to
**Java 17 and Spring Boot 4.1.1** and extended with improvements around
concurrency, validation, and error handling.

## Tech stack

- Java 17
- Spring Boot 4 (Spring Web, Spring Data JPA, Bean Validation)
- Hibernate
- H2 (in-memory database)
- Gradle

## Features

- Create products linked to a category
- Update product stock with a delta (positive to add, negative to remove)
- Input validation with Bean Validation and clear per-field error messages
- Optimistic locking (`@Version`) to prevent lost updates on concurrent stock changes
- Centralized error handling with `@RestControllerAdvice`
- Product status modeled as an enum

## Getting started

### Prerequisites

- JDK 17+

### Run

```bash
git clone https://github.com/HachaMan/online-store
cd online-store/product-service
./gradlew bootRun
```

The app starts on `http://localhost:8080` and loads sample data from
`data.sql` into an in-memory H2 database.

### Run tests

```bash
cd online-store/product-service
./gradlew test
```

## API

| Method | Endpoint                          | Description                  |
|--------|------------------------------------|-------------------------------------------|
| GET| `/products`| List products (optional ?categoryId=)|
| GET| `/products/{id}`| Get one product|
| GET| `/products/category/{categoryName}`| List products by category name|
| POST| `/products`| Create a product|
| PUT| `/products/{id}`| Update name, description, price, category |
| DELETE| `/products/{id}`| Soft-delete a product|
| PATCH| `/products/update-stock/{id}`| Add or remove stock|


### Create a product

```http
POST /products
Content-Type: application/json

{
  "name": "Running shoes",
  "description": "Lightweight and breathable",
  "stock": 10,
  "price": 59.99,
  "categoryId": 1
}
```

Response: `201 Created` with the created product.

### Update stock

```http
PATCH /products/update-stock/1
Content-Type: application/json

{ "quantity": -3 }
```

Response: `200 OK` with the updated product.

### Error responses

| Status | When|
|--------|----------------------------------------------------------------|
| 400    | Validation failed, or the update would leave stock below zero|
| 404    | Product not found|
| 409    | The product was modified concurrently, so re-read it and retry|

Validation errors are returned per field:

```json
{
  "name": "Name is required",
  "price": "Price must be greater than zero"
}
```

## Design notes

- **Transactions:** write operations are `@Transactional`, so multi-step changes
  commit or roll back together, and entity changes are persisted through
  Hibernate's dirty checking.
- **Optimistic locking:** `Product` has a `@Version` field. If two requests modify
  the same product at the same time, the second commit fails and the API
  returns `409 Conflict` instead of silently overwriting data.
- **Two kinds of validation:** input rules (required fields, positive price) are
  declared on the request DTO with `@Valid`. Rules that depend on current state
  (insufficient stock) live in the service layer.
- **Layering:** controllers call services, services call repositories, and DTOs
  (records) keep entities out of the API. The service throws domain exceptions
  (`ResourceNotFoundException`) and the global handler maps them to HTTP responses.

## Possible next steps

- [ ] Add more unit and integration tests
- [ ] Switch H2 to PostgreSQL with Flyway migrations
- [ ] Add API documentation (springdoc-openapi)

## Credits

Based on a Spring Boot course project https://github.com/digitallab-academy/ms-course-youtube/tree/01-REST; migrated and extended by
Javier García | https://github.com/HachaMan.