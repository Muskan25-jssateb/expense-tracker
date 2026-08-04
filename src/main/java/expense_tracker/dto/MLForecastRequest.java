package expense_tracker.dto;

public class MLForecastRequest {

    private int day_of_month;
    private double spent_so_far;
    private double previous_month_spend;
    private double monthly_budget;
    private int transaction_count;

    public MLForecastRequest() {
    }

    public MLForecastRequest(
            int day_of_month,
            double spent_so_far,
            double previous_month_spend,
            double monthly_budget,
            int transaction_count
    ) {
        this.day_of_month = day_of_month;
        this.spent_so_far = spent_so_far;
        this.previous_month_spend = previous_month_spend;
        this.monthly_budget = monthly_budget;
        this.transaction_count = transaction_count;
    }

    public int getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(int day_of_month) {
        this.day_of_month = day_of_month;
    }

    public double getSpent_so_far() {
        return spent_so_far;
    }

    public void setSpent_so_far(double spent_so_far) {
        this.spent_so_far = spent_so_far;
    }

    public double getPrevious_month_spend() {
        return previous_month_spend;
    }

    public void setPrevious_month_spend(double previous_month_spend) {
        this.previous_month_spend = previous_month_spend;
    }

    public double getMonthly_budget() {
        return monthly_budget;
    }

    public void setMonthly_budget(double monthly_budget) {
        this.monthly_budget = monthly_budget;
    }

    public int getTransaction_count() {
        return transaction_count;
    }

    public void setTransaction_count(int transaction_count) {
        this.transaction_count = transaction_count;
    }
}