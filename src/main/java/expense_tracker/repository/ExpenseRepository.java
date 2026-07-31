package expense_tracker.repository;

import expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.time.LocalDate;
import expense_tracker.entity.User;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user")
    Double getTotalExpense(@Param("user") User user);

    @Query("SELECT e.category, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.user = :user " +
            "GROUP BY e.category")
    List<Object[]> getCategorySummary(@Param("user") User user);

    List<Expense> findByCategoryAndUser(String category, User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    Expense findTopByUserOrderByAmountDesc(User user);

    long count();

    List<Expense> findByUser(User user);

    Optional<Expense> findByIdAndUser(Long id, User user);

    Page<Expense> findByUser(User user, Pageable pageable);
}