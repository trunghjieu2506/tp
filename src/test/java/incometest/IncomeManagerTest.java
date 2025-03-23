package incometest;

import expense_income.income.Income;
import expense_income.income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IncomeManagerTest {
    private IncomeManager manager;

    @BeforeEach
    void setUp() {
        manager = new IncomeManager();
    }

    @Test
    void testAddIncome_WithDate() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        manager.addIncome("Salary", 2000.00, date);
        assertEquals(1, manager.getIncomeCount());
        Income i = manager.getIncome(0);
        assertEquals("Salary", i.getSource());
        assertEquals(2000.00, i.getAmount(), 0.01);
        assertEquals(date, i.getDate());
    }

    @Test
    void testAddIncome_TodayDate() {
        LocalDate today = LocalDate.now();
        manager.addIncome("Bonus", 500.00, today);
        assertEquals(today, manager.getIncome(0).getDate());
    }

    @Test
    void testAddIncome_EmptySource() {
        manager.addIncome("", 100.00, LocalDate.now());
        assertEquals(0, manager.getIncomeCount()); // Graceful failure
    }

    @Test
    void testAddIncome_NullSource() {
        manager.addIncome(null, 100.00, LocalDate.now());
        assertEquals(0, manager.getIncomeCount());
    }

    @Test
    void testAddIncome_ZeroAmount() {
        manager.addIncome("Test", 0, LocalDate.now());
        assertEquals(0, manager.getIncomeCount());
    }

    @Test
    void testAddIncome_NegativeAmount() {
        manager.addIncome("Freelance", -100, LocalDate.now());
        assertEquals(0, manager.getIncomeCount());
    }

    @Test
    void testDeleteIncome_ValidIndex() {
        manager.addIncome("Salary", 2000.00, LocalDate.now());
        assertEquals(1, manager.getIncomeCount());
        manager.deleteIncome(1);
        assertEquals(0, manager.getIncomeCount());
    }

    @Test
    void testDeleteIncome_InvalidIndex() {
        manager.addIncome("Salary", 2000.00, LocalDate.now());
        manager.deleteIncome(2); // Should not delete
        assertEquals(1, manager.getIncomeCount());
    }

    @Test
    void testEditIncome_ValidUpdate() {
        manager.addIncome("Part-Time", 800.00, LocalDate.of(2024, 1, 1));
        manager.editIncome(1, "Internship", 1000.00, LocalDate.of(2025, 1, 1));
        Income updated = manager.getIncome(0);
        assertEquals("Internship", updated.getSource());
        assertEquals(1000.00, updated.getAmount(), 0.01);
        assertEquals(LocalDate.of(2025, 1, 1), updated.getDate());
    }

    @Test
    void testEditIncome_InvalidIndex() {
        manager.editIncome(1, "New", 123.45, LocalDate.now());
        assertEquals(0, manager.getIncomeCount()); // No income was edited
    }

    @Test
    void testSortIncomesByRecent() {
        manager.addIncome("A", 100, LocalDate.of(2023, 1, 1));
        manager.addIncome("B", 200, LocalDate.of(2025, 1, 1));
        manager.addIncome("C", 150, LocalDate.of(2024, 1, 1));

        manager.sortIncomesByDate(true); // Most recent first

        assertEquals("B", manager.getIncome(0).getSource());
        assertEquals("C", manager.getIncome(1).getSource());
        assertEquals("A", manager.getIncome(2).getSource());
    }

    @Test
    void testSortIncomesByOldest() {
        manager.addIncome("X", 50, LocalDate.of(2025, 3, 1));
        manager.addIncome("Y", 60, LocalDate.of(2022, 3, 1));
        manager.addIncome("Z", 70, LocalDate.of(2023, 3, 1));

        manager.sortIncomesByDate(false); // Oldest first

        assertEquals("Y", manager.getIncome(0).getSource());
        assertEquals("Z", manager.getIncome(1).getSource());
        assertEquals("X", manager.getIncome(2).getSource());
    }
}
