package expense_tracker.dto;

public class MLForecastResponse {

    private double predicted_month_end_spending;
    private double monthly_budget;
    private double predicted_difference;
    private boolean will_exceed_budget;

    public MLForecastResponse() {
    }

    public double getPredicted_month_end_spending() {
        return predicted_month_end_spending;
    }

    public void setPredicted_month_end_spending(
            double predicted_month_end_spending
    ) {
        this.predicted_month_end_spending =
                predicted_month_end_spending;
    }

    public double getMonthly_budget() {
        return monthly_budget;
    }

    public void setMonthly_budget(double monthly_budget) {
        this.monthly_budget = monthly_budget;
    }

    public double getPredicted_difference() {
        return predicted_difference;
    }

    public void setPredicted_difference(
            double predicted_difference
    ) {
        this.predicted_difference =
                predicted_difference;
    }

    public boolean isWill_exceed_budget() {
        return will_exceed_budget;
    }

    public void setWill_exceed_budget(
            boolean will_exceed_budget
    ) {
        this.will_exceed_budget =
                will_exceed_budget;
    }
}