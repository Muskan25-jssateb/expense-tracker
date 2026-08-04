# Expense Tracker

A full-stack intelligent Expense Tracker built with Spring Boot, React, Machine Learning, and Generative AI.

The application allows users to securely manage expenses, set monthly budgets, analyze spending patterns, compare monthly spending, forecast month-end expenses using a Machine Learning model, and generate personalized AI-powered spending insights.

The system uses JWT-based authentication to provide user-specific expense tracking and combines traditional financial analytics, a Random Forest forecasting model, and the Gemini API to provide intelligent spending assistance.

---

## Features

### Authentication & Security

- User Registration and Login
- JWT-based Authentication
- Password Encryption using BCrypt
- Spring Security
- Protected Frontend Routes
- User-specific Expense Data
- Authenticated Financial Analytics

### Expense Management

- Add Expenses
- View Expense History
- Update Expenses
- Delete Expenses
- Search Expenses by Category
- Filter Expenses by Date Range
- Pagination and Sorting
- Bean Validation
- Global Exception Handling

### Dashboard & Financial Analytics

- Total Expense Calculation
- Expense Count
- Highest Expense
- Category-wise Expense Summary
- Category-wise Spending Visualization
- Monthly Budget Tracking
- Budget Usage Percentage
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

### Machine Learning Expense Forecasting

- Random Forest Regression Model
- Month-end Expense Forecasting
- FastAPI ML Microservice
- User-specific Forecast Generation
- Forecast based on:
  - Day of Month
  - Spending So Far
  - Previous Month Spending
  - Monthly Budget
  - Transaction Count
- Prediction of Potential Budget Overspending
- Model Evaluation using MAE and R²
- Baseline Comparison
- Graceful Fallback when ML Service is Unavailable

### AI Spending Assistant

- Gemini API Integration
- AI-generated Personalized Spending Insights
- Combines:
  - Current Budget Status
  - Current Spending
  - Month-over-Month Comparison
  - Category Spending Patterns
  - Machine Learning Forecast
- Generates Practical Spending Recommendations
- Distinguishes ML Predictions from Observed Spending Data
- AI Insights Generated On Demand
- Graceful Handling of Gemini API Failure
- AI Output Disclaimer

---

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

### Machine Learning

- Python
- FastAPI
- scikit-learn
- pandas
- NumPy
- Random Forest Regression

### Generative AI

- Google Gemini API
- Prompt-based Financial Insight Generation

### Tools

- Git & GitHub
- Postman
- Swagger UI
- IntelliJ IDEA
- VS Code

---

## System Architecture

```text
                         React Frontend
                               |
                               | Axios / REST API
                               v
                     Spring Boot Backend
                               |
             +-----------------+------------------+
             |                 |                  |
             v                 v                  v
       Authentication    Financial Analytics    AI Insights
       JWT + Security    Budget Tracking             |
                         Monthly Comparison          |
                         Category Analysis            |
             |                 |                     |
             |                 +----------+----------+
             |                            |
             |                            v
             |                    FastAPI ML Service
             |                            |
             |                            v
             |                  Random Forest Model
             |                            |
             |                            v
             |                    Expense Forecast
             |                            |
             |                            +----------+
             |                                       |
             |                                       v
             |                                  Gemini API
             |                                       |
             |                                       v
             |                          Personalized AI Insights
             |
             +-------------------+
                                 |
                                 v
                          Spring Data JPA
                                 |
                                 v
                           MySQL Database
```
## Screenshots

### Dashboard Overview
![Dashboard Overview](docs/screenshots/Dashboard%20Overview.png)

### Monthly Budget Tracking
![Monthly Budget](docs/screenshots/Monthly%20Budget.png)

### Spending by Category
![Spending by Category](docs/screenshots/Spending%20by%20Category.png)

### ML Expense Forecast
![ML Expense Forecast](docs/screenshots/ML%20Expense%20Forecast.png)

### AI Spending Assistant
![AI Spending Assistant](docs/screenshots/AI%20Spending%20Assistant.png)

### Expense Management
![Expense Management](docs/screenshots/Expense%20Management.png)
---

## Intelligent Insight Flow

```text
User Expense Data
       |
       v
Spring Boot Financial Analytics
       |
       +---- Budget Status
       |
       +---- Monthly Comparison
       |
       +---- Category Spending
       |
       v
FastAPI ML Service
       |
       v
Random Forest Model
       |
       v
Predicted Month-end Spending
       |
       v
Spring Boot AI Insight Service
       |
       v
Gemini API
       |
       v
Personalized Spending Insights
       |
       v
React Dashboard
```

---

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
React Stores JWT
       |
       v
JWT Sent With Protected Requests
       |
       v
Authenticated User Identified
       |
       v
