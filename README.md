# Expense Tracker

A full-stack intelligent Expense Tracker built with Spring Boot and React that allows users to securely manage expenses, set monthly budgets, analyze spending patterns, compare monthly spending, and receive data-driven financial insights.

The application uses JWT-based authentication and provides user-specific expense tracking with dashboard analytics, budget monitoring, spending recommendations, month-over-month comparisons, and interactive data visualization.

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
- Monthly Budget Tracking
- Budget Usage Percentage and Progress Bar
- Remaining Budget Calculation
- Recommended Daily Spending Limit
- Projected Month-end Spending
- Budget Overspending Warning
- Month-over-Month Expense Comparison
- Monthly Spending Percentage Change
- Category-wise Monthly Comparison
- Largest Category Spending Increase Detection
- Largest Category Spending Decrease Detection
- Rule-based Smart Spending Insights

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
      +--------------------+
      |                    |
      v                    v
Authentication         Financial Analytics
JWT + Security         Budget Tracking
      |                Monthly Comparison
      |                Spending Insights
      |                    |
      +---------+----------+
                |
                v
          Spring Data JPA
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

### Budgets

| Method | Endpoint | Description |
|---|---|---|
| POST | `/budgets` | Set or update monthly budget |
| GET | `/budgets` | Get budget for a specific month |
| GET | `/budgets/summary` | Get budget, spending, remaining amount, and usage percentage |
| GET | `/budgets/comparison` | Compare current and previous month spending and categories |

## Project Structure

```text
expense-tracker/
│
├── src/
│   └── main/
│       └── java/
│           └── expense_tracker/
│               ├── config/
│               ├── controller/
│               ├── dto/
│               ├── entity/
│               ├── repository/
│               ├── security/
│               └── service/
│
├── frontend/
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

- Machine Learning-based Expense Forecasting
- AI-powered Personalized Spending Insights
- Improved Spending Forecast Reliability
- Anomaly Detection for Unusual Expenses
- Improved Responsive UI
- Docker
- Deployment

## Smart Spending Insights

The application currently analyzes expense and budget data to generate rule-based financial insights, including:

- Comparison with the previous month's spending
- Percentage increase or decrease in monthly spending
- Categories with the largest spending increase
- Categories where spending decreased
- Remaining monthly budget
- Recommended daily spending based on the remaining budget
- Projected month-end spending
- Warnings when current spending trends may exceed the monthly budget

### Planned AI & ML Enhancements

The next development phase will introduce machine learning-based expense forecasting and AI-generated personalized financial recommendations using the application's existing analytics data.

## Future Goal

The goal is to evolve the project from a traditional expense tracker into an intelligent personal finance assistant that helps users understand their spending habits and make better budgeting decisions.