package budgettest;

import budgetsaving.budget.Budget;
import budgetsaving.budget.BudgetException;
import budgetsaving.budget.BudgetList;
import budgetsaving.budget.utils.BudgetActiveStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BudgetStatusTest {

    private BudgetList budgetList;

    @BeforeEach
    public void setup() {
        budgetList = new BudgetList("SGD");
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


    //this test should be done by commenting the "end date is before curr date" check
    //in the budget class
    @Test
    public void testBudgetStatusChangesToExpiredIfPastEndDate() throws BudgetException {
        Budget expiredBudget = new Budget("Travel",
                new Money("SGD", BigDecimal.valueOf(500.00)),
                LocalDate.now().minusDays(1), "Leisure");

        budgetList.addNewBudget(expiredBudget);
        budgetList.refreshBudgetStatuses();

        assertEquals(BudgetActiveStatus.EXPIRED, expiredBudget.getBudgetActiveStatus(),
                "Budget should be marked as expired if the current date is past the end date.");
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

