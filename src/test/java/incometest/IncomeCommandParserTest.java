package incometest;

import expenseincome.income.IncomeCommandParser;
import expenseincome.income.IncomeParserResult;
import expenseincome.income.commands.AddIncomeCommand;
import expenseincome.income.commands.DeleteIncomeCommand;
import expenseincome.income.commands.EditIncomeCommand;
import expenseincome.income.commands.ListCategoryIncomeCommand;
import expenseincome.income.commands.ListIncomeCommand;
import expenseincome.income.commands.SortIncomeCommand;
import expenseincome.income.commands.HelpIncomeCommand;
import expenseincome.income.commands.TopCategoryIncomeCommand;
import expenseincome.income.commands.BottomCategoryIncomeCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class IncomeCommandParserTest {

    @Test
    void testParseAddCommandWithDate() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("add Salary 5000 Job 2025-03-25");
        assertTrue(result.hasCommand());
        assertInstanceOf(AddIncomeCommand.class, result.getCommand());
        assertFalse(result.hasFeedback());
    }

    @Test
    void testParseAddCommandWithoutDate() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("add Bonus 1000 Extra");
        assertTrue(result.hasCommand());
        assertInstanceOf(AddIncomeCommand.class, result.getCommand());
        assertFalse(result.hasFeedback());
    }

    @Test
    void testParseInvalidAddCommandMissingAmount() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("add Bonus");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testParseAddCommandNegativeAmount() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("add Bonus -1000 Extra");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testParseListCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("list");
        assertTrue(result.hasCommand());
        assertInstanceOf(ListIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseListCategoryCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("list category Job");
        assertTrue(result.hasCommand());
        assertInstanceOf(ListCategoryIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseListCategoryMissingName() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("list category");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testParseDeleteCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("delete 1");
        assertTrue(result.hasCommand());
        assertInstanceOf(DeleteIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseDeleteInvalidIndex() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("delete abc");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testParseEditCommandWithDate() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("edit 1 Bonus 750 Extra 2025-03-30");
        assertTrue(result.hasCommand());
        assertInstanceOf(EditIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseEditCommandWithoutDate() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("edit 1 Bonus 750 Extra");
        assertTrue(result.hasCommand());
        assertInstanceOf(EditIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseEditMissingFields() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("edit 1 Bonus");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testParseSortRecentCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("sort recent");
        assertTrue(result.hasCommand());
        assertInstanceOf(SortIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseSortOldestCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("sort oldest");
        assertTrue(result.hasCommand());
        assertInstanceOf(SortIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseSortInvalidType() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("sort random");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testParseTopAndBottomCommand() {
        IncomeParserResult top = IncomeCommandParser.parseCommand("top");
        IncomeParserResult bottom = IncomeCommandParser.parseCommand("bottom");
        assertInstanceOf(TopCategoryIncomeCommand.class, top.getCommand());
        assertInstanceOf(BottomCategoryIncomeCommand.class, bottom.getCommand());
    }

    @Test
    void testParseHelpCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("help");
        assertTrue(result.hasCommand());
        assertInstanceOf(HelpIncomeCommand.class, result.getCommand());
    }

    @Test
    void testParseUnknownCommand() {
        IncomeParserResult result = IncomeCommandParser.parseCommand("fly away");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }
}
