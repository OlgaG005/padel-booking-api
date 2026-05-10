Padel Booking API v1.0
======================

Base URL:
http://localhost:8080


AUTH
----

Register user:
POST /api/auth/register

JSON:
{
"fullName": "Olga User",
"email": "olga@test.com",
"password": "123456",
"phone": "6900000000"
}

Login:
POST /api/auth/login

Admin login:
{
"email": "admin@padelbooking.com",
"password": "admin123"
}

Response:
{
"token": "JWT_TOKEN",
"email": "admin@padelbooking.com",
"role": "ADMIN"
}

For protected endpoints always include:

Authorization: Bearer JWT_TOKEN


COURTS
------

Create court:
POST /api/courts

Access:
ADMIN only

Body:
{
"name": "Court 1",
"location": "Main Facility"
}

List courts:
GET /api/courts

Access:
Authenticated users


BOOKINGS
--------

Create booking:
POST /api/bookings

Access:
Authenticated users

Body:
{
"courtId": 1,
"startTime": "2026-05-11T18:00:00",
"endTime": "2026-05-11T19:00:00"
}

My bookings:
GET /api/bookings/my

Access:
Authenticated users

All bookings:
GET /api/bookings

Access:
ADMIN only

Cancel booking:
PATCH /api/bookings/{id}/cancel

Example:
PATCH /api/bookings/1/cancel

Access:
Booking owner or ADMIN

eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJtYXJpYUB0ZXN0LmNvbSIsImlhdCI6MTc3ODQxNjgzNCwiZXhwIjoxNzc4NTAzMjM0fQ.hrs9iOf-6mA0YXeX10qo0qhe8N8Dz9wyRAqNavWZ-vvfsBhPbusGl_GH2qhQVHM1
AVAILABILITY
------------

Court availability:
GET /api/bookings/availability?date=2026-05-11

Access:
Authenticated users


OPENAPI
-------

OpenAPI specification:
GET /v3/api-docs

Swagger UI:
GET /swagger-ui.html


RULES
-----

- New users are USER by default.
- Admin users are not created through public registration.
- A default ADMIN account is created automatically on application startup.
- Only ADMIN users can create courts.
- Logged-in users can create bookings.
- Users can cancel only their own bookings.
- ADMIN users can cancel any booking.
- Booking duration must be exactly 60 or 90 minutes.
- Same court cannot have overlapping bookings.


DEFAULT ADMIN
-------------

Email:
admin@padelbooking.com

Password:
admin123

eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBwYWRlbGJvb2tpbmcuY29tIiwiaWF0IjoxNzc4NDE2MTE4LCJleHAiOjE3Nzg1MDI1MTh9.Gq8YTs8hcpB-3-scBDdDmXHlRlRi6ZaCaqq8eniooHZeB6yGS-Y6kMtXWOUd2dUR