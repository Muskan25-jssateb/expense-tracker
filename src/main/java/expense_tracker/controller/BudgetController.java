package expense_tracker.controller;

import expense_tracker.entity.Budget;
import expense_tracker.service.BudgetService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import expense_tracker.dto.BudgetSummary;
import expense_tracker.dto.MonthlyComparison;
import java.math.BigDecimal;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public Budget setBudget(
            @RequestParam BigDecimal amount,
            @RequestParam Integer month,
            @RequestParam Integer year,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return budgetService.setBudget(
                email,
                amount,
                month,
                year
        );
    }

    @GetMapping
    public Budget getBudget(
            @RequestParam Integer month,
            @RequestParam Integer year,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return budgetService.getBudget(
                email,
                month,
                year
        );
    }

    @GetMapping("/summary")
    public BudgetSummary getBudgetSummary(
            @RequestParam Integer month,
            @RequestParam Integer year,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return budgetService.getBudgetSummary(
                email,
                month,
                year
        );
    }

    @GetMapping("/comparison")
    public MonthlyComparison getMonthlyComparison(
            @RequestParam Integer month,
            @RequestParam Integer year,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return budgetService.getMonthlyComparison(
                email,
                month,
                year
        );
    }
}