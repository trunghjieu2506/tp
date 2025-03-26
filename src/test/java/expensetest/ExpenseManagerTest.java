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
        manager.addExpense("Lunch", 10.50, date, "Food");
        assertEquals(1, manager.getExpenseCount());
        Expense e = manager.getExpense(0);
        assertEquals("Lunch", e.getDescription());
        assertEquals(10.50, e.getAmount(), 0.01);
        assertEquals(date, e.getDate());
        assertEquals("Food", e.getCategory());
    }

    @Test
    void testAddExpense_DefaultTodayDate() {
        LocalDate today = LocalDate.now();
        manager.addExpense("Coffee", 4.00, today, "Drink");
        assertEquals(today, manager.getExpense(0).getDate());
        assertEquals("Drink", manager.getExpense(0).getCategory());
    }

    @Test
    void testAddExpense_EmptyDescription() {
        manager.addExpense("", 10.50, LocalDate.now(), "Other");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpense_NullDescription() {
        manager.addExpense(null, 10.50, LocalDate.now(), "Other");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpense_NegativeAmount() {
        manager.addExpense("Lunch", -5, LocalDate.now(), "Food");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpense_EmptyCategory() {
        manager.addExpense("Lunch", 5.00, LocalDate.now(), "");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpense_ValidIndex() {
        manager.addExpense("Lunch", 10.50, LocalDate.now(), "Food");
        assertEquals(1, manager.getExpenseCount());
        manager.deleteExpense(1);
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpense_InvalidIndex() {
        manager.addExpense("Lunch", 10.50, LocalDate.now(), "Food");
        manager.deleteExpense(2);
        assertEquals(1, manager.getExpenseCount());
    }

    @Test
    void testEditExpense_ValidEdit() {
        LocalDate oldDate = LocalDate.of(2025, 3, 15);
        LocalDate newDate = LocalDate.of(2025, 3, 20);
        manager.addExpense("Lunch", 10.50, oldDate, "Food");
        manager.editExpense(1, "Dinner", 15.00, newDate, "Dinner");
        Expense edited = manager.getExpense(0);
        assertEquals("Dinner", edited.getDescription());
        assertEquals(15.00, edited.getAmount(), 0.01);
        assertEquals(newDate, edited.getDate());
        assertEquals("Dinner", edited.getCategory());
    }

    @Test
    void testEditExpense_InvalidIndex() {
        manager.editExpense(1, "Dinner", 15.00, LocalDate.now(), "Food");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testSortExpensesByRecent() {
        manager.addExpense("A", 5, LocalDate.of(2023, 1, 1), "Misc");
        manager.addExpense("B", 10, LocalDate.of(2025, 1, 1), "Misc");
        manager.addExpense("C", 7, LocalDate.of(2024, 1, 1), "Misc");

        manager.sortExpensesByDate(true); // most recent first

        assertEquals("B", manager.getExpense(0).getDescription());
        assertEquals("C", manager.getExpense(1).getDescription());
        assertEquals("A", manager.getExpense(2).getDescription());
    }

    @Test
    void testSortExpensesByOldest() {
        manager.addExpense("X", 8, LocalDate.of(2025, 3, 10), "Food");
        manager.addExpense("Y", 6, LocalDate.of(2022, 3, 10), "Food");
        manager.addExpense("Z", 9, LocalDate.of(2023, 3, 10), "Food");

        manager.sortExpensesByDate(false); // oldest first

        assertEquals("Y", manager.getExpense(0).getDescription());
        assertEquals("Z", manager.getExpense(1).getDescription());
        assertEquals("X", manager.getExpense(2).getDescription());
    }
}
