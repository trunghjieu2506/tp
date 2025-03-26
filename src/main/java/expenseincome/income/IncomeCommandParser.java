package expenseincome.income;

import expenseincome.income.commands.AddIncomeCommand;
import expenseincome.income.commands.DeleteIncomeCommand;
import expenseincome.income.commands.IncomeCommand;
import expenseincome.income.commands.ListIncomeCommand;
import expenseincome.income.commands.EditIncomeCommand;
import expenseincome.income.commands.SortIncomeCommand;
import expenseincome.income.commands.ListCategoryIncomeCommand;
import java.time.LocalDate;

public class IncomeCommandParser {
    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static IncomeCommand parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Empty input. Please enter a command.");
            return null;
        }

        String[] parts = input.trim().split(" ", 3);
        if (parts.length == 0) {
            System.out.println("Invalid input format.");
            return null;
        }

        String commandType = parts[0];

        switch (commandType) {
        case "add":
            if (parts.length < 3) {
                System.out.println("Usage: add <source> <amount> <category> [yyyy-mm-dd]");
                return null;
            }

            try {
                String[] args = parts[2].split(" ");
                if (args.length < 2) {
                    System.out.println("Usage: add <source> <amount> <category> [yyyy-mm-dd]");
                    return null;
                }

                String source = parts[1];
                double amount = Double.parseDouble(args[0]);
                String rawCategory = args[1];
                String category = capitalize(rawCategory);
                LocalDate date = (args.length >= 3) ? LocalDate.parse(args[2]) : LocalDate.now();

                return new AddIncomeCommand(source, amount, date, category);
            } catch (Exception e) {
                System.out.println("Invalid input. Format: add <source> <amount> <category> [yyyy-mm-dd]");
                return null;
            }

        case "list":
            if (parts.length >= 2 && parts[1].equalsIgnoreCase("category")) {
                if (parts.length < 3) {
                    System.out.println("Usage: list category <categoryName>");
                    return null;
                }
                String category = capitalize(parts[2].trim());
                return new ListCategoryIncomeCommand(category);
            }
            return new ListIncomeCommand();

        case "delete":
            if (parts.length < 2) {
                System.out.println("Usage: delete <index>");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                if (index < 1) {
                    System.out.println("Index must be a positive number.");
                    return null;
                }
                return new DeleteIncomeCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a valid number.");
                return null;
            }

        case "edit":
            if (parts.length < 3) {
                System.out.println("Usage: edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]");
                return null;
            }

            try {
                int index = Integer.parseInt(parts[1]);
                String[] args = parts[2].split(" ");

                if (args.length < 3) {
                    System.out.println("Usage: edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]");
                    return null;
                }

                String newSource = args[0];
                double newAmount = Double.parseDouble(args[1]);
                String rawCategory = args[2];
                String newCategory = capitalize(rawCategory);
                LocalDate newDate = (args.length >= 4) ? LocalDate.parse(args[3]) : LocalDate.now();

                return new EditIncomeCommand(index, newSource, newAmount, newDate, newCategory);
            } catch (Exception e) {
                System.out.println("Invalid input. Format: edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]");
                return null;
            }

        case "sort":
            if (parts.length < 2) {
                System.out.println("Usage: sort <recent|oldest>");
                return null;
            }
            String sortType = parts[1].toLowerCase();
            if (sortType.equals("recent")) {
                return new SortIncomeCommand(true);
            } else if (sortType.equals("oldest")) {
                return new SortIncomeCommand(false);
            } else {
                System.out.println("Unknown sort type. Use 'recent' or 'oldest'.");
                return null;
            }

        default:
            System.out.println("Unknown income command: " + commandType);
            return null;
        }
    }
}
