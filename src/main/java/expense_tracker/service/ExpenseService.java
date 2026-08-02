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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

        User user = getLoggedInUser();

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

    public List<ExpenseResponse> getAllExpenses() {

        User user = getLoggedInUser();

        List<Expense> expenses = expenseRepository.findByUser(user);

        List<ExpenseResponse> responseList = new java.util.ArrayList<>();

        for (Expense expense : expenses) {

            ExpenseResponse response = new ExpenseResponse();

            response.setId(expense.getId());
            response.setCategory(expense.getCategory());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setDate(expense.getDate());

            responseList.add(response);
        }

        return responseList;
    }

    public ExpenseResponse getExpenseById(Long id) {

        User user = getLoggedInUser();

        System.out.println("========== DEBUG ==========");
        System.out.println("Logged in user: " + user.getEmail());
        System.out.println("Requested Expense ID: " + id);

        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> {
                    System.out.println("Expense NOT found for this user");
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Expense not found"
                    );
                });

        System.out.println("Expense FOUND!");

        ExpenseResponse response = new ExpenseResponse();

        response.setId(expense.getId());
        response.setCategory(expense.getCategory());
        response.setAmount(expense.getAmount());
        response.setDescription(expense.getDescription());
        response.setDate(expense.getDate());

        return response;
    }
//    public ExpenseResponse getExpenseById(Long id) {
//
//        User user = getLoggedInUser();
//
//        Expense expense = expenseRepository.findByIdAndUser(id, user)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Expense not found"
//                ));
//
//        ExpenseResponse response = new ExpenseResponse();
//
//        response.setId(expense.getId());
//        response.setCategory(expense.getCategory());
//        response.setAmount(expense.getAmount());
//        response.setDescription(expense.getDescription());
//        response.setDate(expense.getDate());
//
//        return response;
//    }

    public ExpenseResponse updateExpense(Long id, Expense updatedExpense) {

        User user = getLoggedInUser();

        Expense expense = expenseRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found"
                ));

        expense.setCategory(updatedExpense.getCategory());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDescription(updatedExpense.getDescription());
        expense.setDate(updatedExpense.getDate());

        Expense savedExpense = expenseRepository.save(expense);

        ExpenseResponse response = new ExpenseResponse();

        response.setId(savedExpense.getId());
        response.setCategory(savedExpense.getCategory());
        response.setAmount(savedExpense.getAmount());
        response.setDescription(savedExpense.getDescription());
        response.setDate(savedExpense.getDate());

        return response;
    }
    public void deleteExpense(Long id) {

        User user = getLoggedInUser();

        Expense expense = expenseRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found"
                ));

        expenseRepository.delete(expense);
    }

    public Double getTotalExpense() {

        User user = getLoggedInUser();

        Double total = expenseRepository.getTotalExpense(user);

        if (total == null) {
            return 0.0;
        }

        return total;
    }

//    public Double getTotalExpense() {
//        return expenseRepository.getTotalExpense();
//    }

    public Map<String, Double> getCategorySummary() {

        User user = getLoggedInUser();

        List<Object[]> results = expenseRepository.getCategorySummary(user);

        Map<String, Double> summary = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Double total = (Double) row[1];

            summary.put(category, total);
        }

        return summary;
    }

    public List<ExpenseResponse> getExpensesByCategory(String category) {

        User user = getLoggedInUser();

        List<Expense> expenses = expenseRepository.findByCategoryAndUser(category, user);

        List<ExpenseResponse> responseList = new java.util.ArrayList<>();

        for (Expense expense : expenses) {

            ExpenseResponse response = new ExpenseResponse();

            response.setId(expense.getId());
            response.setCategory(expense.getCategory());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setDate(expense.getDate());

            responseList.add(response);
        }

        return responseList;
    }

    public List<ExpenseResponse> getExpensesBetweenDates(LocalDate start, LocalDate end) {

        User user = getLoggedInUser();

        List<Expense> expenses =
                expenseRepository.findByUserAndDateBetween(user, start, end);

        List<ExpenseResponse> responseList = new java.util.ArrayList<>();

        for (Expense expense : expenses) {

            ExpenseResponse response = new ExpenseResponse();

            response.setId(expense.getId());
            response.setCategory(expense.getCategory());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setDate(expense.getDate());

            responseList.add(response);
        }

        return responseList;
    }

    public ExpenseResponse getHighestExpense() {

        User user = getLoggedInUser();

        Expense expense = expenseRepository.findTopByUserOrderByAmountDesc(user);

        if (expense == null) {
            return null;
        }

        ExpenseResponse response = new ExpenseResponse();

        response.setId(expense.getId());
        response.setCategory(expense.getCategory());
        response.setAmount(expense.getAmount());
        response.setDescription(expense.getDescription());
        response.setDate(expense.getDate());

        return response;
    }

    public DashboardResponse getDashboard() {

        User user = getLoggedInUser();

        DashboardResponse dashboard = new DashboardResponse();

        dashboard.setTotalExpense(getTotalExpense());

        dashboard.setExpenseCount((long) expenseRepository.findByUser(user).size());

        dashboard.setHighestExpense(getHighestExpense());

        dashboard.setCategorySummary(getCategorySummary());

        return dashboard;
    }

    public Page<ExpenseResponse> getExpenses(Pageable pageable) {

        User user = getLoggedInUser();

        Page<Expense> expenses = expenseRepository.findByUser(user, pageable);

        return expenses.map(expense -> {

            ExpenseResponse response = new ExpenseResponse();

            response.setId(expense.getId());
            response.setCategory(expense.getCategory());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setDate(expense.getDate());

            return response;
        });
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }


}
