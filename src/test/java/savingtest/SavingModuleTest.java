package savingtest;

import static org.junit.jupiter.api.Assertions.*;

import budgetsaving.saving.exceptions.SavingAttributeException;
import org.junit.jupiter.api.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import budgetsaving.saving.*;
import budgetsaving.saving.utils.*;
import budgetsaving.saving.command.*;
import utils.money.Money;

public class SavingModuleTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // ----- Tests for SavingList -----

    @Test
    public void testSetNewSaving() {
        SavingList savingList = new SavingList("USD");
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Money amount = new Money("USD", new BigDecimal("100.0"));
        String response = savingList.setNewSaving("Goal1", amount, deadline);

        assertTrue(response.contains("Goal1"));
        assertTrue(response.contains("100.0"));
        assertTrue(response.contains("2025-12-31"));

        // Verify that listGoals returns the new goal
        String listResponse = savingList.listSavings();
        assertTrue(listResponse.contains("Goal1"));
    }

    @Test
    public void testContributeToSaving() {
        SavingList savingList = new SavingList("USD");
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Money goalAmount = new Money("USD", new BigDecimal("100.0"));
        savingList.setNewSaving("Goal1", goalAmount, deadline);

        // Contribute less than the goal amount.
        Money contribution = new Money("USD", new BigDecimal("30.0"));
        String response = savingList.contributeToSaving(0, contribution);
        assertTrue(response.contains("funded"));

        // Contribute an amount that exceeds the goal.
        Money contribution2 = new Money("USD", new BigDecimal("80.0"));
        savingList.contributeToSaving(0, contribution2);
        // The Saving class should cap the contribution at the goal and update the status.
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Saving status updated to: COMPLETED"));
    }

    @Test
    public void testListGoalsEmpty() {
        SavingList savingList = new SavingList("USD");
        String response = savingList.listSavings();
        assertEquals("No savings goals set.", response);
    }

    @Test
    public void testModifySavingInvalidIndex() {
        SavingList savingList = new SavingList("USD");
        // With no savings, modifying index 0 should trigger an error.
        savingList.modifySaving(0, new Money("USD", new BigDecimal("50.0")), LocalDate.of(2025, 1, 1));
        String errStr = errContent.toString();
        assertTrue(errStr.contains("Index out of bounds."));
    }

    // ----- Tests for Saving -----

    @Test
    public void testAddContributionAndCompletion() {
        Money goal = new Money("USD", new BigDecimal("100.0"));
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Saving saving = new Saving("Goal1", goal, deadline);

        Money contribution = new Money("USD", new BigDecimal("50.0"));
        saving.addContribution(contribution);
        assertEquals(new BigDecimal("50.0"), saving.getCurrentAmount().getAmount());

        // Now add a contribution that exceeds the goal amount.
        Money contribution2 = new Money("USD", new BigDecimal("60.0"));
        saving.addContribution(contribution2);
        assertEquals(new BigDecimal("100.0"), saving.getCurrentAmount().getAmount());
        // Check that the saving status update message is printed.
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Saving status updated to: COMPLETED"));
    }

    @Test
    public void testToStringWithContributions() {
        Money goal = new Money("USD", new BigDecimal("100.0"));
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Saving saving = new Saving("Goal1", goal, deadline);
        saving.addContribution(new Money("USD", new BigDecimal("30.0")));

        String detailed = saving.toStringWithContributions();
        assertTrue(detailed.contains("Contribution 0"));
    }

    // ----- Tests for SavingAttributes -----

    @Test
    public void testSavingAttributesValidInput() throws SavingAttributeException {
        String input = "i/1 n/Goal1 a/100.0 b/2025-12-31";
        SavingAttributes attributes = new SavingAttributes(input);
        assertEquals(0, attributes.getIndex()); // Converted to 0-index
        assertEquals("Goal1", attributes.getName());
        assertEquals(100.0, attributes.getAmount(), 0.001);
        assertEquals(LocalDate.of(2025, 12, 31), attributes.getDeadline());
    }

    @Test
    public void testSavingAttributesInvalidAmount() {
        String input = "n/Goal1 a/notanumber b/2025-12-31";
        Exception exception = assertThrows(SavingAttributeException.class, () -> {
            new SavingAttributes(input);
        });
        String expectedMessage = "Invalid amount value";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    // (Optional) Additional test for invalid date format in SavingAttributes.
    @Test
    public void testSavingAttributesInvalidDate() {
        String input = "n/Goal1 a/100.0 b/invalid-date";
        Exception exception = assertThrows(SavingAttributeException.class, () -> {
            new SavingAttributes(input);
        });
        String expectedMessage = "not a valid format";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    // ----- Tests for SavingParser -----

    @Test
    public void testParseSetGoalCommand() {
        SavingList savingList = new SavingList("USD");
        String input = "set n/Goal1 a/100.0 b/2025-12-31";
        try {
            Object command = SavingParser.parseSetGoalCommand(input, savingList);
            assertTrue(command instanceof SetSavingCommand);
        } catch (Exception e) { // Replace Exception with your specific parser exception if needed
            fail("Unexpected exception thrown in parseSetGoalCommand: " + e.getMessage());
        }
    }

    @Test
    public void testParseContributeGoalCommand() {
        SavingList savingList = new SavingList("USD");
        String input = "contribute i/1 a/50.0";
        try {
            Object command = SavingParser.parseContributeGoalCommand(input, savingList);
            assertTrue(command instanceof ContributeGoalCommand);
        } catch (Exception e) {
            fail("Unexpected exception thrown in parseContributeGoalCommand: " + e.getMessage());
        }
    }

    @Test
    public void testParseCheckGoalCommand() {
        SavingList savingList = new SavingList("USD");
        try {
            Object command = SavingParser.parseCheckGoalCommand(savingList);
            assertTrue(command instanceof ListSavingsCommand);
        } catch (Exception e) {
            fail("Unexpected exception thrown in parseCheckGoalCommand: " + e.getMessage());
        }
    }

    // ----- Tests for Command classes -----

    @Test
    public void testContributeGoalCommandExecute() {
        SavingList savingList = new SavingList("USD");
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        savingList.setNewSaving("Goal1", new Money("USD", new BigDecimal("100.0")), deadline);
        ContributeGoalCommand command = new ContributeGoalCommand(savingList, 0, new Money("USD", new BigDecimal("50.0")));
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("funded"));
    }

    @Test
    public void testListGoalCommandExecute() {
        SavingList savingList = new SavingList("USD");
        savingList.setNewSaving("Goal1", new Money("USD", new BigDecimal("100.0")), LocalDate.of(2025, 12, 31));
        ListSavingsCommand command = new ListSavingsCommand(savingList);
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Goal1"));
    }

    @Test
    public void testSetGoalCommandExecute() {
        SavingList savingList = new SavingList("USD");
        SetSavingCommand command = new SetSavingCommand(savingList, "Goal1",
                new Money("USD", new BigDecimal("100.0")), LocalDate.of(2025, 12, 31));
        command.execute();
        String outStr = outContent.toString();
        assertTrue(outStr.contains("Goal1"));
    }

    // ----- Tests for SavingGeneralCommand -----

    @Test
    public void testSavingGeneralCommandUnknown() {
        SavingList savingList = new SavingList("USD");
        String input = "unknown command";
        try {
            SavingGeneralCommand command = new SavingGeneralCommand(input, savingList);
            command.execute();
            String outStr = outContent.toString();
            assertTrue(outStr.contains("Unknown saving command."));
        } catch (Exception e) {
            // If an exception is thrown, check that it relates to the unknown command.
            assertTrue(e.getMessage().contains("Unknown saving command."));
        }
    }

    @Test
    public void testSavingGeneralCommandPrompt() {
        // Simulate user input when the initial command is "saving"
        String simulatedInput = "set n/Goal1 a/100.0 b/2025-12-31\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);
        SavingList savingList = new SavingList("USD");

        // The input "saving" should trigger the prompt and read the next line.
        SavingGeneralCommand command = new SavingGeneralCommand("saving", savingList);
        command.execute();

        String outStr = outContent.toString();
        // Check that the command prompt text is printed.
        assertTrue(outStr.contains("Enter saving command:"));

        // Verify that the saving goal "Goal1" was set.
        String list = savingList.listSavings();
        assertTrue(list.contains("Goal1"));

        // Reset System.in if necessary.
        System.setIn(System.in);
    }
}
