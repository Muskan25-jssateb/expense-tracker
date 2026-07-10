# Expense Tracker REST API

A Spring Boot based Expense Tracker application that allows users to manage expenses and view expense analytics through REST APIs.

##  Features

- Add Expense
- View All Expenses
- View Expense by ID
- Update Expense
- Delete Expense
- Total Expense Calculation
- Category-wise Expense Summary
- Search Expenses by Category
- Search Expenses by Date Range
- Highest Expense
- Dashboard API
- Input Validation
- Global Exception Handling

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- Postman

##  Project Structure

```
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL Database
```

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

## Future Enhancements

- Swagger Documentation
- JWT Authentication
- Budget Tracking
- Pagination & Sorting
- React Frontend
- Docker Deployment
