package expense_tracker.dto;

import java.math.BigDecimal;

public class BudgetSummary {

    private BigDecimal budget;
    private BigDecimal spent;
    private BigDecimal remaining;
    private double percentageUsed;

    public BudgetSummary(
            BigDecimal budget,
            BigDecimal spent,
            BigDecimal remaining,
            double percentageUsed
    ) {
        this.budget = budget;
        this.spent = spent;
        this.remaining = remaining;
        this.percentageUsed = percentageUsed;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public double getPercentageUsed() {
        return percentageUsed;
    }
}