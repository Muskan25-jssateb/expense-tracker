package expense_tracker.repository;

import expense_tracker.entity.Budget;
import expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserAndMonthAndYear(
            User user,
            Integer month,
            Integer year
    );
}