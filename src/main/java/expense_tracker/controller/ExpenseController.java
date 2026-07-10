package expense_tracker.controller;

import expense_tracker.entity.Expense;
import expense_tracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import expense_tracker.dto.DashboardResponse;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @PostMapping
    public Expense addExpense(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
   }
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @GetMapping("/total")
    public Double getTotalExpense() {
        return expenseService.getTotalExpense();
    }

    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary() {
        return expenseService.getCategorySummary();
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id,
                                 @Valid @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/search")
    public List<Expense> getExpensesByCategory(@RequestParam String category) {
        return expenseService.getExpensesByCategory(category);
    }

    @GetMapping("/date-range")
    public List<Expense> getExpensesBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return expenseService.getExpensesBetweenDates(start, end);
    }

    @GetMapping("/highest")
    public Expense getHighestExpense() {
        return expenseService.getHighestExpense();
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {
        return expenseService.getDashboard();
    }
}