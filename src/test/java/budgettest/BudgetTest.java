package budgettest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import budget_saving.budget.BudgetList;
import budget_saving.budget.command.BudgetGeneralCommand;
import cashflow.model.interfaces.BudgetManager;

public class BudgetTest {
    private BudgetManager budgetManager;

    @BeforeEach
    public void setup() {
        budgetManager = new BudgetList("USD");
    }

    @Test
    public void testUnknownCommand() {
        // Input that does not match any known command keyword.
        BudgetGeneralCommand command = new BudgetGeneralCommand("unknown", budgetManager);
        command.execute();
        // No budget should be added; you might capture output if needed.
        assertThrows(IndexOutOfBoundsException.class, () -> {
            ((BudgetList) budgetManager).getBudget(0);
        });
    }

    @Test
    public void testSetCommandViaGeneralCommand() {
        // Example input for setting a budget; adjust the format as required by BudgetParser.
        String input = "set n/TestBudget a/1000 e/2025-12-31 c/TestCategory";
        BudgetGeneralCommand command = new BudgetGeneralCommand(input, budgetManager);
        command.execute();
        assertEquals("TestBudget", ((BudgetList) budgetManager).getBudget(0).getName());
    }
}
