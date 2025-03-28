package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;

public class HelpExpenseCommand extends ExpenseCommand {

    // ANSI color codes
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    @Override
    public void execute(ExpenseManager manager) {
        System.out.println(CYAN + "Expense Commands:" + RESET);

        System.out.println(YELLOW + "- add <desc> <amount> <category> [yyyy-mm-dd]" + RESET);
        System.out.println("    Adds a new expense with a description, amount, category, and optional date.");

        System.out.println(YELLOW + "- list" + RESET);
        System.out.println("    Lists all recorded expenses.");

        System.out.println(YELLOW + "- list category <categoryName>" + RESET);
        System.out.println("    Lists all expenses in the specified category.");

        System.out.println(YELLOW + "- delete <index>" + RESET);
        System.out.println("    Deletes the expense at the given index.");

        System.out.println(YELLOW + "- edit <index> <newDesc> <newAmount> <newCategory> [yyyy-mm-dd]" + RESET);
        System.out.println("    Edits an existing expense at the given index.");

        System.out.println(YELLOW + "- sort recent | oldest" + RESET);
        System.out.println("    Sorts expenses by date: most recent or oldest first.");

        System.out.println(YELLOW + "- top" + RESET);
        System.out.println("    Displays the category with the highest total spending.");

        System.out.println(YELLOW + "- bottom" + RESET);
        System.out.println("    Displays the category with the lowest total spending.");

        System.out.println(YELLOW + "- help" + RESET);
        System.out.println("    Displays this list of available expense commands.");
    }
}
