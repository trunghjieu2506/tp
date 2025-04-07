package expenseincome.expense;

import expenseincome.expense.commands.AddExpenseCommand;
import expenseincome.expense.commands.DeleteExpenseCommand;
import expenseincome.expense.commands.ListExpenseCommand;
import expenseincome.expense.commands.EditExpenseCommand;
import expenseincome.expense.commands.SortExpenseCommand;
import expenseincome.expense.commands.ListCategoryExpenseCommand;
import expenseincome.expense.commands.TopCategoryExpenseCommand;
import expenseincome.expense.commands.BottomCategoryExpenseCommand;
import expenseincome.expense.commands.HelpExpenseCommand;
import expenseincome.expense.exceptions.ExpenseException;

import java.time.LocalDate;
/**
 * Parses string-based user input into executable ExpenseCommand objects.
 * Also returns feedback messages when the input is invalid.
 * This class serves as the CLI interface logic for the Expense module.
 */
public class ExpenseCommandParser {
    /**
     * Capitalizes the first letter of a string and lowercases the rest.
     *
     * @param input the string to capitalize
     * @return the formatted string
     */
    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    /**
     * Parses a full command string and returns a structured result.
     *
     * @param input the user input string
     * @return ExpenseParserResult containing either a command or feedback
     */
    public static ExpenseParserResult parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ExpenseParserResult(null, "Please enter a command.");
        }

        String[] parts = input.trim().split(" ", 3);
        String commandType = parts[0].toLowerCase();

        return switch (commandType) {
        case "add" -> parseAdd(parts);
        case "edit" -> parseEdit(parts);
        case "delete" -> parseDelete(parts);
        case "list" -> parseList(parts);
        case "sort" -> parseSort(parts);
        case "top" -> new ExpenseParserResult(new TopCategoryExpenseCommand(), null);
        case "bottom" -> new ExpenseParserResult(new BottomCategoryExpenseCommand(), null);
        case "help" -> new ExpenseParserResult(new HelpExpenseCommand(), null);
        default -> new ExpenseParserResult(null, "Unknown command: " + commandType);
        };
    }

    private static ExpenseParserResult parseAdd(String[] parts) {
        if (parts.length < 3) {
            return new ExpenseParserResult(null, "Usage: add <description> <amount> <category> [yyyy-mm-dd]");
        }

        try {
            String description = parts[1];
            String[] args = parts[2].split(" ");
            if (args.length < 2) {
                throw new ExpenseException("Please provide both amount and category.");
            }

            double amount = Double.parseDouble(args[0]);
            if (amount <= 0) {
                throw new ExpenseException("Amount must be greater than zero.");
            }

            String category = capitalize(args[1].trim());
            LocalDate date = (args.length >= 3) ? LocalDate.parse(args[2]) : LocalDate.now();

            return new ExpenseParserResult(new AddExpenseCommand(description, amount, date, category), null);
        } catch (ExpenseException e) {
            return new ExpenseParserResult(null, e.getMessage());
        } catch (NumberFormatException e) {
            return new ExpenseParserResult(null, "Invalid amount. Please enter a number.");
        } catch (Exception e) {
            return new ExpenseParserResult(null, "Invalid date or command format.");
        }
    }

    private static ExpenseParserResult parseEdit(String[] parts) {
        if (parts.length < 3) {
            return new ExpenseParserResult(null, "Usage: edit <index> " +
                    "<newDesc> <newAmount> <newCategory> [yyyy-mm-dd]");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            String[] args = parts[2].split(" ");
            if (args.length < 3) {
                throw new ExpenseException("Please provide all required fields for editing.");
            }

            String newDesc = args[0];
            double newAmount = Double.parseDouble(args[1]);
            if (newAmount <= 0) {
                throw new ExpenseException("Amount must be greater than zero.");
            }

            String newCategory = capitalize(args[2].trim());
            LocalDate newDate = (args.length >= 4) ? LocalDate.parse(args[3]) : LocalDate.now();

            return new ExpenseParserResult(new EditExpenseCommand(index, newDesc,
                    newAmount, newDate, newCategory), null);
        } catch (ExpenseException e) {
            return new ExpenseParserResult(null, e.getMessage());
        } catch (NumberFormatException e) {
            return new ExpenseParserResult(null, "Invalid number format in edit command.");
        } catch (Exception e) {
            return new ExpenseParserResult(null, "Invalid date or edit command format.");
        }
    }

    private static ExpenseParserResult parseDelete(String[] parts) {
        if (parts.length < 2) {
            return new ExpenseParserResult(null, "Usage: delete <index>");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            if (index < 1) {
                throw new ExpenseException("Index must be a positive integer.");
            }
            return new ExpenseParserResult(new DeleteExpenseCommand(index), null);
        } catch (ExpenseException e) {
            return new ExpenseParserResult(null, e.getMessage());
        } catch (NumberFormatException e) {
            return new ExpenseParserResult(null, "Invalid index. Please enter a number.");
        }
    }

    private static ExpenseParserResult parseList(String[] parts) {
        if (parts.length >= 2 && parts[1].equalsIgnoreCase("category")) {
            if (parts.length < 3) {
                return new ExpenseParserResult(null, "Usage: list category <categoryName>");
            }
            String category = capitalize(parts[2].trim());
            return new ExpenseParserResult(new ListCategoryExpenseCommand(category), null);
        }
        return new ExpenseParserResult(new ListExpenseCommand(), null);
    }

    private static ExpenseParserResult parseSort(String[] parts) {
        if (parts.length < 2) {
            return new ExpenseParserResult(null, "Usage: sort <recent|oldest>");
        }

        String sortType = parts[1].toLowerCase();
        return switch (sortType) {
        case "recent" -> new ExpenseParserResult(new SortExpenseCommand(true), null);
        case "oldest" -> new ExpenseParserResult(new SortExpenseCommand(false), null);
        default -> new ExpenseParserResult(null, "Unknown sort type. Use 'recent' or 'oldest'.");
        };
    }
}
