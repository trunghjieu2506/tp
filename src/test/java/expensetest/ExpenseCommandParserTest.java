package expensetest;

import expenseincome.expense.ExpenseCommandParser;
import expenseincome.expense.ExpenseParserResult;
import expenseincome.expense.commands.BottomCategoryExpenseCommand;
import expenseincome.expense.commands.TopCategoryExpenseCommand;
import expenseincome.expense.commands.AddExpenseCommand;
import expenseincome.expense.commands.DeleteExpenseCommand;
import expenseincome.expense.commands.EditExpenseCommand;
import expenseincome.expense.commands.ListExpenseCommand;
import expenseincome.expense.commands.ListCategoryExpenseCommand;
import expenseincome.expense.commands.SortExpenseCommand;
import expenseincome.expense.commands.HelpExpenseCommand;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ExpenseCommandParserTest {

    @Test
    void testValidAddCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("add Lunch 10.5 Food 2025-04-06");
        assertTrue(result.hasCommand());
        assertFalse(result.hasFeedback());
        assertInstanceOf(AddExpenseCommand.class, result.getCommand());
    }

    @Test
    void testAddCommandNegativeAmount() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("add Lunch -5 Food");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
        assertEquals("Amount must be greater than zero.", result.getFeedback());
    }

    @Test
    void testInvalidAddMissingArgs() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("add Lunch");
        assertFalse(result.hasCommand());
        assertTrue(result.hasFeedback());
    }

    @Test
    void testValidEditCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("edit 1 Dinner 20.5 Food 2025-04-07");
        assertTrue(result.hasCommand());
        assertInstanceOf(EditExpenseCommand.class, result.getCommand());
    }

    @Test
    void testEditCommandInvalidIndexFormat() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("edit one Dinner 20.5 Food");
        assertFalse(result.hasCommand());
        assertEquals("Invalid number format in edit command.", result.getFeedback());
    }

    @Test
    void testEditCommandMissingFields() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("edit 1 Dinner");
        assertFalse(result.hasCommand());
        assertEquals("Please provide all required fields for editing.", result.getFeedback());
    }

    @Test
    void testDeleteCommandValid() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("delete 2");
        assertTrue(result.hasCommand());
        assertInstanceOf(DeleteExpenseCommand.class, result.getCommand());
    }

    @Test
    void testDeleteCommandInvalidNumber() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("delete one");
        assertFalse(result.hasCommand());
        assertEquals("Invalid index. Please enter a number.", result.getFeedback());
    }

    @Test
    void testDeleteCommandNegativeIndex() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("delete 0");
        assertFalse(result.hasCommand());
        assertEquals("Index must be a positive integer.", result.getFeedback());
    }

    @Test
    void testSortRecentCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("sort recent");
        assertTrue(result.hasCommand());
        assertInstanceOf(SortExpenseCommand.class, result.getCommand());
    }

    @Test
    void testSortOldestCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("sort oldest");
        assertTrue(result.hasCommand());
        assertInstanceOf(SortExpenseCommand.class, result.getCommand());
    }

    @Test
    void testSortInvalidType() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("sort fast");
        assertFalse(result.hasCommand());
        assertEquals("Unknown sort type. Use 'recent' or 'oldest'.", result.getFeedback());
    }

    @Test
    void testListCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("list");
        assertTrue(result.hasCommand());
        assertInstanceOf(ListExpenseCommand.class, result.getCommand());
    }

    @Test
    void testListCategoryCommandValid() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("list category Food");
        assertTrue(result.hasCommand());
        assertInstanceOf(ListCategoryExpenseCommand.class, result.getCommand());
    }

    @Test
    void testListCategoryCommandMissingCategory() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("list category");
        assertFalse(result.hasCommand());
        assertEquals("Usage: list category <categoryName>", result.getFeedback());
    }

    @Test
    void testTopCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("top");
        assertTrue(result.hasCommand());
        assertInstanceOf(TopCategoryExpenseCommand.class, result.getCommand());
    }

    @Test
    void testBottomCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("bottom");
        assertTrue(result.hasCommand());
        assertInstanceOf(BottomCategoryExpenseCommand.class, result.getCommand());
    }

    @Test
    void testHelpCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("help");
        assertTrue(result.hasCommand());
        assertInstanceOf(HelpExpenseCommand.class, result.getCommand());
    }

    @Test
    void testUnknownCommand() {
        ExpenseParserResult result = ExpenseCommandParser.parseCommand("fly");
        assertFalse(result.hasCommand());
        assertEquals("Unknown command: fly", result.getFeedback());
    }
}
