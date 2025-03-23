package expense_income.expense;

import expense_income.expense.commands.AddExpenseCommand;
import expense_income.expense.commands.DeleteExpenseCommand;
import expense_income.expense.commands.ExpenseCommand;
import expense_income.expense.commands.ListExpenseCommand;
import expense_income.expense.commands.EditExpenseCommand;
import expense_income.expense.commands.SortExpenseCommand;
import java.time.LocalDate;

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
                System.out.println("Usage: add <description> <amount> [yyyy-mm-dd]");
                return null;
            }
            try {
                String description = parts[1];
                String[] amountAndDate = parts[2].split(" ");
                double amount = Double.parseDouble(amountAndDate[0]);
                LocalDate date = (amountAndDate.length > 1) ? LocalDate.parse(amountAndDate[1]) : LocalDate.now();
                return new AddExpenseCommand(description, amount, date);
            } catch (Exception e) {
                System.out.println("Invalid input. Use: add <description> <amount> [yyyy-mm-dd]");
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
                return new DeleteExpenseCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a number.");
                return null;
            }

        case "edit":
            if (parts.length < 3) {
                System.out.println("Usage: edit <index> <newDescription> <newAmount> [yyyy-mm-dd]");
                return null;
            }
            try {
                int index = Integer.parseInt(parts[1]);
                String[] values = parts[2].split(" ");
                if (values.length < 2) {
                    System.out.println("Usage: edit <index> <newDescription> <newAmount> [yyyy-mm-dd]");
                    return null;
                }
                String newDescription = values[0];
                double newAmount = Double.parseDouble(values[1]);
                LocalDate newDate = (values.length >= 3) ? LocalDate.parse(values[2]) : LocalDate.now();
                return new EditExpenseCommand(index, newDescription, newAmount, newDate);
            } catch (Exception e) {
                System.out.println("Invalid input. Use: edit <index> <newDescription> <newAmount> [yyyy-mm-dd]");
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
