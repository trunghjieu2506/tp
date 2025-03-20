package incometest;

import expense_income.income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IncomeManagerTest {
    private IncomeManager manager;

    @BeforeEach
    void setUp() {
        manager = new IncomeManager();
    }

    @Test
    void testAddIncome() {
        manager.addIncome("Salary", 2000.00);
        assertEquals(1, manager.getIncomeCount());
        assertEquals("Salary", manager.getIncome(0).getSource());
        assertEquals(2000.00, manager.getIncome(0).getAmount(), 0.01);
    }

    @Test
    void testAddIncome_EmptySource() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addIncome("", 100.00);
        });
        assertEquals("Income source cannot be empty.", exception.getMessage());
    }

    @Test
    void testAddIncome_NullSource() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addIncome(null, 100.00);
        });
        assertEquals("Income source cannot be empty.", exception.getMessage());
    }

    @Test
    void testAddIncome_ZeroAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addIncome("Salary", 0);
        });
        assertEquals("Income amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testAddIncome_NegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addIncome("Salary", -200);
        });
        assertEquals("Income amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testDeleteIncome_ValidIndex() {
        manager.addIncome("Salary", 2000.00);
        manager.deleteIncome(1);
        assertEquals(0, manager.getIncomeCount());
    }

    @Test
    void testDeleteIncome_InvalidIndex() {
        manager.addIncome("Salary", 2000.00);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.deleteIncome(2); // Invalid index
        });

        assertEquals("Invalid index: must be between 1 and 1", exception.getMessage());
        assertEquals(1, manager.getIncomeCount()); // Ensure income was not deleted
    }
}

