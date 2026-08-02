package expense_tracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseResponse {

    private Long id;
    private String category;
    private Double amount;
    private String description;
    private LocalDate date;
}