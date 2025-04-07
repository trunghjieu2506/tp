package incometest;

import cashflow.model.FinanceData;
import cashflow.model.storage.DummyStorage;
import expenseincome.income.HandleIncomeCommand;
import expenseincome.income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the handle() method of HandleIncomeCommand using simulated user input.
 */
public class IncomeHandleTest {

    private IncomeManager manager;

    @BeforeEach
    void setUp() throws Exception {
        FinanceData data = new FinanceData();
        data.setCurrency("USD");
        DummyStorage dummyStorage = new DummyStorage();
        manager = new IncomeManager(data, "USD", dummyStorage);
    }

    @Test
    public void testHandleIncomeCommand_addAndExit() {
        String simulatedInput = "add Salary 5000 Job 2025-04-01\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        HandleIncomeCommand.handle(scanner, manager);

        assertEquals(1, manager.getIncomeCount(), "Income should be added correctly.");
    }
}
