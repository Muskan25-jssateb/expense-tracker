package expense_tracker.controller;

import expense_tracker.service.AIInsightService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIInsightController {

    private final AIInsightService aiInsightService;

    public AIInsightController(
            AIInsightService aiInsightService
    ) {
        this.aiInsightService = aiInsightService;
    }

    @GetMapping("/insights")
    public String getSpendingInsights(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return aiInsightService.generateSpendingInsight(email);
    }
}