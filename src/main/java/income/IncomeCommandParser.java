package income;

import income.commands.AddCommand;
import income.commands.DeleteCommand;
import income.commands.IncomeCommand;
import income.commands.ListIncomeCommand;

public class IncomeCommandParser {
    public static IncomeCommand parseCommand(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length == 0) {
            return null;
        }

        String commandType = parts[0];

        switch (commandType) {
        case "add-income":
            if (parts.length < 3) {
                System.out.println("Usage: add-income <source> <amount>");
                return null;
            }
            try {
                double amount = Double.parseDouble(parts[2]);
                return new AddCommand(parts[1], amount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
                return null;
            }

        case "list-income":
            return new ListIncomeCommand(); // List only incomes

        case "delete-income":
            if (parts.length < 2) {
                System.out.println("Usage: delete-income <number>");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a number.");
                return null;
            }

        default:
            return null;
        }
    }
}
