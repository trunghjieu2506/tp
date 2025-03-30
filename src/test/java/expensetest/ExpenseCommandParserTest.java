package expensetest;

import expenseincome.expense.ExpenseCommandParser;
import expenseincome.expense.commands.AddExpenseCommand;
import expenseincome.expense.commands.DeleteExpenseCommand;
import expenseincome.expense.commands.EditExpenseCommand;
import expenseincome.expense.commands.ExpenseCommand;
import expenseincome.expense.commands.ListExpenseCommand;
import expenseincome.expense.commands.ListCategoryExpenseCommand;
import expenseincome.expense.commands.SortExpenseCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExpenseCommandParserTest {

    @Test
    void testParseAddCommandValid() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("add Coffee 3.0 Drink 2025-03-26");
        assertTrue(cmd instanceof AddExpenseCommand);
    }

    @Test
    void testParseAddCommandMissingArgs() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("add Coffee");
        assertNull(cmd);
    }

    @Test
    void testParseListCommand() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("list");
        assertTrue(cmd instanceof ListExpenseCommand);
    }

    @Test
    void testParseListCategoryCommand() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("list category Food");
        assertTrue(cmd instanceof ListCategoryExpenseCommand);
    }

    @Test
    void testParseListCategoryCommandMissingCategory() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("list category");
        assertNull(cmd);
    }

    @Test
    void testParseDeleteCommandValid() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("delete 1");
        assertTrue(cmd instanceof DeleteExpenseCommand);
    }

    @Test
    void testParseDeleteCommandInvalid() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("delete one");
        assertNull(cmd);
    }

    @Test
    void testParseEditCommandValid() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand(
                "edit 1 Dinner 20.5 Food 2025-04-01");
        assertTrue(cmd instanceof EditExpenseCommand);
    }

    @Test
    void testParseEditCommandMissingArgs() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("edit 1 Dinner");
        assertNull(cmd);
    }

    @Test
    void testParseSortCommandRecent() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("sort recent");
        assertTrue(cmd instanceof SortExpenseCommand);
    }

    @Test
    void testParseSortCommandOldest() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("sort oldest");
        assertTrue(cmd instanceof SortExpenseCommand);
    }

    @Test
    void testParseSortCommandUnknown() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("sort newestest");
        assertNull(cmd);
    }

    @Test
    void testParseUnknownCommand() {
        ExpenseCommand cmd = ExpenseCommandParser.parseCommand("fly now");
        assertNull(cmd);
    }
}
