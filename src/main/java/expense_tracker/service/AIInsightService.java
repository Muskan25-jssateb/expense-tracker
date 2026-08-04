package expense_tracker.service;

import expense_tracker.dto.BudgetSummary;
import expense_tracker.dto.MonthlyComparison;
import expense_tracker.dto.MLForecastResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AIInsightService {

    private final GeminiService geminiService;
    private final BudgetService budgetService;

    public AIInsightService(
            GeminiService geminiService,
            BudgetService budgetService
    ) {
        this.geminiService = geminiService;
        this.budgetService = budgetService;
    }


    public String generateSpendingInsight(String email) {

        LocalDate today = LocalDate.now();

        int month = today.getMonthValue();
        int year = today.getYear();


        BudgetSummary budgetSummary =
                budgetService.getBudgetSummary(
                        email,
                        month,
                        year
                );


        MonthlyComparison comparison =
                budgetService.getMonthlyComparison(
                        email,
                        month,
                        year
                );


        MLForecastResponse forecast = null;

        try {
            forecast = budgetService.getMLForecast(email);
        } catch (Exception e) {
            System.err.println(
                    "ML forecast unavailable: " + e.getMessage()
            );
        }
        String forecastText;

        if (forecast != null) {

            forecastText = String.format(
                    """
                    - Predicted month-end spending: ₹%.2f
                    - Monthly budget: ₹%.2f
                    - Predicted difference: ₹%.2f
                    - Predicted to exceed budget: %s
                    """,
                    forecast.getPredicted_month_end_spending(),
                    forecast.getMonthly_budget(),
                    forecast.getPredicted_difference(),
                    forecast.isWill_exceed_budget()
            );

        } else {

            forecastText =
                    "Machine learning forecast is currently unavailable.";
        }


        String prompt = """
        You are an AI spending assistant inside a personal expense tracker.

        Analyze the user's financial data below and provide useful,
        practical spending insights.

        Current month financial data:
        - Monthly budget: ₹%s
        - Amount spent so far: ₹%s
        - Remaining budget: ₹%s
        - Budget used: %.2f%%

        Month-over-month comparison:
        - Current month spending: ₹%.2f
        - Previous month spending: ₹%.2f
        - Spending difference: ₹%.2f
        - Percentage change: %.2f%%

        Category spending this month:
        %s

        Category spending last month:
        %s

        Machine learning forecast:
        %s

        Instructions:
        - Give 3 short personalized spending insights.
        - Mention important category changes when relevant.
        - Use the ML forecast to warn about possible overspending when relevant.
        - Give practical recommendations.
        - Do not invent financial information.
        - Do not give investment, credit, tax, or legal advice.
        - Keep the response concise and easy to understand.
        - Do not assume whether a spending category is essential or non-essential unless the data explicitly says so.
        - Distinguish clearly between observed spending data and machine-learning predictions.
        - Use Indian Rupees (₹).
        """.formatted(
                budgetSummary.getBudget(),
                budgetSummary.getSpent(),
                budgetSummary.getRemaining(),
                budgetSummary.getPercentageUsed(),

                comparison.getCurrentMonthTotal(),
                comparison.getPreviousMonthTotal(),
                comparison.getDifference(),
                comparison.getPercentageChange(),

                comparison.getCurrentMonthCategories(),
                comparison.getPreviousMonthCategories(),

                forecastText
        );

        try {
            return geminiService.generateContent(prompt);

        } catch (Exception e) {

            System.err.println(
                    "Gemini AI unavailable: " + e.getMessage()
            );

            return """
            AI spending insights are temporarily unavailable.
            Your budget and expense analytics are still available on the dashboard.
            Please try generating insights again later.
            """;
        }
    }
}