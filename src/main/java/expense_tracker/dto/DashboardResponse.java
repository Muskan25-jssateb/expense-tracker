package expense_tracker.dto;

import expense_tracker.entity.Expense;
import lombok.Data;

import java.util.Map;

@Data
public class DashboardResponse {

    private Double totalExpense;

    private Long expenseCount;

    private Expense highestExpense;

    private Map<String, Double> categorySummary;

}