User-specific Expense and Analytics Data
```

---

## Machine Learning Model

The expense forecasting component uses a **Random Forest Regression** model to estimate the user's final spending for the current month.

### Model Features

The model receives five input features:

```text
day_of_month
spent_so_far
previous_month_spend
monthly_budget
transaction_count
```

The target variable is:

```text
final_month_spend
```

### Dataset

The initial model was trained and evaluated using a generated synthetic dataset containing:

```text
3,000 samples
```

Train/test split:

```text
Training samples: 2,400
Testing samples: 600
```

The synthetic dataset is generated through `generate_data.py`, allowing the training data to be reproduced without storing generated dataset files in the repository.

### Model Evaluation

Random Forest performance on the synthetic test set:

| Metric | Result |
|---|---:|
| Mean Absolute Error (MAE) | ₹3,158.52 |
| R² Score | 0.9111 |

A simple daily spending-rate projection was used as a baseline.

| Model | MAE | R² |
|---|---:|---:|
| Daily-rate Baseline | ₹3,344.63 | 0.9045 |
| Random Forest | ₹3,158.52 | 0.9111 |

The Random Forest achieved approximately:

```text
5.56% lower MAE than the baseline
```

> The evaluation was performed on synthetic data. The reported R² value represents goodness of fit and should not be interpreted as classification accuracy.

---

## AI Spending Assistant

The AI Spending Assistant uses the Gemini API to transform calculated financial analytics and ML forecasts into concise, personalized explanations.

Instead of sending raw transaction records directly to the AI service, the backend provides summarized financial context such as:

```text
Monthly Budget
Amount Spent
Remaining Budget
Budget Usage Percentage
Previous Month Spending
Monthly Percentage Change
Category Spending Summaries
ML Predicted Month-end Spending
Predicted Budget Difference
```

Gemini then generates three short spending insights and practical recommendations based on the supplied data.

The prompt instructs the model to:

- Use only the supplied financial information
- Avoid inventing financial facts
- Distinguish observed data from ML predictions
- Avoid assuming whether categories are essential or non-essential
- Avoid investment, tax, credit, and legal advice
- Keep recommendations concise
- Use Indian Rupees (₹)

AI insights are informational and may not always be accurate.

---

## Reliability & Fallback Handling

The intelligent features are designed so that failure of an external component does not unnecessarily break the dashboard.

### ML Service Unavailable

If the FastAPI ML service cannot be reached:

```text
ML Forecast
    |
    X unavailable
    |
    v
Budget + Spending Analytics
    |
    v
Gemini
    |
    v
AI Insights Without ML Forecast
```

### Gemini API Unavailable

If Gemini cannot be reached, the application returns a user-friendly message while the standard expense, budget, and analytics functionality remains available.

---

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

### Budgets & Analytics

| Method | Endpoint | Description |
|---|---|---|
| POST | `/budgets` | Set or update monthly budget |
| GET | `/budgets` | Get budget for a specific month |
| GET | `/budgets/summary` | Get budget usage summary |
| GET | `/budgets/comparison` | Compare current and previous month |
| GET | `/budgets/forecast` | Generate ML month-end expense forecast |

### AI

| Method | Endpoint | Description |
|---|---|---|
| GET | `/ai/insights` | Generate personalized AI spending insights |

---

## Project Structure

```text
expense-tracker/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── expense_tracker/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── entity/
│       │       ├── repository/
│       │       ├── security/
│       │       └── service/
│       │
│       └── resources/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── services/
│   └── package.json
│
├── ml-service/
│   ├── app.py
│   ├── generate_data.py
│   ├── train_model.py
│   ├── test_prediction.py
│   ├── requirements.txt
│   └── models/
│       └── model_metrics.json
│
├── pom.xml
├── .gitignore
└── README.md
```

Generated datasets, Python cache files, and trained `.pkl` model artifacts are excluded from Git.

---

## Running the Project

The application consists of three locally running services:

```text
React Frontend      → Port 5173
Spring Boot Backend → Port 8080
FastAPI ML Service  → Port 8000
```

### 1. Database

Create and configure a MySQL database for the application.

Configure the required database properties in the Spring Boot application configuration.

---

### 2. Gemini API Key

Create a Gemini API key and store it as an environment variable.

On Windows:

```powershell
setx GEMINI_API_KEY "YOUR_API_KEY"
```

Restart the terminal/IDE after creating the environment variable.

Spring Boot reads the key using:

```properties
gemini.api.key=${GEMINI_API_KEY}
```

> Never commit the actual Gemini API key to GitHub.

---

### 3. Machine Learning Service

Navigate to:

```bash
cd ml-service
```

Install the Python dependencies:

```bash
pip install -r requirements.txt
```

Generate the synthetic training dataset:

```bash
python generate_data.py
```

Train the Random Forest model:

```bash
python train_model.py
```

Start FastAPI:

```bash
uvicorn app:app --reload
```

The ML service runs on:

```text
http://localhost:8000
```

---

### 4. Spring Boot Backend

Run the Spring Boot application.

The backend runs on:

```text
http://localhost:8080
```

Swagger documentation is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

---

### 5. React Frontend

Navigate to:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start Vite:

```bash
npm run dev
```

The frontend runs on:

```text
http://localhost:5173
```

---

## Security Notes

- Passwords are encrypted using BCrypt.
- Protected APIs require JWT authentication.
- Expense and financial analytics are associated with the authenticated user.
- The Gemini API key is loaded through an environment variable rather than hardcoded into source code.
- API keys and secrets should never be committed to the repository.

---

## Current Intelligent Features

The application currently combines three levels of financial analysis:

### 1. Rule-based Analytics

Calculates:

- Budget usage
- Remaining budget
- Recommended daily spending
- Month-over-month changes
- Category spending changes
- Spending warnings

### 2. Machine Learning

Predicts:

- Estimated final month spending
- Whether spending is likely to exceed the monthly budget
- Expected difference from the budget

### 3. Generative AI

Explains:

- Current spending behavior
- Significant category changes
- ML forecast implications
- Practical spending recommendations

---

## Future Improvements

- Anomaly Detection for Unusual Expenses
- Training Forecasting Models on Larger Real-world Datasets
- Time-series Expense Forecasting
- Improved Responsive UI
- Docker Containerization
- Cloud Deployment
- Automated Model Retraining
- Additional Model Monitoring

---

## Future Goal

The goal of this project is to evolve a traditional CRUD-based expense tracker into an intelligent personal finance assistant by combining secure full-stack development, financial analytics, machine learning forecasting, and generative AI.

The project demonstrates the integration of:

```text
Full-stack Development
        +
Backend Security
        +
Financial Analytics
        +
Machine Learning
        +
Generative AI
```

to provide users with a more informative and intelligent expense-management experience.

