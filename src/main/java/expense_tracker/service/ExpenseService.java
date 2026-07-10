package expense_tracker.service;

import expense_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import expense_tracker.entity.Expense;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import expense_tracker.dto.DashboardResponse;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id).orElse(null);
        if (existingExpense == null) {
            return null;
        }
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setDate(expense.getDate());
        return expenseRepository.save(existingExpense);
    }
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Double getTotalExpense() {
        return expenseRepository.getTotalExpense();
    }

    public Map<String, Double> getCategorySummary() {

        List<Object[]> results = expenseRepository.getCategorySummary();

        Map<String, Double> summary = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Double total = (Double) row[1];

            summary.put(category, total);
        }

        return summary;
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> getExpensesBetweenDates(LocalDate start, LocalDate end) {
        return expenseRepository.findByDateBetween(start, end);
    }

    public Expense getHighestExpense() {
        return expenseRepository.findTopByOrderByAmountDesc();
    }

    public DashboardResponse getDashboard() {

        DashboardResponse dashboard = new DashboardResponse();

        dashboard.setTotalExpense(getTotalExpense());

        dashboard.setExpenseCount(expenseRepository.count());

        dashboard.setHighestExpense(getHighestExpense());

        dashboard.setCategorySummary(getCategorySummary());

        return dashboard;
    }

}
