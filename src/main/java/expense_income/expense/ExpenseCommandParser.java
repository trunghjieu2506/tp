package expense_income.expense;

import expense_income.expense.commands.AddExpenseCommand;
import expense_income.expense.commands.DeleteExpenseCommand;
import expense_income.expense.commands.ExpenseCommand;
import expense_income.expense.commands.ListExpenseCommand;
import expense_income.expense.commands.EditExpenseCommand;
import expense_income.expense.commands.SortExpenseCommand;
import expense_income.expense.commands.ListCategoryExpenseCommand;
import java.time.LocalDate;

public class ExpenseCommandParser {

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static ExpenseCommand parseCommand(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length == 0) {
            return null;
        }

        String commandType = parts[0];

        switch (commandType) {
        case "add":
            if (parts.length < 3) {
                System.out.println("Usage: add <desc> <amount> <category> [yyyy-mm-dd]");
                return null;
            }

            try {
                String[] args = parts[2].split(" ");

                if (args.length < 2) {
                    System.out.println("Usage: add <desc> <amount> <category> [yyyy-mm-dd]");
                    return null;
                }

                String description = parts[1];
                double amount = Double.parseDouble(args[0]);

                String rawCategory = args[1];
                String category = capitalize(rawCategory.trim());

                LocalDate date = (args.length >= 3) ? LocalDate.parse(args[2]) : LocalDate.now();

                return new AddExpenseCommand(description, amount, date, category);
            } catch (Exception e) {
                System.out.println("Invalid input. Format: add <desc> <amount> <category> [yyyy-mm-dd]");
                return null;
            }

        case "list":
            if (parts.length >= 2 && parts[1].equalsIgnoreCase("category")) {
                if (parts.length < 3) {
                    System.out.println("Usage: list category <categoryName>");
                    return null;
                }
                String category = capitalize(parts[2].trim());
                return new ListCategoryExpenseCommand(category);
            }
            return new ListExpenseCommand();

        case "delete":
            if (parts.length < 2) {
                System.out.println("Usage: delete <number>");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                return new DeleteExpenseCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a number.");
                return null;
            }

        case "edit":
            if (parts.length < 3) {
                System.out.println("Usage: edit <index> <newDesc> <newAmount> <newCategory> [yyyy-mm-dd]");
                return null;
            }

            try {
                int index = Integer.parseInt(parts[1]);
                String[] args = parts[2].split(" ");

                if (args.length < 3) {
                    System.out.println("Usage: edit <index> <newDesc> <newAmount> <newCategory> [yyyy-mm-dd]");
                    return null;
                }

                String newDesc = args[0];
                double newAmount = Double.parseDouble(args[1]);

                String rawCategory = args[2];
                String newCategory = capitalize(rawCategory.trim());

                LocalDate newDate = (args.length >= 4) ? LocalDate.parse(args[3]) : LocalDate.now();

                return new EditExpenseCommand(index, newDesc, newAmount, newDate, newCategory);
            } catch (Exception e) {
                System.out.println("Invalid input. Format: edit <index> <newDesc> <newAmount> <newCategory> [yyyy-mm-dd]");
                return null;
            }

        case "sort":
            if (parts.length < 2) {
                System.out.println("Usage: sort <recent|oldest>");
                return null;
            }
            String sortType = parts[1].toLowerCase();
            if (sortType.equals("recent")) {
                return new SortExpenseCommand(true);
            } else if (sortType.equals("oldest")) {
                return new SortExpenseCommand(false);
            } else {
                System.out.println("Unknown sort type. Use 'recent' or 'oldest'.");
                return null;
            }

        default:
            return null;
        }
    }
}
