import random
import pandas as pd

random.seed(42)

rows = []

# Create many synthetic historical months
for _ in range(3000):

    budget = random.randint(5000, 50000)

    previous_month_spend = random.randint(
        int(budget * 0.6),
        int(budget * 1.4)
    )

    final_month_spend = random.randint(
        int(budget * 0.65),
        int(budget * 1.35)
    )

    day_of_month = random.randint(5, 28)

    # Approximate how much of the final monthly spend
    # has happened by this day.
    progress = day_of_month / 30

    variation = random.uniform(0.75, 1.25)

    spent_so_far = final_month_spend * progress * variation

    # Prevent partial spending from exceeding final spending
    spent_so_far = min(
        spent_so_far,
        final_month_spend * 0.95
    )

    transaction_count = random.randint(
        max(1, day_of_month // 2),
        day_of_month * 3
    )

    rows.append({
        "day_of_month": day_of_month,
        "spent_so_far": round(spent_so_far, 2),
        "previous_month_spend": previous_month_spend,
        "monthly_budget": budget,
        "transaction_count": transaction_count,
        "final_month_spend": final_month_spend
    })


df = pd.DataFrame(rows)

df.to_csv(
    "data/expense_training_data.csv",
    index=False
)

print("Dataset generated successfully.")
print()
print(df.head())
print()
print("Rows:", len(df))