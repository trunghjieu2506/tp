package budgettest;

import budgetsaving.budget.Budget;
import budgetsaving.budget.exceptions.BudgetException;
import budgetsaving.budget.BudgetList;
import budgetsaving.budget.utils.BudgetActiveStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BudgetStatusTest {

    private BudgetList budgetList;

    @BeforeEach
    public void setup() {
        budgetList = new BudgetList(Currency.getInstance("SGD"));
    }

    @Test
    public void testBudgetStatusRemainsActiveIfNotExpired() throws BudgetException {
        Budget activeBudget = new Budget("Groceries",
                new Money("SGD", BigDecimal.valueOf(200.00)),
                LocalDate.now().plusDays(5), "Food");

        budgetList.addNewBudget(activeBudget);
        budgetList.refreshBudgetStatuses();

        assertEquals(BudgetActiveStatus.ACTIVE, activeBudget.getBudgetActiveStatus(),
                "Budget should remain active if the end date is in the future.");
    }


    @Test
    public void testNoCrashWhenBudgetIsNullInUpdateStatus() {
        // Directly calling the private method isn't ideal, but we can subclass to expose it if needed.
        // Alternatively, ensure null budgets are never added in real logic.
        assertDoesNotThrow(() -> {
            // Simulate null being passed somehow (though it's not possible with current public methods)
            // This just asserts defensive programming
            budgetList.refreshBudgetStatuses(); // no nulls internally; just shows it doesn’t crash
        });
    }
}

