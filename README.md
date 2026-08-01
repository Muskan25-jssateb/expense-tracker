# Expense Tracker

A full-stack Expense Tracker application built using Spring Boot and React.

The application allows users to securely register and log in, manage their personal expenses, and view expense analytics through an interactive dashboard.

## Features

### Authentication & Security
- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Protected API Endpoints
- User-Specific Expense Management
- React Protected Routes
- Logout Functionality

### Expense Management
- Add Expense
- View Expenses
- View Expense by ID
- Update Expense
- Delete Expense
- Search Expenses by Category
- Filter Expenses by Date Range
- Pagination
- Sorting
- Bean Validation

### Dashboard & Analytics
- Total Expense Calculation
- Expense Count
- Highest Expense
- Category-wise Expense Summary
- Dashboard API
- React Dashboard displaying live backend data

### Backend
- REST API Architecture
- Global Exception Handling
- Swagger / OpenAPI Documentation
- Spring Security
- JWT-based Authorization

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

### Backend

Controller  
↓  
Service  
↓  
Repository  
↓  
MySQL Database

### Full-Stack Flow

React Frontend  
↓  
Axios  
↓  
JWT Authentication  
↓  
Spring Boot REST API  
↓  
Spring Data JPA  
↓  
MySQL Database

## REST APIs

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT |

### Expenses

| Method | Endpoint | Description |
|---|---|---|
| POST | `/expenses` | Add an expense |
| GET | `/expenses` | Get logged-in user's expenses |
| GET | `/expenses/{id}` | Get expense by ID |
| PUT | `/expenses/{id}` | Update expense |
| DELETE | `/expenses/{id}` | Delete expense |
| GET | `/expenses/total` | Get total expense |
| GET | `/expenses/category-summary` | Get category-wise summary |
| GET | `/expenses/search?category=Food` | Search by category |
| GET | `/expenses/date-range` | Filter by date range |
| GET | `/expenses/highest` | Get highest expense |
| GET | `/expenses/dashboard` | Get dashboard analytics |
| GET | `/expenses/paged` | Get paginated expenses |

## Security

The application uses JWT-based authentication.

After successful login, the backend generates a JWT token. The React frontend stores the token and automatically sends it in authenticated API requests using:

Authorization: Bearer <JWT_TOKEN>

Expense operations are user-specific, preventing one user from accessing another user's expenses.

## Current Progress

- [x] Spring Boot REST API
- [x] MySQL Integration
- [x] Expense CRUD
- [x] Validation
- [x] Global Exception Handling
- [x] Swagger Documentation
- [x] User Registration
- [x] User Login
- [x] BCrypt Password Encryption
- [x] JWT Authentication
- [x] User-Specific Expenses
- [x] React Frontend Setup
- [x] Login & Registration UI
- [x] Protected Routes
- [x] Expense Dashboard
- [x] Add Expense
- [x] Edit Expense
- [x] Delete Expense
- [x] Logout
- [ ] Frontend Search & Filters
- [ ] Charts & Advanced Analytics
- [ ] AI Spending Insights
- [ ] Budget Management
- [ ] Docker
- [ ] Deployment

## Planned Enhancements

- AI-powered spending insights
- Interactive charts and analytics
- Monthly budget management
- Category and date filters in the frontend
- Improved responsive UI
- Docker containerization
- Cloud deployment
