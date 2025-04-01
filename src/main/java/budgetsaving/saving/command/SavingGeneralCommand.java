package budgetsaving.saving.command;

import budgetsaving.saving.utils.SavingParser;
import cashflow.command.Command;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

import cashflow.model.interfaces.SavingManager;

public class SavingGeneralCommand implements Command {
    private static final String SET_GOAL_COMMAND = "set";
    private static final String CONTRIBUTE_COMMAND = "contribute";
    private static final String LIST_GOAL_COMMAND = "list";
    //just reuse it because it might be more confusing to read to import from budget side
    public static final String DASH = "- ";

    private static final String SAVING_COMMANDS =
                      DASH + SET_GOAL_COMMAND + " n/GOAL_NAME a/AMOUNT b/YYYY-MM-DD\n"
                    + DASH + CONTRIBUTE_COMMAND + " i/INDEX a/AMOUNT\n"
                    + DASH + LIST_GOAL_COMMAND + " \n";

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
            System.out.print(SAVING_COMMANDS + "Enter saving command: ");
            input = scanner.nextLine().trim();
        }
        input = input.trim();
        String lowerInput = input.toLowerCase();
        try{
            if (lowerInput.startsWith(SET_GOAL_COMMAND)) {
                command = SavingParser.parseSetGoalCommand(input, savingList);
            } else if (lowerInput.startsWith(CONTRIBUTE_COMMAND)) {
                command = SavingParser.parseContributeGoalCommand(input, savingList);
            } else if (lowerInput.startsWith(LIST_GOAL_COMMAND)) {
                command = SavingParser.parseCheckGoalCommand(savingList);
            } else {
                System.out.println("Unknown saving command.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid currency/amount entered.");
        }
    }

    @Override
    public void execute() {
        try {
            command.execute();
        } catch (DateTimeParseException e) {
            System.err.println(SetGoalCommand.DATE_FORMAT_ERROR);
        } catch (Exception e) {
            System.err.println("An error has occurred when executing the command.");
        }
    }

    //public Result excute()

    public static void handleSavingCommand(Scanner scanner, SavingManager savingManager) {
        while (true){
            System.out.print("Here's a list of saving commands: \n" + SAVING_COMMANDS + "Enter saving command: ");
            String input = scanner.nextLine().trim();
            if (input.startsWith("exit")) {
                break;
            }
            SavingGeneralCommand command = new SavingGeneralCommand(input, savingManager);
            command.execute();
        }
    }
}
