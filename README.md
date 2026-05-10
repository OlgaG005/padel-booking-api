# Padel Booking API

REST API for managing padel court reservations built with Spring Boot.

---

## Description

This application provides functionality for:

- User registration and authentication
- Court management
- Booking management
- Booking availability validation

The system supports two user roles:

- USER
- ADMIN

Authenticated users can create and cancel their own bookings, while
administrators can manage courts and cancel any booking in the system.

---

## Features

- JWT authentication
- Role-based authorization (USER / ADMIN)
- Court management
- Booking management
- Booking overlap protection
- Availability endpoint
- Global exception handling
- OpenAPI documentation
- Swagger UI support

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database
- JWT
- OpenAPI / Swagger
- Maven

---

## API Documentation

OpenAPI specification:


GET /v3/api-docs


Swagger UI:


http://localhost:8080/swagger-ui.html


---

## Authentication

### Register


POST /api/auth/register


### Login


POST /api/auth/login


Protected endpoints require:


Authorization: Bearer JWT_TOKEN


---

## Default Admin Account


Email: admin@padelbooking.com


---

## Business Rules

- New users are assigned the USER role
- Admin users are created automatically on startup
- Only ADMIN users can create courts
- Logged-in users can create bookings
- Users can cancel only their own bookings
- ADMIN users can cancel any booking
- Booking duration must be exactly 60 or 90 minutes
- Overlapping bookings are not allowed

---

## Running the Application

### Clone the repository


git clone <repository-url>


### Run the application


./mvnw spring-boot:run


Or run directly from IntelliJ IDEA.

The application starts at:


http://localhost:8080


---

## H2 Console


http://localhost:8080/h2-console


JDBC URL:


jdbc:h2:mem:padelbookingdb


---

## Project Structure


auth/        -> authentication & JWT

booking/     -> booking logic

court/       -> court management

user/        -> user entity & roles

config/      -> security & OpenAPI config

exception/   -> global exception handling


---

## Example Endpoints

### Create Court

POST /api/courts

Required role:

ADMIN


### Create Booking


POST /api/bookings


Required role:


USER or ADMIN


### Cancel Booking


DELETE /api/bookings/{id}


### Check Court Availability

GET /api/bookings/availability


