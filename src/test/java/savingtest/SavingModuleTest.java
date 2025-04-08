package savingtest;

import budgetsaving.saving.SavingList;
import budgetsaving.saving.exceptions.SavingAttributeException;
import budgetsaving.saving.command.SetSavingCommand;
import budgetsaving.saving.command.ContributeGoalCommand;
import budgetsaving.saving.command.ListSavingsCommand;
import budgetsaving.saving.command.SavingGeneralCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.money.Money;
import cashflow.model.storage.Storage;
import cashflow.model.interfaces.Finance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;




public class SavingModuleTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private Storage dummySavingStorage;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Create a dummy storage instance so that the savingStorage inside SavingList is not null.
        dummySavingStorage = new Storage("dummySavingPath") {
            private java.util.ArrayList<Finance> savedFile = new java.util.ArrayList<>();

            @Override
            public java.util.ArrayList<Finance> loadFile() {
                // Always return an empty list.
                return new java.util.ArrayList<>();
            }

            @Override
            public void saveFile(java.util.ArrayList<Finance> financeList) {
                savedFile = new java.util.ArrayList<>(financeList);
                System.out.println("Dummy saveFile called with " + financeList.size() + " item(s).");
            }

            public java.util.ArrayList<Finance> getSavedFile() {
                return savedFile;
            }
        };
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // ----- Tests for SavingList and Saving commands -----

    @Test
    public void testSetNewSaving() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage),
                "SavingList constructor should not throw an exception");
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Money amount = new Money("USD", new BigDecimal("100.0"));
        String response = assertDoesNotThrow(() -> savingList.setNewSaving("Goal1", amount, deadline),
                "setNewSaving should not throw an exception with valid input");

        assertTrue(response.contains("Goal1"), "Response should contain the saving goal name.");
        assertTrue(response.contains("100.0"), "Response should contain the saving goal amount.");
        assertTrue(response.contains("2025-12-31"), "Response should contain the deadline date.");

        String listResponse = savingList.listSavings();
        assertTrue(listResponse.contains("Goal1"), "List of savings should include the new goal.");
    }


    @Test
    public void testListGoalsEmpty() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage),
                "SavingList constructor should not throw an exception");
        String response = savingList.listSavings();
        assertEquals("No savings goals set.", response.trim());
    }

    @Test
    public void testSavingAttributesValidInput() throws SavingAttributeException {
        String input = "i/1 n/Goal1 a/100.0 b/2025-12-31";
        budgetsaving.saving.utils.SavingAttributes attributes = new budgetsaving.saving.utils.SavingAttributes(input);
        assertEquals(0, attributes.getIndex());
        assertEquals("Goal1", attributes.getName());
        assertEquals(100.0, attributes.getAmount(), 0.001);
        assertEquals(LocalDate.of(2025, 12, 31), attributes.getDeadline());
    }

    @Test
    public void testSavingAttributesInvalidAmount() {
        String input = "n/Goal1 a/notanumber b/2025-12-31";
        Exception exception = assertThrows(SavingAttributeException.class, () ->
                new budgetsaving.saving.utils.SavingAttributes(input)
        );
        String expectedMessage = "Invalid amount value";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testSavingAttributesInvalidDate() {
        String input = "n/Goal1 a/100.0 b/invalid-date";
        Exception exception = assertThrows(SavingAttributeException.class, () ->
                new budgetsaving.saving.utils.SavingAttributes(input)
        );
        String expectedMessage = "not a valid format";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    // ----- Tests for SavingParser -----

    @Test
    public void testParseSetGoalCommand() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));
        String input = "set n/Goal1 a/100.0 b/2025-12-31";
        try {
            Object command = budgetsaving.saving.utils.SavingParser.parseSetGoalCommand(input, savingList);
            assertTrue(command instanceof SetSavingCommand);
        } catch (Exception e) {
            fail("Unexpected exception in parseSetGoalCommand: " + e.getMessage());
        }
    }

    @Test
    public void testParseContributeGoalCommand() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));
        String input = "contribute i/1 a/50.0";
        try {
            Object command = budgetsaving.saving.utils.SavingParser.parseContributeGoalCommand(input, savingList);
            assertTrue(command instanceof ContributeGoalCommand);
        } catch (Exception e) {
            fail("Unexpected exception in parseContributeGoalCommand: " + e.getMessage());
        }
    }

    @Test
    public void testParseCheckGoalCommand() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));
        try {
            Object command = budgetsaving.saving.utils.SavingParser.parseListGoalCommand(savingList);
            assertTrue(command instanceof ListSavingsCommand);
        } catch (Exception e) {
            fail("Unexpected exception in parseCheckGoalCommand: " + e.getMessage());
        }
    }

    // ----- Tests for Command classes -----

    @Test
    public void testContributeGoalCommandExecute() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        assertDoesNotThrow(() -> savingList.setNewSaving("Goal1",
                new Money("USD", new BigDecimal("100.0")), deadline));
        ContributeGoalCommand command = new ContributeGoalCommand(savingList,
                0, new Money("USD", new BigDecimal("50.0")));
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("funded"));
    }

    @Test
    public void testListGoalCommandExecute() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));
        assertDoesNotThrow(() -> savingList.setNewSaving("Goal1",
                new Money("USD", new BigDecimal("100.0")),
                LocalDate.of(2025, 12, 31)));
        ListSavingsCommand command = new ListSavingsCommand(savingList);
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Goal1"));
    }

    @Test
    public void testSetGoalCommandExecute() {
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));
        SetSavingCommand command = new SetSavingCommand(savingList, "Goal1",
                new Money("USD", new BigDecimal("100.0")),
                LocalDate.of(2025, 12, 31));
        assertDoesNotThrow(() -> command.execute());
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Goal1"));
    }


    @Test
    public void testSavingGeneralCommandPrompt() {
        String simulatedInput = "set n/Goal1 a/100.0 b/2025-12-31\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);
        SavingList savingList = assertDoesNotThrow(() -> new SavingList("USD", dummySavingStorage));

        SavingGeneralCommand command = new SavingGeneralCommand("saving", savingList);
        command.execute();

        String outStr = outContent.toString();
        assertTrue(outStr.contains("Enter saving command:"), "Output should prompt for saving command.");
        String list = savingList.listSavings();
        assertTrue(list.contains("Goal1"), "Savings list should contain the new goal.");

        System.setIn(System.in);
    }
}
