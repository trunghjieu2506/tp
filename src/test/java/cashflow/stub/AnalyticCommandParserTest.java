package cashflow.stub;

import cashflow.analytics.command.*;
import cashflow.analytics.parser.AnalyticCommandParser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticCommandParserTest {

    @Test
    @DisplayName("parseCommand: help -> returns HelpCommand")
    void testHelpCommand() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("help");
        assertNotNull(cmd);
        assertTrue(cmd instanceof HelpCommand);
    }

    @Test
    @DisplayName("parseCommand: overview with no args -> returns OverviewCommand with current date")
    void testOverviewNoArgs() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("overview");
        assertNotNull(cmd);
        assertTrue(cmd instanceof OverviewCommand);

        // We can't easily check the internal year/month without reflection,
        // but at least we know it didn't throw an exception.
    }

    @Test
    @DisplayName("parseCommand: overview with yyyy-MM -> returns OverviewCommand for correct month/year")
    void testOverviewWithArgs() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("overview 2025-04");
        assertNotNull(cmd);
        assertTrue(cmd instanceof OverviewCommand);
        // Additional checks might require reflection or getters in OverviewCommand
    }

    @Test
    @DisplayName("parseCommand: overview with invalid date -> throws DateTimeParseException")
    void testOverviewInvalidDate() {
        // "overview 2025/04" is invalid format
        Exception ex = assertThrows(DateTimeParseException.class, () ->
                AnalyticCommandParser.parseCommand("overview 2025/04")
        );
        assertTrue(ex.getMessage().contains("Incorrect DateTime Format"));
    }

    @Test
    @DisplayName("parseCommand: unknown command -> throws IllegalArgumentException")
    void testUnknownCommand() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                AnalyticCommandParser.parseCommand("somethingThatDoesNotExist 2025-01")
        );
        assertTrue(ex.getMessage().contains("Unknown command"));
    }

    @Test
    @DisplayName("parseCommand: trend missing args -> throws Exception")
    void testTrendMissingArgs() {
        // "trend expense 2025-01" is incomplete, should have 5 tokens
        Exception ex = assertThrows(Exception.class, () ->
                AnalyticCommandParser.parseCommand("trend expense 2025-01")
        );
        assertTrue(ex.getMessage().contains("Incorrect syntax. Use trend <data-type> <start-date> <end-date> <interval>"));
    }

    @Test
    @DisplayName("parseCommand: trend invalid data-type -> throws Exception")
    void testTrendInvalidDataType() {
        // data-type must be "expense" or "income"
        Exception ex = assertThrows(Exception.class, () ->
                AnalyticCommandParser.parseCommand("trend random 2025-01-01 2025-02-01 weekly")
        );
        assertTrue(ex.getMessage().contains("Incorrect data-type. Use trend expense or trend income"));
    }

    @Test
    @DisplayName("parseCommand: trend invalid date range -> throws Exception")
    void testTrendInvalidDateRange() {
        // end must be after start
        Exception ex = assertThrows(Exception.class, () ->
                AnalyticCommandParser.parseCommand("trend expense 2025-02-01 2025-01-01 weekly")
        );
        assertTrue(ex.getMessage().contains("must be after start date"));
    }

    @Test
    @DisplayName("parseCommand: trend invalid interval -> throws Exception")
    void testTrendInvalidInterval() {
        Exception ex = assertThrows(Exception.class, () ->
                AnalyticCommandParser.parseCommand("trend expense 2025-01-01 2025-02-01 daily")
        );
        assertTrue(ex.getMessage().contains("Invalid interval"));
    }

    @Test
    @DisplayName("parseCommand: trend valid command -> returns TrendCommand")
    void testTrendValidCommand() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("trend expense 2025-01-01 2025-02-01 monthly");
        assertNotNull(cmd);
        assertTrue(cmd instanceof TrendCommand);
    }

    @Test
    @DisplayName("parseCommand: insight no args -> returns SpendingInsightCommand with today's date")
    void testInsightNoArgs() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("insight");
        assertNotNull(cmd);
        assertTrue(cmd instanceof SpendingInsightCommand);
    }

    @Test
    @DisplayName("parseCommand: insight with valid yyyy-MM -> returns SpendingInsightCommand")
    void testInsightWithArgs() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("insight 2025-03");
        assertNotNull(cmd);
        assertTrue(cmd instanceof SpendingInsightCommand);
    }

    @Test
    @DisplayName("parseCommand: spending-breakdown -> returns SpendingBreakDownCommand with today's date")
    void testSpendingBreakdownNoArgs() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("spending-breakdown");
        assertNotNull(cmd);
        assertTrue(cmd instanceof SpendingBreakDownCommand);
    }

    @Test
    @DisplayName("parseCommand: spending-breakdown with valid yyyy-MM -> returns SpendingBreakDownCommand")
    void testSpendingBreakdownWithArgs() throws Exception {
        AnalyticGeneralCommand cmd = AnalyticCommandParser.parseCommand("spending-breakdown 2025-07");
        assertNotNull(cmd);
        assertTrue(cmd instanceof SpendingBreakDownCommand);
    }
}
