package expense_income.expense;

import expense_income.expense.commands.AddCommand;
import expense_income.expense.commands.DeleteCommand;
import expense_income.expense.commands.ExpenseCommand;
import expense_income.expense.commands.ListExpenseCommand;
import expense_income.expense.commands.EditExpenseCommand;

public class ExpenseCommandParser {

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

        case "edit":
            if (parts.length < 3) {
                System.out.println("Usage: edit <index> <newDesc> <newAmount>");
                return null;
            }
            try {
                String[] descAndAmount = parts[2].split(" ");
                if (descAndAmount.length < 2) {
                    System.out.println("Usage: edit <index> <newDesc> <newAmount>");
                    return null;
                }
                int index = Integer.parseInt(parts[1]);
                String newDesc = descAndAmount[0];
                double newAmount = Double.parseDouble(descAndAmount[1]);
                return new EditExpenseCommand(index, newDesc, newAmount);
            } catch (Exception e) {
                System.out.println("Invalid input for edit command.");
                return null;
            }

        default:
            return null;
        }
    }
}
