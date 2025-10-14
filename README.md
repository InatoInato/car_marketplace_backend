# Car Marketplace
A full-stack web application for buying and selling cars.
## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Endpoints](#endpoints)

## Features
- List cars for sale
- Search for cars by make, model, year, and price
- Contact sellers directly through the platform
- User authentication and profiles
- Admin dashboard for managing listings and users

## Technologies Used
- Frontend: React, Redux, Tailwind CSS
- Backend: Java (Spring Boot)
- Database: PostgreSQL
- Authentication: JWT

## Endpoints
- `host: http://localhost:8080` (for local development)
- `GET /api/cars`: Retrieve a list of all cars
- `GET /api/cars/{id}`: Retrieve details of a specific car
- `POST /api/cars`: Create a new car listing
- `PUT /api/cars/{id}`: Update an existing car listing
- `DELETE /api/cars/{id}`: Delete a car listing
- `POST /api/auth/register`: Register a new user
- `POST /api/auth/login`: Authenticate a user and return a JWT
- `GET /api/users/{id}`: Retrieve user profile information
- `PUT /api/users/{id}`: Update user profile information
- `GET /api/admin/dashboard`: Access admin dashboard data (admin only)