package expense_tracker.service;

import expense_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import expense_tracker.entity.Expense;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import expense_tracker.dto.DashboardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import expense_tracker.repository.UserRepository;
import expense_tracker.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import expense_tracker.dto.ExpenseResponse;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }
    public ExpenseResponse saveExpense(Expense expense) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        expense.setUser(user);

        Expense savedExpense = expenseRepository.save(expense);

        ExpenseResponse response = new ExpenseResponse();

        response.setId(savedExpense.getId());
        response.setCategory(savedExpense.getCategory());
        response.setAmount(savedExpense.getAmount());
        response.setDescription(savedExpense.getDescription());
        response.setDate(savedExpense.getDate());

        return response;
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

    public Page<Expense> getExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

}
