from fastapi import FastAPI
from pydantic import BaseModel
import pandas as pd
import joblib

app = FastAPI(
    title="Expense Forecast ML Service",
    version="1.0"
)

model = joblib.load(
    "models/expense_forecast_model.pkl"
)


class ExpenseForecastRequest(BaseModel):
    day_of_month: int
    spent_so_far: float
    previous_month_spend: float
    monthly_budget: float
    transaction_count: int


@app.get("/")
def health_check():
    return {
        "status": "running",
        "service": "Expense Forecast ML Service"
    }


@app.post("/predict")
def predict_expense(request: ExpenseForecastRequest):

    features = pd.DataFrame([{
        "day_of_month": request.day_of_month,
        "spent_so_far": request.spent_so_far,
        "previous_month_spend": request.previous_month_spend,
        "monthly_budget": request.monthly_budget,
        "transaction_count": request.transaction_count
    }])

    prediction = float(
        model.predict(features)[0]
    )

    predicted_difference = (
        prediction - request.monthly_budget
    )

    return {
        "predicted_month_end_spending": round(prediction, 2),
        "monthly_budget": request.monthly_budget,
        "predicted_difference": round(predicted_difference, 2),
        "will_exceed_budget": predicted_difference > 0
    }