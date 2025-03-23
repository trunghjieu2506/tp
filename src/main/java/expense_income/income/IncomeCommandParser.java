package expense_income.income;

import expense_income.income.commands.AddIncomeCommand;
import expense_income.income.commands.DeleteIncomeCommand;
import expense_income.income.commands.IncomeCommand;
import expense_income.income.commands.ListIncomeCommand;
import expense_income.income.commands.EditIncomeCommand;
import expense_income.income.commands.SortIncomeCommand;
import java.time.LocalDate;

public class IncomeCommandParser {

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
                System.out.println("Usage: add <source> <amount> [yyyy-mm-dd]");
                return null;
            }
            try {
                String source = parts[1];
                String[] amtAndDate = parts[2].split(" ");
                double amount = Double.parseDouble(amtAndDate[0]);
                LocalDate date = (amtAndDate.length >= 2) ? LocalDate.parse(amtAndDate[1]) : LocalDate.now();
                return new AddIncomeCommand(source, amount, date);
            } catch (Exception e) {
                System.out.println("Invalid input. Please use: add <source> <amount> [yyyy-mm-dd] - optional");
                return null;
            }

        case "list":
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
                System.out.println("Usage: edit <index> <newSource> <newAmount> [yyyy-mm-dd]");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                String[] values = parts[2].split(" ");
                if (values.length < 2) {
                    System.out.println("Usage: edit <index> <newSource> <newAmount> [yyyy-mm-dd]");
                    return null;
                }
                String newSource = values[0];
                double newAmount = Double.parseDouble(values[1]);
                LocalDate newDate = (values.length >= 3) ? LocalDate.parse(values[2]) : LocalDate.now();
                return new EditIncomeCommand(index, newSource, newAmount, newDate);
            } catch (Exception e) {
                System.out.println("Invalid input. Please use: edit <index> <newSource> <newAmount> [yyyy-mm-dd]");
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
