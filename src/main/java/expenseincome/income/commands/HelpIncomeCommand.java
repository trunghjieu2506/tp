package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class HelpIncomeCommand extends IncomeCommand {

    // ANSI color codes (works in most terminals)
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    @Override
    public void execute(IncomeManager manager) {
        System.out.println(CYAN + "Income Commands:" + RESET);

        System.out.println(YELLOW + "- add <source> <amount> <category> [yyyy-mm-dd]" + RESET);
        System.out.println("    Adds a new income with source, amount, category, and optional date.");

        System.out.println(YELLOW + "- list" + RESET);
        System.out.println("    Lists all recorded incomes.");

        System.out.println(YELLOW + "- list category <categoryName>" + RESET);
        System.out.println("    Lists all incomes in the specified category.");

        System.out.println(YELLOW + "- delete <index>" + RESET);
        System.out.println("    Deletes the income at the given index.");

        System.out.println(YELLOW + "- edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]" + RESET);
        System.out.println("    Edits an existing income at the given index.");

        System.out.println(YELLOW + "- sort recent | oldest" + RESET);
        System.out.println("    Sorts incomes by date, either most recent or oldest first.");

        System.out.println(YELLOW + "- top" + RESET);
        System.out.println("    Shows the category with the highest total income.");

        System.out.println(YELLOW + "- bottom" + RESET);
        System.out.println("    Shows the category with the lowest total income.");

        System.out.println(YELLOW + "- help" + RESET);
        System.out.println("    Displays this list of income commands.");
    }
}
