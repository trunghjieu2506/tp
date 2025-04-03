package incometest;

import cashflow.model.FinanceData;
import expenseincome.income.IncomeManager;
import expenseincome.income.commands.AddIncomeCommand;
import expenseincome.income.commands.DeleteIncomeCommand;
import expenseincome.income.commands.EditIncomeCommand;
import expenseincome.income.commands.ListCategoryIncomeCommand;
import expenseincome.income.commands.ListIncomeCommand;
import expenseincome.income.commands.SortIncomeCommand;
import expenseincome.income.commands.HelpIncomeCommand;
import expenseincome.income.commands.TopCategoryIncomeCommand;
import expenseincome.income.commands.BottomCategoryIncomeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeCommandTestSuite {
    private IncomeManager incomeManager;

    @BeforeEach
    void setUp() {
        FinanceData data = new FinanceData();
        data.setCurrency("USD");
        incomeManager = new IncomeManager(data,"USD");
    }

    @Test
    void testAddIncomeCommandExecute() {
        AddIncomeCommand command = new AddIncomeCommand(
                "Salary", 3000.0, LocalDate.of(2025, 3, 26), "Job");
        command.execute(incomeManager);
        assertEquals(1, incomeManager.getIncomeCount());
        assertEquals("Salary", incomeManager.getIncome(0).getSource());
    }

    @Test
    void testDeleteIncomeCommandExecute() {
        incomeManager.addIncome("Bonus", 500.0, LocalDate.now(), "Job");
        DeleteIncomeCommand command = new DeleteIncomeCommand(1);
        command.execute(incomeManager);
        assertEquals(0, incomeManager.getIncomeCount());
    }

    @Test
    void testEditIncomeCommandExecute() {
        incomeManager.addIncome("Gift", 100.0, LocalDate.now(), "Gift");
        EditIncomeCommand command = new EditIncomeCommand(
                1, "Investment", 200.0, LocalDate.of(2025, 3, 27), "Finance");
        command.execute(incomeManager);
        assertEquals("Investment", incomeManager.getIncome(0).getSource());
        assertEquals(200.0, incomeManager.getIncome(0).getAmount());
    }

    @Test
    void testListIncomeCommandExecute() {
        incomeManager.addIncome("Allowance", 100.0, LocalDate.now(), "Personal");
        ListIncomeCommand command = new ListIncomeCommand();
        command.execute(incomeManager);
    }

    @Test
    void testListCategoryIncomeCommandExecute() {
        incomeManager.addIncome("Part-time", 800.0, LocalDate.now(), "Work");
        ListCategoryIncomeCommand command = new ListCategoryIncomeCommand("Work");
        command.execute(incomeManager);
    }

    @Test
    void testSortIncomeCommandRecent() {
        incomeManager.addIncome("A", 1, LocalDate.of(2023, 1, 1), "Misc");
        incomeManager.addIncome("B", 2, LocalDate.of(2025, 1, 1), "Misc");
        SortIncomeCommand command = new SortIncomeCommand(true);
        command.execute(incomeManager);
        assertEquals("B", incomeManager.getIncome(0).getSource());
    }

    @Test
    void testSortIncomeCommandOldest() {
        incomeManager.addIncome("X", 1, LocalDate.of(2025, 3, 10), "Job");
        incomeManager.addIncome("Y", 1, LocalDate.of(2022, 3, 10), "Job");
        SortIncomeCommand command = new SortIncomeCommand(false);
        command.execute(incomeManager);
        assertEquals("Y", incomeManager.getIncome(0).getSource());
    }

    @Test
    void testHelpIncomeCommandExecute() {
        HelpIncomeCommand command = new HelpIncomeCommand();
        command.execute(incomeManager); // should print help text without errors
    }

    @Test
    void testTopCategoryIncomeCommandExecute() {
        incomeManager.addIncome("Paycheck", 3000.0, LocalDate.now(), "Job");
        incomeManager.addIncome("Freelance", 2000.0, LocalDate.now(), "Freelance");
        incomeManager.addIncome("Bonus", 1000.0, LocalDate.now(), "Job");
        TopCategoryIncomeCommand command = new TopCategoryIncomeCommand();
        command.execute(incomeManager); // should output "Job"
    }

    @Test
    void testBottomCategoryIncomeCommandExecute() {
        incomeManager.addIncome("Paycheck", 3000.0, LocalDate.now(), "Job");
        incomeManager.addIncome("Freelance", 200.0, LocalDate.now(), "Freelance");
        BottomCategoryIncomeCommand command = new BottomCategoryIncomeCommand();
        command.execute(incomeManager); // should output "Freelance"
    }

}
