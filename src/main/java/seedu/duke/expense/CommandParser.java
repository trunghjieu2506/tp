package seedu.duke.expense;

import seedu.duke.expense.commands.*;

public class CommandParser {
    public static Command parseCommand(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length == 0) {
            return null;
        }

        String commandType = parts[0];

        switch (commandType) {
        case "add":
            if (parts.length < 3) {
                System.out.println("Usage: add <desc> <amount>");
                return null;
            }
            try {
                double amount = Double.parseDouble(parts[2]);
                return new AddCommand(parts[1], amount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
                return null;
            }

        case "list":
            return new ListCommand();

        case "delete":
            if (parts.length < 2) {
                System.out.println("Usage: delete <number>");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a number.");
                return null;
            }

        case "exit":
            return null;

        default:
            System.out.println("Invalid command. Use: add <desc> <amount>, list, delete <number>, exit");
            return null;
        }
    }
}
