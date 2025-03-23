package expense_income.income;

import expense_income.income.commands.AddIncomeCommand;
import expense_income.income.commands.DeleteIncomeCommand;
import expense_income.income.commands.IncomeCommand;
import expense_income.income.commands.ListIncomeCommand;
import expense_income.income.commands.EditIncomeCommand;

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
                System.out.println("Usage: add <source> <amount>");
                return null;
            }
            try {
                String source = parts[1];
                double amount = Double.parseDouble(parts[2]);

                if (source.trim().isEmpty()) {
                    System.out.println("Source cannot be empty.");
                    return null;
                }
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    return null;
                }

                return new AddIncomeCommand(source, amount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
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
                System.out.println("Usage: edit <index> <newSource> <newAmount>");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                String[] sourceAndAmount = parts[2].split(" ");

                if (sourceAndAmount.length < 2) {
                    System.out.println("Usage: edit <index> <newSource> <newAmount>");
                    return null;
                }

                String newSource = sourceAndAmount[0];
                double newAmount = Double.parseDouble(sourceAndAmount[1]);

                if (index < 1) {
                    System.out.println("Index must be a positive number.");
                    return null;
                }
                if (newSource.trim().isEmpty()) {
                    System.out.println("Source cannot be empty.");
                    return null;
                }
                if (newAmount <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    return null;
                }

                return new EditIncomeCommand(index, newSource, newAmount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter valid numbers.");
                return null;
            } catch (Exception e) {
                System.out.println("Invalid input for edit command.");
                return null;
            }

        default:
            System.out.println("Unknown income command: " + commandType);
            return null;
        }
    }
}
