# Expense Tracker

A full-stack Expense Tracker application built with **Spring Boot, React, and MySQL**.

The application allows users to securely register and log in, manage their personal expenses, and view expense analytics through a dashboard.

## Features

### Authentication & Security
- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Protected Backend APIs
- Protected React Routes
- User-Specific Expense Management
- Logout Functionality

### Expense Management
- Add Expense
- View Expenses
- View Expense by ID
- Update Expense
- Delete Expense
- Search by Category
- Search by Date Range
- Pagination
- Sorting
- Bean Validation

### Dashboard & Analytics
- Total Expense Calculation
- Expense Count
- Highest Expense Retrieval
- Category-wise Expense Summary
- Dashboard API
- Live React Dashboard

### Backend
- RESTful API Architecture
- Spring Security
- JWT-based Authorization
- Global Exception Handling
- Swagger / OpenAPI Documentation

### Frontend
- React + Vite
- Login Page
- Registration Page
- Protected Routes
- Dashboard
- Expense Management
- Add / Edit / Delete Expenses
- Navigation & Logout
- Axios API Integration
- Bootstrap UI

## Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- JWT
- Swagger / OpenAPI

### Frontend
- React
- Vite
- JavaScript
- React Router
- Axios
- Bootstrap
- Recharts

### Tools
- Postman
- Swagger UI
- Git
- GitHub

## Architecture

```text
React Frontend
      ↓
    Axios
      ↓
JWT Authentication
      ↓
Spring Boot REST API
      ↓
   Service Layer
      ↓
Spring Data JPA
      ↓
 MySQL Database
```

## Project Structure

```text
expense-tracker/
│
├── src/                 # Spring Boot Backend
├── frontend/            # React Frontend
│   ├── src/
│   ├── public/
│   └── package.json
│
├── pom.xml
└── README.md
```

## REST APIs

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT |

### Expenses

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/expenses` | Add an expense |
| GET | `/expenses` | Get user's expenses |
| GET | `/expenses/{id}` | Get expense by ID |
| PUT | `/expenses/{id}` | Update an expense |
| DELETE | `/expenses/{id}` | Delete an expense |
| GET | `/expenses/total` | Get total expense |
| GET | `/expenses/category-summary` | Get category-wise summary |
| GET | `/expenses/search?category=Food` | Search by category |
| GET | `/expenses/date-range` | Filter by date range |
| GET | `/expenses/highest` | Get highest expense |
| GET | `/expenses/dashboard` | Get dashboard analytics |
| GET | `/expenses/paged` | Get paginated expenses |

## Authentication Flow

```text
User Login
    ↓
Spring Boot validates credentials
    ↓
JWT generated
    ↓
React stores JWT
    ↓
Axios sends JWT with API requests
    ↓
Spring Security validates JWT
    ↓
User accesses protected resources
```

Each user's expenses are isolated so authenticated users can only access their own expense data.

## Current Progress

- [x] Spring Boot REST API
- [x] MySQL Integration
- [x] Expense CRUD
- [x] Bean Validation
- [x] Global Exception Handling
- [x] Swagger Documentation
- [x] User Registration
- [x] User Login
- [x] BCrypt Password Encryption
- [x] JWT Authentication
- [x] User-Specific Expenses
- [x] React Frontend
- [x] Login & Registration UI
- [x] Protected Routes
- [x] Dashboard
- [x] Add Expense
- [x] Edit Expense
- [x] Delete Expense
- [x] Logout

## Future Enhancements

- Frontend Category & Date Filters
- Interactive Charts & Advanced Analytics
- AI-Powered Spending Insights
- Budget Management
- Improved Responsive UI
- Docker Containerization
- Cloud Deployment