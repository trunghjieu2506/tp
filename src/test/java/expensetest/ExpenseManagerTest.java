package expensetest;

import expense_income.expense.ExpenseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {
    private ExpenseManager manager;

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
    }

    @Test
    void testAddExpense() {
        manager.addExpense("Lunch", 10.50);
        assertEquals(1, manager.getExpenseCount());
        assertEquals("Lunch", manager.getExpense(0).getDescription());
        assertEquals(10.50, manager.getExpense(0).getAmount(), 0.01);
    }

    @Test
    void testAddExpense_EmptyDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addExpense("", 10.50);
        });
        assertEquals("Expense description cannot be empty.", exception.getMessage());
    }

    @Test
    void testAddExpense_NullDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addExpense(null, 10.50);
        });
        assertEquals("Expense description cannot be empty.", exception.getMessage());
    }

    @Test
    void testAddExpense_ZeroAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addExpense("Lunch", 0);
        });
        assertEquals("Expense amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testAddExpense_NegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addExpense("Lunch", -5);
        });
        assertEquals("Expense amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testDeleteExpense_ValidIndex() {
        manager.addExpense("Lunch", 10.50);
        manager.deleteExpense(1);
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpense_InvalidIndex() {
        manager.addExpense("Lunch", 10.50);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.deleteExpense(2);
        });

        assertEquals("Invalid index: must be between 1 and 1", exception.getMessage());
        assertEquals(1, manager.getExpenseCount()); // Expense should still exist
    }
}

