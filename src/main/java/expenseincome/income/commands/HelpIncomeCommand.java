package expenseincome.income.commands;

import utils.io.IOHandler;
import utils.textcolour.TextColour;
import expenseincome.income.IncomeManager;

/**
 * HelpIncomeCommand displays all the available income commands in a user-friendly format.
 */
public class HelpIncomeCommand extends IncomeCommand {

    public static final String ADD_COMMAND = "add";
    public static final String EDIT_COMMAND = "edit";
    public static final String DELETE_COMMAND = "delete";
    public static final String LIST_COMMAND = "list";
    public static final String SORT_COMMAND = "sort";
    public static final String TOP_COMMAND = "top";
    public static final String BOTTOM_COMMAND = "bottom";
    public static final String HELP_COMMAND = "help";
    public static final String EXIT_COMMAND = "exit";

    public static final String LINE_SEPARATOR = "-".repeat(70);
    public static final String DASH = "- ";

    static final String INCOME_COMMANDS =
            LINE_SEPARATOR + '\n'
                    + TextColour.PURPLE  + DASH + ADD_COMMAND
                    + " <source> <amount> <category> [yyyy-mm-dd]\n"  + TextColour.RESET
                    + TextColour.GREEN   + DASH + EDIT_COMMAND
                    + " <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]\n" + TextColour.RESET
                    + TextColour.YELLOW  + DASH + DELETE_COMMAND
                    + " <index>\n" + TextColour.RESET
                    + TextColour.CYAN    + DASH + LIST_COMMAND
                    + " / list category <categoryName>\n" + TextColour.RESET
                    + TextColour.BLUE    + DASH + SORT_COMMAND
                    + " recent | oldest\n" + TextColour.RESET
                    + TextColour.RED     + DASH + TOP_COMMAND
                    + " (shows category with highest income)\n" + TextColour.RESET
                    + TextColour.RED     + DASH + BOTTOM_COMMAND
                    + " (shows category with lowest income)\n" + TextColour.RESET
                    + DASH + HELP_COMMAND
                    + " to check all available income commands\n"
                    + DASH + EXIT_COMMAND
                    + " to return to the main menu\n"
                    + LINE_SEPARATOR;

    /**
     * Executes the help command by printing available income-related commands.
     *
     * @param manager The IncomeManager context, unused in this command.
     */
    @Override
    public void execute(IncomeManager manager) {
        IOHandler.writeOutput("Here's a list of income commands:\n" + INCOME_COMMANDS);
    }
}
