package seedu.duke.expense;

import seedu.duke.expense.commands.*;
import seedu.duke.income.IncomeManager;

public class ExpenseCommandParser {
    private static IncomeManager incomeManager; // Reference to IncomeManager

    public static void setIncomeManager(IncomeManager manager) {
        incomeManager = manager;
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
            return new ListAllCommand(incomeManager); // List both expenses and incomes

        case "list-expense":
            return new ListExpenseCommand(); // List only expenses

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

        default:
            return null;
        }
    }
}
