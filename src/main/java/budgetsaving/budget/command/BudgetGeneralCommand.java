package budgetsaving.budget.command;

import budgetsaving.budget.exceptions.BudgetException;
import budgetsaving.budget.utils.BudgetParser;
import utils.textcolour.TextColour;
import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;
import utils.io.IOHandler;

import java.util.Scanner;


//this class contains all the budget commands
//ALL EXCEPTIONS should be handled inside their respective commands
//so any exception thrown out should not be coming from lower hierarchy
public class BudgetGeneralCommand implements Command {

    public static final String LIST_BUDGET = "list";
    public static final String SET_BUDGET = "set";
    public static final String CHECK_BUDGET = "check";
    public static final String DEDUCT_BUDGET = "deduct";
    public static final String MODIFY_BUDGET = "modify";
    public static final String HELP_COMMAND = "help";
    public static final String EXIT_COMMAND = "exit";

    public static final String LINE_SEPARATOR = "-".repeat(70);
    public static final String DASH = "- ";

    static final String BUDGET_COMMANDS =
            LINE_SEPARATOR + '\n'
        + TextColour.PURPLE    + DASH + SET_BUDGET
                  + " n/BUDGET_NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY\n"  + TextColour.RESET
        + TextColour.GREEN  + DASH + CHECK_BUDGET  + " i/INDEX\n"                 + TextColour.RESET
        + TextColour.YELLOW + DASH + LIST_BUDGET   + "\n"                         + TextColour.RESET
        + TextColour.BLUE   + DASH + DEDUCT_BUDGET + " i/INDEX a/AMOUNT\n"        + TextColour.RESET
        + TextColour.CYAN   + DASH + MODIFY_BUDGET
                  + " i/INDEX n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY\n" + TextColour.RESET
        + DASH + HELP_COMMAND + " to check all possible commands\n"
        + DASH + EXIT_COMMAND + " to exit to the main menu\n"
        + LINE_SEPARATOR;

    private Command command;

    /**
     * Constructs a BudgetGeneralCommand by parsing the user input and initializing the corresponding command.
     * If the input is exactly "budget", the user is prompted for further details.
     * @param input the full user input command string.
     * @param budgetManager the budget manager to operate on.
     */
    public BudgetGeneralCommand(String input, BudgetManager budgetManager) {
        try {
            if (input.startsWith(SET_BUDGET)) {
                command = BudgetParser.parseSetBudgetCommand(input, budgetManager);
            } else if (input.startsWith(DEDUCT_BUDGET)) {
                command = BudgetParser.parseDeductBudgetCommand(input, budgetManager);
            } else if (input.startsWith(LIST_BUDGET)) {
                command = BudgetParser.parseListBudgetCommand(budgetManager);
            } else if (input.startsWith(CHECK_BUDGET)) {
                command = BudgetParser.parseCheckBudgetCommand(input, budgetManager);
            } else if (input.startsWith(MODIFY_BUDGET)) {
                command = BudgetParser.parseModifyBudgetCommand(input, budgetManager);
            } else if (input.startsWith(HELP_COMMAND)){
                command = new BudgetHelpCommand();
            } else {
                IOHandler.writeError("Unknown budget command. Type 'help' for help.");
            }
        } catch (BudgetException e) {
            IOHandler.writeError(e.getMessage());
        }
    }


    @Override
    public void execute() {
        if (command != null) {
            command.execute();
        } else{
            IOHandler.writeError("You have entered an invalid command. Type 'help' for help.");
        }
    }

    //the 'main' function to all budget commands
    public static void handleBudgetCommand(Scanner scanner, BudgetManager budgetManager) {
        IOHandler.writeOutput("Here's a list of budget commands: \n" + BUDGET_COMMANDS);
        while (true){
            IOHandler.writeOutputNoLn("> ");
            String input = scanner.nextLine().trim();
            if (input.startsWith(EXIT_COMMAND)) {
                break;
            }
            BudgetGeneralCommand command = new BudgetGeneralCommand(input, budgetManager);
            command.execute();
        }
    }
}
