package expense_tracker.dto;

import java.util.Map;

public class MonthlyComparison {

    private double currentMonthTotal;
    private double previousMonthTotal;
    private double difference;
    private double percentageChange;

    private Map<String, Double> currentMonthCategories;
    private Map<String, Double> previousMonthCategories;

    public MonthlyComparison(
            double currentMonthTotal,
            double previousMonthTotal,
            double difference,
            double percentageChange,
            Map<String, Double> currentMonthCategories,
            Map<String, Double> previousMonthCategories
    ) {
        this.currentMonthTotal = currentMonthTotal;
        this.previousMonthTotal = previousMonthTotal;
        this.difference = difference;
        this.percentageChange = percentageChange;
        this.currentMonthCategories = currentMonthCategories;
        this.previousMonthCategories = previousMonthCategories;
    }

    public double getCurrentMonthTotal() {
        return currentMonthTotal;
    }

    public double getPreviousMonthTotal() {
        return previousMonthTotal;
    }

    public double getDifference() {
        return difference;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public Map<String, Double> getCurrentMonthCategories() {
        return currentMonthCategories;
    }

    public Map<String, Double> getPreviousMonthCategories() {
        return previousMonthCategories;
    }
}