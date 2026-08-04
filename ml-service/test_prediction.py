import joblib
import pandas as pd

model = joblib.load(
    "models/expense_forecast_model.pkl"
)

sample = pd.DataFrame([{
    "day_of_month": 10,
    "spent_so_far": 3500,
    "previous_month_spend": 9000,
    "monthly_budget": 10000,
    "transaction_count": 12
}])

prediction = model.predict(sample)[0]

print("Current spending: ₹3500")
print("Monthly budget: ₹10000")
print("Previous month spending: ₹9000")
print()
print(f"Predicted month-end spending: ₹{prediction:.2f}")

difference = prediction - 10000

if difference > 0:
    print(f"Predicted overspending: ₹{difference:.2f}")
else:
    print(f"Predicted amount under budget: ₹{abs(difference):.2f}")