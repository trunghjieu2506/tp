package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class HelpIncomeCommand extends IncomeCommand {
    @Override
    public void execute(IncomeManager manager) {
        System.out.println("Income Command Help:");
        System.out.println("  add <source> <amount> <category> [yyyy-mm-dd]");
        System.out.println("    - Adds a new income.");
        System.out.println("    - Source must be ONE word (use '-' for spacing).");
        System.out.println("    - Example: add Part-Time 1500 Freelance 2025-04-01");
        System.out.println();

        System.out.println("  edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]");
        System.out.println("    - Edits an existing income at the given index.");
        System.out.println("    - Use '-' for spaces in newSource.");
        System.out.println("    - Example: edit 1 Bonus-Pay 500 Job 2025-04-02");
        System.out.println();

        System.out.println("  delete <index>");
        System.out.println("    - Deletes the income at the given index.");
        System.out.println("    - Example: delete 2");
        System.out.println();

        System.out.println("  list");
        System.out.println("    - Lists all recorded incomes.");
        System.out.println();

        System.out.println("  list category <categoryName>");
        System.out.println("    - Lists incomes for the specified category.");
        System.out.println("    - Example: list category Job");
        System.out.println();

        System.out.println("  sort recent");
        System.out.println("    - Sorts incomes from most recent to oldest.");
        System.out.println("  sort oldest");
        System.out.println("    - Sorts incomes from oldest to most recent.");
        System.out.println();

        System.out.println("  top");
        System.out.println("    - Displays the category with the highest income.");
        System.out.println("  bottom");
        System.out.println("    - Displays the category with the lowest income.");
        System.out.println();

        System.out.println("  help");
        System.out.println("    - Displays this help message.");
    }
}
