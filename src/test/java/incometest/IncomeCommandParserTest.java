package incometest;

import expenseincome.income.IncomeCommandParser;
import expenseincome.income.commands.AddIncomeCommand;
import expenseincome.income.commands.DeleteIncomeCommand;
import expenseincome.income.commands.EditIncomeCommand;
import expenseincome.income.commands.IncomeCommand;
import expenseincome.income.commands.ListCategoryIncomeCommand;
import expenseincome.income.commands.ListIncomeCommand;
import expenseincome.income.commands.SortIncomeCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IncomeCommandParserTest {

    @Test
    void testParseAddCommandWithDate() {
        IncomeCommand command = IncomeCommandParser.parseCommand(
                "add Salary 5000 Job 2025-03-25");
        assertTrue(command instanceof AddIncomeCommand);
    }

    @Test
    void testParseAddCommandWithoutDate() {
        IncomeCommand command = IncomeCommandParser.parseCommand(
                "add Bonus 1000 Extra");
        assertTrue(command instanceof AddIncomeCommand);
    }

    @Test
    void testParseInvalidAddCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("add Bonus");
        assertNull(command);
    }

    @Test
    void testParseListCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("list");
        assertTrue(command instanceof ListIncomeCommand);
    }

    @Test
    void testParseListCategoryCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("list category Job");
        assertTrue(command instanceof ListCategoryIncomeCommand);
    }

    @Test
    void testParseInvalidListCategoryCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("list category");
        assertNull(command);
    }

    @Test
    void testParseDeleteCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("delete 1");
        assertTrue(command instanceof DeleteIncomeCommand);
    }

    @Test
    void testParseInvalidDeleteCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("delete abc");
        assertNull(command);
    }

    @Test
    void testParseEditCommandWithDate() {
        IncomeCommand command = IncomeCommandParser.parseCommand(
                "edit 1 Bonus 750 Extra 2025-03-30");
        assertTrue(command instanceof EditIncomeCommand);
    }

    @Test
    void testParseEditCommandWithoutDate() {
        IncomeCommand command = IncomeCommandParser.parseCommand(
                "edit 1 Bonus 750 Extra");
        assertTrue(command instanceof EditIncomeCommand);
    }

    @Test
    void testParseInvalidEditCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("edit 1 Bonus");
        assertNull(command);
    }

    @Test
    void testParseSortRecentCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("sort recent");
        assertTrue(command instanceof SortIncomeCommand);
    }

    @Test
    void testParseSortOldestCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("sort oldest");
        assertTrue(command instanceof SortIncomeCommand);
    }

    @Test
    void testParseSortInvalidType() {
        IncomeCommand command = IncomeCommandParser.parseCommand("sort random");
        assertNull(command);
    }

    @Test
    void testParseUnknownCommand() {
        IncomeCommand command = IncomeCommandParser.parseCommand("dance now");
        assertNull(command);
    }
}
