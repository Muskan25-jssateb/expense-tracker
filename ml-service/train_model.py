import pandas as pd
import joblib
import json

from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error, r2_score


# Load dataset
df = pd.read_csv("data/expense_training_data.csv")

features = [
    "day_of_month",
    "spent_so_far",
    "previous_month_spend",
    "monthly_budget",
    "transaction_count"
]

target = "final_month_spend"

X = df[features]
y = df[target]


# Split dataset
X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)


# Train model
model = RandomForestRegressor(
    n_estimators=200,
    random_state=42
)

model.fit(X_train, y_train)


# Make predictions
predictions = model.predict(X_test)
# Simple non-ML baseline:
# Assume spending continues at the same daily rate
baseline_predictions = (
    X_test["spent_so_far"]
    / X_test["day_of_month"]
    * 30
)

baseline_mae = mean_absolute_error(
    y_test,
    baseline_predictions
)

baseline_r2 = r2_score(
    y_test,
    baseline_predictions
)

# Evaluate model
mae = mean_absolute_error(
    y_test,
    predictions
)

r2 = r2_score(
    y_test,
    predictions
)

print("Model trained successfully.")
print()
print(f"Training samples: {len(X_train)}")
print(f"Testing samples: {len(X_test)}")
print()
print(f"Mean Absolute Error: ₹{mae:.2f}")
print(f"R² Score: {r2:.4f}")
print()
print("----- Baseline Comparison -----")
print(f"Baseline MAE: ₹{baseline_mae:.2f}")
print(f"Baseline R² Score: {baseline_r2:.4f}")

improvement = (
    (baseline_mae - mae)
    / baseline_mae
) * 100

print()
print(
    f"ML MAE improvement over baseline: "
    f"{improvement:.2f}%"
)

# Save trained model
joblib.dump(
    model,
    "models/expense_forecast_model.pkl"
)

print()
print("Model saved to models/expense_forecast_model.pkl")

metrics = {
    "model": "RandomForestRegressor",
    "training_samples": len(X_train),
    "testing_samples": len(X_test),
    "mae": round(mae, 2),
    "r2_score": round(r2, 4),
    "baseline_mae": round(baseline_mae, 2),
    "baseline_r2_score": round(baseline_r2, 4),
    "mae_improvement_percent": round(improvement, 2),
    "dataset_type": "synthetic"
}

with open("models/model_metrics.json", "w") as file:
    json.dump(metrics, file, indent=4)

print("Model metrics saved to models/model_metrics.json")