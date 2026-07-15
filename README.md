# Expense Tracker REST API

A Spring Boot based Expense Tracker application that allows users to manage expenses and view expense analytics through REST APIs.

##  Features

- Create, Read, Update and Delete (CRUD) expenses
- Search expenses by category
- Search expenses by date range
- Calculate total expenses
- Category-wise expense summary
- Highest expense retrieval
- Dashboard API
- Pagination
- Sorting
- Bean Validation
- Global Exception Handling
- Interactive API Documentation using Swagger/OpenAPI

##  Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- Swagger / OpenAPI
- Postman
- Git & GitHub

##  Architecture

Controller
        ↓
Service
        ↓
Repository
        ↓
MySQL Database

##  REST APIs

| Method | Endpoint |
|---------|----------|
| POST | /expenses |
| GET | /expenses |
| GET | /expenses/{id} |
| PUT | /expenses/{id} |
| DELETE | /expenses/{id} |
| GET | /expenses/total |
| GET | /expenses/category-summary |
| GET | /expenses/search?category=Food |
| GET | /expenses/date-range |
| GET | /expenses/highest |
| GET | /expenses/dashboard |

##  Future Enhancements

- JWT Authentication
- User Registration & Login
- User-specific Expenses
- Budget Management
- React Frontend
- Charts & Analytics Dashboard
- Docker
- Deployment (Render/Railway)
