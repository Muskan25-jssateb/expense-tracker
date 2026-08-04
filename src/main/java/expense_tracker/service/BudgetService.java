package expense_tracker.service;

import expense_tracker.entity.Budget;
import expense_tracker.entity.User;
import expense_tracker.repository.BudgetRepository;
import expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import expense_tracker.dto.BudgetSummary;
import expense_tracker.entity.Expense;
import expense_tracker.repository.ExpenseRepository;
import expense_tracker.dto.MonthlyComparison;

import java.util.HashMap;
import java.util.Map;

import java.time.LocalDate;
import java.util.List;

import java.math.BigDecimal;
import expense_tracker.dto.MLForecastRequest;
import expense_tracker.dto.MLForecastResponse;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final MLForecastService mlForecastService;

    public BudgetService(
            BudgetRepository budgetRepository,
            UserRepository userRepository,
            ExpenseRepository expenseRepository,
            MLForecastService mlForecastService
    ) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.mlForecastService = mlForecastService;
    }

    public Budget setBudget(
            String email,
            BigDecimal amount,
            Integer month,
            Integer year
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Budget budget = budgetRepository
                .findByUserAndMonthAndYear(user, month, year)
                .orElse(new Budget());

        budget.setAmount(amount);
        budget.setMonth(month);
        budget.setYear(year);
        budget.setUser(user);

        return budgetRepository.save(budget);
    }

    public Budget getBudget(
            String email,
            Integer month,
            Integer year
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return budgetRepository
                .findByUserAndMonthAndYear(user, month, year)
                .orElse(null);
    }

    public BudgetSummary getBudgetSummary(
            String email,
            Integer month,
            Integer year
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Budget budget = budgetRepository
                .findByUserAndMonthAndYear(user, month, year)
                .orElse(null);

        BigDecimal budgetAmount =
                budget != null ? budget.getAmount() : BigDecimal.ZERO;

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth()
        );

        List<Expense> expenses =
                expenseRepository.findByUserAndDateBetween(
                        user,
                        startDate,
                        endDate
                );

        BigDecimal spent = expenses.stream()
                .map(expense -> BigDecimal.valueOf(expense.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remaining = budgetAmount.subtract(spent);

        double percentageUsed = 0;

        if (budgetAmount.compareTo(BigDecimal.ZERO) > 0) {
            percentageUsed =
                    spent.divide(
                            budgetAmount,
                            4,
                            java.math.RoundingMode.HALF_UP
                    ).doubleValue() * 100;
        }

        return new BudgetSummary(
                budgetAmount,
                spent,
                remaining,
                percentageUsed
        );
    }

    public MonthlyComparison getMonthlyComparison(
            String email,
            Integer month,
            Integer year
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Current month
        LocalDate currentStart = LocalDate.of(year, month, 1);
        LocalDate currentEnd =
                currentStart.withDayOfMonth(currentStart.lengthOfMonth());

        // Previous month
        LocalDate previousStart = currentStart.minusMonths(1);
        LocalDate previousEnd =
                previousStart.withDayOfMonth(previousStart.lengthOfMonth());

        List<Expense> currentExpenses =
                expenseRepository.findByUserAndDateBetween(
                        user,
                        currentStart,
                        currentEnd
                );

        List<Expense> previousExpenses =
                expenseRepository.findByUserAndDateBetween(
                        user,
                        previousStart,
                        previousEnd
                );

        double currentTotal = currentExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double previousTotal = previousExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double difference = currentTotal - previousTotal;

        double percentageChange = 0;

        if (previousTotal > 0) {
            percentageChange =
                    (difference / previousTotal) * 100;
        }

        Map<String, Double> currentCategories = new HashMap<>();

        for (Expense expense : currentExpenses) {
            currentCategories.merge(
                    expense.getCategory(),
                    expense.getAmount(),
                    Double::sum
            );
        }

        Map<String, Double> previousCategories = new HashMap<>();

        for (Expense expense : previousExpenses) {
            previousCategories.merge(
                    expense.getCategory(),
                    expense.getAmount(),
                    Double::sum
            );
        }

        return new MonthlyComparison(
                currentTotal,
                previousTotal,
                difference,
                percentageChange,
                currentCategories,
                previousCategories
        );
    }

    public MLForecastResponse getMLForecast(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        LocalDate today = LocalDate.now();

        LocalDate currentMonthStart =
                today.withDayOfMonth(1);

        LocalDate previousMonthStart =
                currentMonthStart.minusMonths(1);

        LocalDate previousMonthEnd =
                currentMonthStart.minusDays(1);


        List<Expense> currentExpenses =
                expenseRepository.findByUserAndDateBetween(
                        user,
                        currentMonthStart,
                        today
                );

        double spentSoFar = currentExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();


        List<Expense> previousExpenses =
                expenseRepository.findByUserAndDateBetween(
                        user,
                        previousMonthStart,
                        previousMonthEnd
                );

        double previousMonthSpend = previousExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();


        Budget budget = budgetRepository
                .findByUserAndMonthAndYear(
                        user,
                        today.getMonthValue(),
                        today.getYear()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Set a monthly budget before generating a forecast"
                        )
                );


        long transactionCount =
                expenseRepository.countByUserAndDateBetween(
                        user,
                        currentMonthStart,
                        today
                );


        MLForecastRequest request =
                new MLForecastRequest(
                        today.getDayOfMonth(),
                        spentSoFar,
                        previousMonthSpend,
                        budget.getAmount().doubleValue(),
                        (int) transactionCount
                );


        return mlForecastService.getPrediction(request);
    }

}