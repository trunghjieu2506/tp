package expensetest;

import cashflow.model.FinanceData;
import cashflow.model.storage.DummyStorage;
import expenseincome.expense.ExpenseManager;
import expenseincome.expense.HandleExpenseCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the handle() method of HandleExpenseCommand using simulated user input.
 */
public class ExpenseHandleTest {

    private ExpenseManager manager;

    @BeforeEach
    void setUp() throws Exception {
        FinanceData data = new FinanceData();
        data.setCurrency("USD");
        DummyStorage dummyStorage = new DummyStorage();
        manager = new ExpenseManager(data, "USD", dummyStorage);
    }

    @Test
    public void testHandleExpenseCommand_addAndExit() {
        String simulatedInput = "add Lunch 5 Food 2025-04-01\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        HandleExpenseCommand.handle(scanner, manager);

        assertEquals(1, manager.getExpenseCount(), "Expense should be added correctly.");
    }
}
