package budgettest;

import org.junit.jupiter.api.Test;
import budgetsaving.budget.Budget;
import utils.money.Money;
import expenseincome.expense.Expense;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetTest {

    @Test
    public void testBudgetConstructorInvalidArguments() {
        Money validMoney = new Money("USD", BigDecimal.valueOf(1000));
        LocalDate futureDate = LocalDate.now().plusDays(10);

        // Null or empty name
        assertThrows(IllegalArgumentException.class, () ->
                new Budget(null, validMoney, futureDate, "Category")
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("   ", validMoney, futureDate, "Category")
        );

        // Null totalBudget
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", null, futureDate, "Category")
        );

        // Negative totalBudget amount
        Money negativeMoney = new Money("USD", BigDecimal.valueOf(-500));
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", negativeMoney, futureDate, "Category")
        );

        // Null currency in Money
        Money nullCurrencyMoney = new Money((Currency) null, BigDecimal.valueOf(1000));
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", nullCurrencyMoney, futureDate, "Category")
        );

        // Null end date
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", validMoney, null, "Category")
        );

        // Past end date
        LocalDate pastDate = LocalDate.now().minusDays(1);
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", validMoney, pastDate, "Category")
        );

        // Null or empty category
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", validMoney, futureDate, null)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Budget("Budget1", validMoney, futureDate, "   ")
        );
    }

    @Test
    public void testBudgetSettersAndMethods() {
        Money money = new Money("USD", BigDecimal.valueOf(1000));
        LocalDate futureDate = LocalDate.now().plusDays(10);
        Budget budget = new Budget("Budget1", money, futureDate, "Category1");

        // setTotalBudget / setRemainingBudget (valid)
        budget.setTotalBudget(2000);
        // Check that the money amount was updated accordingly
        assertEquals(BigDecimal.valueOf(2000.00), money.getAmount());

        budget.setRemainingBudget(1500);
        assertEquals(0, budget.getRemainingBudget().getAmount().compareTo(BigDecimal.valueOf(1500)));

        // add() and deduct()
        BigDecimal remainingBefore = budget.getRemainingBudget().getAmount();
        budget.add(500);
        assertEquals(remainingBefore.add(BigDecimal.valueOf(500)), budget.getRemainingBudget().getAmount());
        budget.deduct(300);
        assertEquals(remainingBefore.add(BigDecimal.valueOf(200)), budget.getRemainingBudget().getAmount());
    }

    @Test
    public void testModifyBudget() {
        Money money = new Money("USD", BigDecimal.valueOf(1000));
        LocalDate futureDate = LocalDate.now().plusDays(10);
        Budget budget = new Budget("Budget1", money, futureDate, "Category1");

        // Add an expense so money spent is 200
        Expense expense = new Expense("Expense1", new Money("USD",
                BigDecimal.valueOf(200)), LocalDate.now(), "Food");
        budget.deductFromExpense(expense);

        // Trying to set total amount below money spent => IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                budget.modifyBudget(100, "NewName",
                        futureDate.plusDays(5), "NewCategory")
        );
        // Valid modification: only total amount
        budget.modifyBudget(1500, null, null, null);
        // 200 spent => remaining = 1300
        assertEquals(BigDecimal.valueOf(1500.00), money.getAmount());
        assertEquals(BigDecimal.valueOf(1300.00), budget.getRemainingBudget().getAmount());

        // Valid modification: name, endDate, category
        LocalDate newEndDate = futureDate.plusDays(5);
        budget.modifyBudget(0, "UpdatedName", newEndDate, "Updatedcategory");
        assertEquals("UpdatedName", budget.getName());
        assertEquals("Updatedcategory", budget.getCategory());
    }

}
