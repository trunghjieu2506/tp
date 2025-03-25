package expensetest;

import expense_income.expense.Expense;
import expense_income.expense.ExpenseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {
    private ExpenseManager manager;

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
    }

    @Test
    void testAddExpense_WithDate() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        manager.addExpense("Lunch", 10.50, date);
        assertEquals(1, manager.getExpenseCount());
        Expense e = manager.getExpense(0);
        assertEquals("Lunch", e.getDescription());
        assertEquals(10.50, e.getAmount(), 0.01);
        assertEquals(date, e.getDate());
    }

    @Test
    void testAddExpense_DefaultTodayDate() {
        LocalDate today = LocalDate.now();
        manager.addExpense("Coffee", 4.00, today);
        assertEquals(today, manager.getExpense(0).getDate());
    }

    @Test
    void testAddExpense_EmptyDescription() {
        manager.addExpense("", 10.50, LocalDate.now());
        assertEquals(0, manager.getExpenseCount()); // Error handled gracefully
    }

    @Test
    void testAddExpense_NullDescription() {
        manager.addExpense(null, 10.50, LocalDate.now());
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpense_NegativeAmount() {
        manager.addExpense("Lunch", -5, LocalDate.now());
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpense_ValidIndex() {
        manager.addExpense("Lunch", 10.50, LocalDate.now());
        assertEquals(1, manager.getExpenseCount());
        manager.deleteExpense(1);
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpense_InvalidIndex() {
        manager.addExpense("Lunch", 10.50, LocalDate.now());
        manager.deleteExpense(2); // Should print error
        assertEquals(1, manager.getExpenseCount()); // Nothing deleted
    }

    @Test
    void testEditExpense_ValidEdit() {
        LocalDate oldDate = LocalDate.of(2025, 3, 15);
        LocalDate newDate = LocalDate.of(2025, 3, 20);
        manager.addExpense("Lunch", 10.50, oldDate);
        manager.editExpense(1, "Dinner", 15.00, newDate);
        Expense edited = manager.getExpense(0);
        assertEquals("Dinner", edited.getDescription());
        assertEquals(15.00, edited.getAmount(), 0.01);
        assertEquals(newDate, edited.getDate());
    }

    @Test
    void testEditExpense_InvalidIndex() {
        manager.editExpense(1, "Dinner", 15.00, LocalDate.now());
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testSortExpensesByRecent() {
        manager.addExpense("A", 5, LocalDate.of(2023, 1, 1));
        manager.addExpense("B", 10, LocalDate.of(2025, 1, 1));
        manager.addExpense("C", 7, LocalDate.of(2024, 1, 1));

        manager.sortExpensesByDate(true); // most recent first

        assertEquals("B", manager.getExpense(0).getDescription());
        assertEquals("C", manager.getExpense(1).getDescription());
        assertEquals("A", manager.getExpense(2).getDescription());
    }

    @Test
    void testSortExpensesByOldest() {
        manager.addExpense("X", 8, LocalDate.of(2025, 3, 10));
        manager.addExpense("Y", 6, LocalDate.of(2022, 3, 10));
        manager.addExpense("Z", 9, LocalDate.of(2023, 3, 10));

        manager.sortExpensesByDate(false); // oldest first

        assertEquals("Y", manager.getExpense(0).getDescription());
        assertEquals("Z", manager.getExpense(1).getDescription());
        assertEquals("X", manager.getExpense(2).getDescription());
    }
}
