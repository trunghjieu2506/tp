package savingtest;

import budgetsaving.saving.SavingList;
import budgetsaving.saving.Saving;
import budgetsaving.saving.exceptions.SavingAttributeException;
import budgetsaving.saving.exceptions.SavingRuntimeException;
import budgetsaving.saving.utils.SavingAttributes;
import budgetsaving.saving.utils.SavingParser;
import budgetsaving.saving.command.SetSavingCommand;
import budgetsaving.saving.command.ContributeGoalCommand;
import budgetsaving.saving.command.ListSavingsCommand;
import budgetsaving.saving.command.SavingGeneralCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.money.Money;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import cashflow.model.storage.Storage;
import cashflow.model.interfaces.Finance;

import static org.junit.jupiter.api.Assertions.*;

public class SavingModuleTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    // Dummy saving storage to ensure SavingList has a non-null savingStorage.
    private Storage dummySavingStorage;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Create a dummy storage instance to be passed to SavingList.
        dummySavingStorage = new Storage("dummySavingPath") {
            // To optionally capture saved data:
            private java.util.ArrayList<Finance> savedFile = new java.util.ArrayList<>();

            @Override
            public java.util.ArrayList<Finance> loadFile() {
                // Always return an empty list.
                return new java.util.ArrayList<>();
            }

            @Override
            public void saveFile(java.util.ArrayList<Finance> financeList) {
                savedFile = new java.util.ArrayList<>(financeList);
                // Optionally print a message, or simply do nothing.
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
        // Use the overloaded constructor supplying dummySavingStorage.
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Money amount = new Money("USD", new BigDecimal("100.0"));
        // Expect no runtime exception when valid data is provided.
        String response = assertDoesNotThrow(() -> savingList.setNewSaving("Goal1", amount, deadline));

        assertTrue(response.contains("Goal1"), "Response should contain the saving goal name.");
        assertTrue(response.contains("100.0"), "Response should contain the saving goal amount.");
        assertTrue(response.contains("2025-12-31"), "Response should contain the deadline date.");

        // Verify that listing savings returns the new goal.
        String listResponse = savingList.listSavings();
        assertTrue(listResponse.contains("Goal1"), "List of savings should include the new goal.");
    }

    @Test
    public void testSetNewSavingThrowsSavingRuntimeException() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        // Using a negative amount should trigger a SavingRuntimeException.
        Money negativeAmount = new Money("USD", new BigDecimal("-100.0"));
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Exception exception = assertThrows(SavingRuntimeException.class, () ->
                savingList.setNewSaving("GoalInvalid", negativeAmount, deadline)
        );
        // Optionally verify that the exception message contains expected text.
        String expectedMessage = "Invalid saving amount";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testContributeToSaving() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Money goalAmount = new Money("USD", new BigDecimal("100.0"));
        assertDoesNotThrow(() -> savingList.setNewSaving("Goal1", goalAmount, deadline));

        // Contribute an amount less than the goal.
        Money contribution = new Money("USD", new BigDecimal("30.0"));
        String response = savingList.contributeToSaving(0, contribution);
        assertTrue(response.contains("funded"), "Response should indicate that the goal has been funded.");

        // Contribute an amount that exceeds the goal.
        Money contribution2 = new Money("USD", new BigDecimal("80.0"));
        savingList.contributeToSaving(0, contribution2);
        // Check that the output indicates the saving status is updated to completed.
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Saving status updated to: COMPLETED"),
                "Output should indicate that saving status was updated to COMPLETED.");
    }

    @Test
    public void testListGoalsEmpty() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        String response = savingList.listSavings();
        assertEquals("No savings goals set.", response.trim(),
                "Listing savings on an empty list should return the appropriate message.");
    }

    @Test
    public void testModifySavingInvalidIndex() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        // With no savings added, modifying index 0 should trigger an error.
        savingList.modifySaving(0, new Money("USD", new BigDecimal("50.0")), LocalDate.of(2025, 1, 1));
        String errStr = errContent.toString();
        assertTrue(errStr.contains("Index out of bounds."),
                "Error output should mention that the index is out of bounds.");
    }

    // ----- Tests for Saving (the saving goal itself) -----

    @Test
    public void testAddContributionAndCompletion() {
        Money goal = new Money("USD", new BigDecimal("100.0"));
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Saving saving = assertDoesNotThrow(() -> new Saving("Goal1", goal, deadline),
                "Creating a new Saving should not throw an exception.");

        Money contribution = new Money("USD", new BigDecimal("50.0"));
        assertDoesNotThrow(() -> saving.addContribution(contribution),
                "Adding the first contribution should not throw an exception.");
        // Use compareTo for BigDecimal to avoid trailing zero issues.
        assertEquals(0, new BigDecimal("50.0").compareTo(saving.getCurrentAmount().getAmount()),
                "The current amount should be 50.0 after the first contribution.");

        // Add a second contribution that would exceed the goal.
        Money contribution2 = new Money("USD", new BigDecimal("60.0"));
        assertDoesNotThrow(() -> saving.addContribution(contribution2),
                "Adding a contribution that exceeds the goal should not throw an exception.");
        assertEquals(0, new BigDecimal("100.0").compareTo(saving.getCurrentAmount().getAmount()),
                "The current amount should cap at 100.0 when contributions exceed the goal.");

        // Check that the expected completion status message is printed.
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Saving status updated to: COMPLETED"),
                "Output should indicate that saving status was updated to COMPLETED.");
    }

    // ----- Tests for SavingAttributes -----

    @Test
    public void testSavingAttributesValidInput() throws SavingAttributeException {
        String input = "i/1 n/Goal1 a/100.0 b/2025-12-31";
        budgetsaving.saving.utils.SavingAttributes attributes = new budgetsaving.saving.utils.SavingAttributes(input);
        assertEquals(0, attributes.getIndex(), "Index should be converted to 0-index.");
        assertEquals("Goal1", attributes.getName(), "Name attribute mismatch.");
        assertEquals(100.0, attributes.getAmount(), 0.001, "Amount attribute mismatch.");
        assertEquals(LocalDate.of(2025, 12, 31), attributes.getDeadline(), "Deadline attribute mismatch.");
    }

    @Test
    public void testSavingAttributesInvalidAmount() {
        String input = "n/Goal1 a/notanumber b/2025-12-31";
        Exception exception = assertThrows(SavingAttributeException.class, () -> new budgetsaving.saving.utils.SavingAttributes(input));
        String expectedMessage = "Invalid amount value";
        assertTrue(exception.getMessage().contains(expectedMessage), "Exception message should indicate an invalid amount value.");
    }

    @Test
    public void testSavingAttributesInvalidDate() {
        String input = "n/Goal1 a/100.0 b/invalid-date";
        Exception exception = assertThrows(SavingAttributeException.class, () -> new budgetsaving.saving.utils.SavingAttributes(input));
        String expectedMessage = "not a valid format";
        assertTrue(exception.getMessage().contains(expectedMessage), "Exception message should indicate an invalid date format.");
    }

    // ----- Tests for SavingParser -----

    @Test
    public void testParseSetGoalCommand() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        String input = "set n/Goal1 a/100.0 b/2025-12-31";
        try {
            Object command = budgetsaving.saving.utils.SavingParser.parseSetGoalCommand(input, savingList);
            assertTrue(command instanceof SetSavingCommand, "Parsed command should be an instance of SetSavingCommand.");
        } catch (Exception e) {
            fail("Unexpected exception in parseSetGoalCommand: " + e.getMessage());
        }
    }

    @Test
    public void testParseContributeGoalCommand() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        String input = "contribute i/1 a/50.0";
        try {
            Object command = budgetsaving.saving.utils.SavingParser.parseContributeGoalCommand(input, savingList);
            assertTrue(command instanceof ContributeGoalCommand, "Parsed command should be an instance of ContributeGoalCommand.");
        } catch (Exception e) {
            fail("Unexpected exception in parseContributeGoalCommand: " + e.getMessage());
        }
    }

    @Test
    public void testParseCheckGoalCommand() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        try {
            Object command = budgetsaving.saving.utils.SavingParser.parseListGoalCommand(savingList);
            assertTrue(command instanceof ListSavingsCommand, "Parsed command should be an instance of ListSavingsCommand.");
        } catch (Exception e) {
            fail("Unexpected exception in parseCheckGoalCommand: " + e.getMessage());
        }
    }

    // ----- Tests for Command classes -----

    @Test
    public void testContributeGoalCommandExecute() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        assertDoesNotThrow(() -> savingList.setNewSaving("Goal1", new Money("USD", new BigDecimal("100.0")), deadline));
        ContributeGoalCommand command = new ContributeGoalCommand(savingList, 0, new Money("USD", new BigDecimal("50.0")));
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("funded"), "Output should indicate that the saving goal is funded.");
    }

    @Test
    public void testListGoalCommandExecute() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        assertDoesNotThrow(() -> savingList.setNewSaving("Goal1", new Money("USD", new BigDecimal("100.0")), LocalDate.of(2025, 12, 31)));
        ListSavingsCommand command = new ListSavingsCommand(savingList);
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Goal1"), "Output should contain the goal name.");
    }

    @Test
    public void testSetGoalCommandExecute() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        SetSavingCommand command = new SetSavingCommand(savingList, "Goal1", new Money("USD", new BigDecimal("100.0")), LocalDate.of(2025, 12, 31));
        assertDoesNotThrow(() -> command.execute());
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Goal1"), "Output should contain the goal name after setting a new saving.");
    }

    // ----- Tests for SavingGeneralCommand -----

    @Test
    public void testSavingGeneralCommandUnknown() {
        SavingList savingList = new SavingList("USD", dummySavingStorage);
        String input = "unknown command";
        try {
            SavingGeneralCommand command = new SavingGeneralCommand(input, savingList);
            command.execute();
            String outStr = outContent.toString();
            assertTrue(outStr.contains("Unknown saving command."),
                    "Output should indicate an unknown saving command.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unknown saving command."),
                    "Exception message should contain 'Unknown saving command.'");
        }
    }

    @Test
    public void testSavingGeneralCommandPrompt() {
        // Simulate user input when the initial command is "saving"
        String simulatedInput = "set n/Goal1 a/100.0 b/2025-12-31\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);
        SavingList savingList = new SavingList("USD", dummySavingStorage);

        SavingGeneralCommand command = new SavingGeneralCommand("saving", savingList);
        command.execute();

        String outStr = outContent.toString();
        assertTrue(outStr.contains("Enter saving command:"), "Output should prompt for saving command.");
        String list = savingList.listSavings();
        assertTrue(list.contains("Goal1"), "Savings list should contain the new goal.");

        // Reset System.in to the original stream.
        System.setIn(System.in);
    }
}
