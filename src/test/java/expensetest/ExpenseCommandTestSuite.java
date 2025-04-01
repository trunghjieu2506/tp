package expensetest;

import expenseincome.expense.ExpenseManager;
import expenseincome.expense.commands.AddExpenseCommand;
import expenseincome.expense.commands.DeleteExpenseCommand;
import expenseincome.expense.commands.EditExpenseCommand;
import expenseincome.expense.commands.ListAllCommand;
import expenseincome.expense.commands.ListCategoryExpenseCommand;
import expenseincome.expense.commands.ListExpenseCommand;
import expenseincome.expense.commands.SortExpenseCommand;
import expenseincome.expense.commands.HelpExpenseCommand;
import expenseincome.expense.commands.TopCategoryExpenseCommand;
import expenseincome.expense.commands.BottomCategoryExpenseCommand;
import expenseincome.income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseCommandTestSuite {
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;

    @BeforeEach
    void setUp() {
        expenseManager = new ExpenseManager();
        incomeManager = new IncomeManager();
    }

    @Test
    void testAddExpenseCommandExecute() {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Lunch", 12.5, LocalDate.of(2025, 3, 26), "Food");
        cmd.execute(expenseManager);
        assertEquals(1, expenseManager.getExpenseCount());
        assertEquals("Lunch", expenseManager.getExpense(0).getDescription());
    }

    @Test
    void testDeleteExpenseCommandExecute() {
        expenseManager.addExpense("Test", 10.0, LocalDate.now(), "Food");
        DeleteExpenseCommand cmd = new DeleteExpenseCommand(1);
        cmd.execute(expenseManager);
        assertEquals(0, expenseManager.getExpenseCount());
    }

    @Test
    void testEditExpenseCommandExecute() {
        expenseManager.addExpense("Old", 5.0, LocalDate.now(), "OldCat");
        EditExpenseCommand cmd = new EditExpenseCommand(
                1, "New", 9.0, LocalDate.of(2024, 1, 1), "NewCat");
        cmd.execute(expenseManager);
        assertEquals("New", expenseManager.getExpense(0).getDescription());
        assertEquals("NewCat", expenseManager.getExpense(0).getCategory());
    }

    @Test
    void testListExpenseCommandExecute() {
        ListExpenseCommand cmd = new ListExpenseCommand();
        cmd.execute(expenseManager); // should not crash
    }

    @Test
    void testListCategoryExpenseCommandExecute() {
        expenseManager.addExpense("Coffee", 3.5, LocalDate.now(), "Drink");
        ListCategoryExpenseCommand cmd = new ListCategoryExpenseCommand("Drink");
        cmd.execute(expenseManager); // should not crash
    }

    @Test
    void testSortExpenseCommandRecent() {
        expenseManager.addExpense("A", 1, LocalDate.of(2023, 1, 1), "Food");
        expenseManager.addExpense("B", 2, LocalDate.of(2025, 1, 1), "Food");
        SortExpenseCommand cmd = new SortExpenseCommand(true);
        cmd.execute(expenseManager);
        assertEquals("B", expenseManager.getExpense(0).getDescription());
    }

    @Test
    void testSortExpenseCommandOldest() {
        expenseManager.addExpense("X", 1, LocalDate.of(2025, 3, 10), "Food");
        expenseManager.addExpense("Y", 1, LocalDate.of(2022, 3, 10), "Food");
        SortExpenseCommand cmd = new SortExpenseCommand(false);
        cmd.execute(expenseManager);
        assertEquals("Y", expenseManager.getExpense(0).getDescription());
    }

    @Test
    void testListAllCommandExecute() {
        ListAllCommand cmd = new ListAllCommand(incomeManager);
        cmd.execute(expenseManager); // should not crash even with empty data
    }

    @Test
    void testHelpExpenseCommandExecute() {
        HelpExpenseCommand cmd = new HelpExpenseCommand();
        cmd.execute(expenseManager); // should print help text without errors
    }

    @Test
    void testTopCategoryExpenseCommandExecute() {
        expenseManager.addExpense("Meal", 20.0, LocalDate.now(), "Food");
        expenseManager.addExpense("Snack", 10.0, LocalDate.now(), "Food");
        expenseManager.addExpense("Taxi", 15.0, LocalDate.now(), "Transport");
        TopCategoryExpenseCommand cmd = new TopCategoryExpenseCommand();
        cmd.execute(expenseManager); // should output "Food"
    }

    @Test
    void testBottomCategoryExpenseCommandExecute() {
        expenseManager.addExpense("Meal", 20.0, LocalDate.now(), "Food");
        expenseManager.addExpense("Snack", 10.0, LocalDate.now(), "Food");
        expenseManager.addExpense("Taxi", 5.0, LocalDate.now(), "Transport");
        BottomCategoryExpenseCommand cmd = new BottomCategoryExpenseCommand();
        cmd.execute(expenseManager); // should output "Transport"
    }

}
