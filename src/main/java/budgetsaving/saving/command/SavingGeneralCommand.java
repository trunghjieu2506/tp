package budgetsaving.saving.command;

import budgetsaving.saving.exceptions.SavingException;
import budgetsaving.saving.utils.SavingParser;
import cashflow.ui.command.Command;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

import cashflow.model.interfaces.SavingManager;
import utils.io.IOHandler;
import utils.textcolour.TextColour;

import static budgetsaving.budget.command.BudgetGeneralCommand.EXIT_COMMAND;
import static budgetsaving.budget.command.BudgetGeneralCommand.HELP_COMMAND;

public class SavingGeneralCommand implements Command {
    private static final String SET_SAVING_COMMAND = "set";
    private static final String CONTRIBUTE_COMMAND = "contribute";
    private static final String LIST_SAVING_COMMAND = "list";
    private static final String DELETE_SAVING_COMMAND = "delete-s";
    private static final String DELETE_CONTRIBUTION_COMMAND = "delete-c";


    //just reuse it because it might be more confusing to read to import from budget side
    public static final String DASH = "- ";

    private static final String LINE_SEPARATOR = "-".repeat(70);
    static final String SAVING_COMMANDS =
                    LINE_SEPARATOR + '\n'
            + TextColour.GREEN  + DASH + SET_SAVING_COMMAND + " n/GOAL_NAME a/AMOUNT b/YYYY-MM-DD\n"
            + TextColour.YELLOW + DASH + CONTRIBUTE_COMMAND + " i/INDEX a/AMOUNT\n"
            + TextColour.BLUE   + DASH + LIST_SAVING_COMMAND + " \n"
            + TextColour.CYAN   + DASH + DELETE_SAVING_COMMAND + " i/INDEX\n"
            //+ TextColour.PURPLE   + DASH + DELETE_CONTRIBUTION_COMMAND + " i/INDEX_S c/INDEX_C\n"
            + TextColour.RESET
            + DASH + HELP_COMMAND + " to check all the saving commands\n"
            + DASH + EXIT_COMMAND + " to exit from saving mode\n"
            + LINE_SEPARATOR + '\n';

    private Command command;

    /**
     * Constructs a SavingGeneralCommand by parsing the user input and initializing the corresponding command.
     * If the input is just "saving", the user is prompted for further details.
     *
     * Expected saving subcommand formats:
     * - set-goal n/GOAL_NAME a/AMOUNT b/BY
     * - contribute-goal n/GOAL_NAME a/AMOUNT
     * - check-goal
     *
     * @param input the full user input command string.
     * @param savingList the saving list to operate on.
     */
    public SavingGeneralCommand(String input, SavingManager savingList){
        // If the command is exactly "saving", prompt the user for the specific saving subcommand.
        if (input.trim().equalsIgnoreCase("saving")) {
            Scanner scanner = new Scanner(System.in);
            IOHandler.writeOutput(SAVING_COMMANDS + "Enter saving command: ");
            input = scanner.nextLine().trim();
        }
        input = input.trim();
        String lowerInput = input.toLowerCase();
        try{
            if (lowerInput.startsWith(SET_SAVING_COMMAND)) {
                command = SavingParser.parseSetGoalCommand(input, savingList);
            } else if (lowerInput.startsWith(CONTRIBUTE_COMMAND)) {
                command = SavingParser.parseContributeGoalCommand(input, savingList);
            } else if (lowerInput.startsWith(LIST_SAVING_COMMAND)) {
                command = SavingParser.parseCheckGoalCommand(savingList);
            } else if (lowerInput.startsWith(HELP_COMMAND)){
                command = new SavingHelpCommand();
            } else if (lowerInput.startsWith(DELETE_SAVING_COMMAND)) {
                command = SavingParser.parseDeleteSavingCommand(input, savingList);
            }
            else {
                IOHandler.writeError("Unknown saving command.");
            }
        } catch (SavingException e) {
            IOHandler.writeError(e.getMessage());
            IOHandler.writeError("Type 'help' for help");
        }
    }

    @Override
    public void execute() {
        try {
            command.execute();
        } catch (DateTimeParseException e) {
            IOHandler.writeError(SetSavingCommand.DATE_FORMAT_ERROR);
        } catch (Exception e) {
            IOHandler.writeError("An error has occurred when executing the command.");
        }
    }

    //public Result excute()

    public static void handleSavingCommand(Scanner scanner, SavingManager savingManager) {
        IOHandler.writeOutputNoLn("Here's a list of saving commands: \n" + SAVING_COMMANDS);
        while (true){
            IOHandler.writeOutputNoLn("> ");
            String input = scanner.nextLine().trim();
            if (input.startsWith("exit")) {
                break;
            }
            SavingGeneralCommand command = new SavingGeneralCommand(input, savingManager);
            command.execute();
        }
    }
}
