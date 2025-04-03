package expensetest;

import cashflow.model.FinanceData;
import expenseincome.expense.Expense;
import expenseincome.expense.ExpenseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseManagerTest {
    private ExpenseManager manager;

    @BeforeEach
    void setUp() {
        FinanceData data = new FinanceData();
        data.setCurrency("USD");
        manager = new ExpenseManager(data, "USD");
    }

    @Test
    void testAddExpenseWithDate() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        manager.addExpense("Lunch", 10.50, date, "Food");
        assertEquals(1, manager.getExpenseCount());
        Expense e = manager.getExpense(0);
        assertEquals("Lunch", e.getDescription());
        assertEquals(BigDecimal.valueOf(10.50).setScale(2), e.getAmount().getAmount());
        assertEquals(date, e.getDate());
        assertEquals("Food", e.getCategory());
    }

    @Test
    void testAddExpenseDefaultTodayDate() {
        LocalDate today = LocalDate.now();
        manager.addExpense("Coffee", 4.00, today, "Drink");
        assertEquals(today, manager.getExpense(0).getDate());
        assertEquals("Drink", manager.getExpense(0).getCategory());
    }

    @Test
    void testAddExpenseEmptyDescription() {
        manager.addExpense("", 10.50, LocalDate.now(), "Other");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpenseNullDescription() {
        manager.addExpense(null, 10.50, LocalDate.now(), "Other");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpenseNegativeAmount() {
        manager.addExpense("Lunch", -5, LocalDate.now(), "Food");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testAddExpenseEmptyCategory() {
        manager.addExpense("Lunch", 5.00, LocalDate.now(), "");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpenseValidIndex() {
        manager.addExpense("Lunch", 10.50, LocalDate.now(), "Food");
        assertEquals(1, manager.getExpenseCount());
        manager.deleteExpense(1);
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testDeleteExpenseInvalidIndex() {
        manager.addExpense("Lunch", 10.50, LocalDate.now(), "Food");
        manager.deleteExpense(2);
        assertEquals(1, manager.getExpenseCount());
    }

    @Test
    void testEditExpenseValidEdit() {
        LocalDate oldDate = LocalDate.of(2025, 3, 15);
        LocalDate newDate = LocalDate.of(2025, 3, 20);
        manager.addExpense("Lunch", 10.50, oldDate, "Food");
        manager.editExpense(1, "Dinner", 15.00, newDate, "Dinner");
        Expense edited = manager.getExpense(0);
        assertEquals("Dinner", edited.getDescription());
        assertEquals(BigDecimal.valueOf(10.50).setScale(2), edited.getAmount().getAmount());
        assertEquals(newDate, edited.getDate());
        assertEquals("Dinner", edited.getCategory());
    }

    @Test
    void testEditExpenseInvalidIndex() {
        manager.editExpense(1, "Dinner", 15.00, LocalDate.now(), "Food");
        assertEquals(0, manager.getExpenseCount());
    }

    @Test
    void testSortExpensesByRecent() {
        manager.addExpense("A", 5, LocalDate.of(2023, 1, 1), "Misc");
        manager.addExpense("B", 10, LocalDate.of(2025, 1, 1), "Misc");
        manager.addExpense("C", 7, LocalDate.of(2024, 1, 1), "Misc");

        manager.sortExpensesByDate(true);

        assertEquals("B", manager.getExpense(0).getDescription());
        assertEquals("C", manager.getExpense(1).getDescription());
        assertEquals("A", manager.getExpense(2).getDescription());
    }

    @Test
    void testSortExpensesByOldest() {
        manager.addExpense("X", 8, LocalDate.of(2025, 3, 10), "Food");
        manager.addExpense("Y", 6, LocalDate.of(2022, 3, 10), "Food");
        manager.addExpense("Z", 9, LocalDate.of(2023, 3, 10), "Food");

        manager.sortExpensesByDate(false);

        assertEquals("Y", manager.getExpense(0).getDescription());
        assertEquals("Z", manager.getExpense(1).getDescription());
        assertEquals("X", manager.getExpense(2).getDescription());
    }

    @Test
    void testAddExpense_WithDate_UsingMoney() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        Currency currency = Currency.getInstance("SGD");
        Money money = new Money(currency, 10.50);
        Expense expense = new Expense("Lunch", money, date, "Food");
        manager.getList().add(expense);
        assertEquals(1, manager.getExpenseCount());
        Expense e = manager.getExpense(0);
        assertEquals("Lunch", e.getDescription());
        assertEquals(money.getAmount(), e.getAmount().getAmount());
        assertEquals(date, e.getDate());
        assertEquals("Food", e.getCategory());
    }

}