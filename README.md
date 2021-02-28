# Spring Template

Spring template to bootstrap projects. Developed using:

- `WebFlux` for reactive programming
- `JWT` for authentication
- `MongoDB` as database.

From here, other features can be easily added, as multiple role handling (in JWT claims), WebSocket communication, GitHub Actions for ci/cd, automated tests.

## Implemented Rest API

| Method | Path         | Description |
| ------ | ------------ | ----------- |
| POST   | /auth/signup | Signup      |
| POST   | /auth/login  | Login       |

| Method | Path           | Description             |
| ------ | -------------- | ----------------------- |
| GET    | /products      | Get all products        |
| GET    | /products/{id} | Get a product by id     |
| POST   | /products      | Create new product      |
| PUT    | /products/{id} | Update product data     |
| DELETE | /products/{id} | Delete existing product |

## Usage

The Spring application and the MongoDB instances can be run using Docker containers:

1. `cp .env.sample .env`
2. `docker-compose up -d`

For local development, the project can be opened and run inside the preferred IDE, after starting the database using `docker-compose up -d mongodb`.
