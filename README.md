# Expense Tracker

A full-stack Expense Tracker application built with Spring Boot and React that allows users to securely manage expenses, analyze spending patterns, and visualize expense data.

The application uses JWT-based authentication and provides user-specific expense tracking with dashboard analytics, filtering, and data visualization.

## Features

### Authentication & Security
- User Registration and Login
- JWT Authentication
- Password Encryption using BCrypt
- Spring Security
- Protected Frontend Routes
- User-specific Expenses

### Expense Management
- Add Expenses
- View Expense History
- Update Expenses
- Delete Expenses
- Search Expenses by Category
- Filter Expenses by Date Range
- Bean Validation
- Global Exception Handling

### Dashboard & Analytics
- Total Expense Calculation
- Expense Count
- Highest Expense
- Category-wise Expense Summary
- Category-wise Spending Bar Chart

### Backend
- RESTful APIs
- Pagination
- Sorting
- Swagger/OpenAPI Documentation
- Layered Architecture

## Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- MySQL
- Maven
- Lombok
- Swagger / OpenAPI

### Frontend
- React
- JavaScript
- Vite
- Axios
- Bootstrap
- Recharts

### Tools
- Git & GitHub
- Postman
- IntelliJ IDEA
- VS Code

## Architecture

```text
React Frontend
      |
      | Axios / REST API
      v
Spring Boot Backend
      |
      v
Controller
      |
      v
Service
      |
      v
Repository
      |
      v
MySQL Database
```

## Authentication Flow

```text
Register / Login
       |
       v
Spring Security
       |
       v
JWT Generated
       |
       v
React stores JWT
       |
       v
JWT sent with protected API requests
       |
       v
User-specific expense data
```

## REST APIs

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT |

### Expenses

| Method | Endpoint | Description |
|---|---|---|
| POST | `/expenses` | Add expense |
| GET | `/expenses` | Get expenses |
| GET | `/expenses/{id}` | Get expense by ID |
| PUT | `/expenses/{id}` | Update expense |
| DELETE | `/expenses/{id}` | Delete expense |
| GET | `/expenses/total` | Get total expense |
| GET | `/expenses/category-summary` | Category-wise summary |
| GET | `/expenses/search?category=Food` | Search by category |
| GET | `/expenses/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` | Filter by date range |
| GET | `/expenses/highest` | Get highest expense |
| GET | `/expenses/dashboard` | Get dashboard analytics |

## Project Structure

```text
expense-tracker/
│
├── src/                    # Spring Boot backend
│   └── main/
│       └── java/
│
├── frontend/               # React frontend
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── services/
│   └── package.json
│
├── pom.xml
└── README.md
```

## Running the Project

### Backend

Configure the MySQL database in the application configuration and run the Spring Boot application.

Backend runs on:

```text
http://localhost:8080
```

Swagger documentation:

```text
http://localhost:8080/swagger-ui/index.html
```

### Frontend

Navigate to:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

## Upcoming Features

- Monthly Budget Management
- Budget Usage Progress Tracking
- Recommended Daily Spending Limit
- Month-over-Month Expense Comparison
- Category-wise Monthly Comparison
- AI-powered Spending Insights
- Spending Recommendations
- Improved Responsive UI
- Docker
- Deployment

## Planned AI Insights

The analytics system will use calculated spending data to provide insights such as:

- Comparison with the previous month's spending
- Categories with the largest spending increase
- Categories where spending decreased
- Remaining monthly budget
- Recommended daily spending based on remaining budget
- Potential areas where spending can be reduced

## Future Goal

The goal is to evolve the project from a traditional expense tracker into an intelligent personal finance assistant that helps users understand their spending habits and make better budgeting decisions.