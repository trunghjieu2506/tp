package expenseincome.income;

import expenseincome.income.commands.AddIncomeCommand;
import expenseincome.income.commands.DeleteIncomeCommand;
import expenseincome.income.commands.ListIncomeCommand;
import expenseincome.income.commands.EditIncomeCommand;
import expenseincome.income.commands.SortIncomeCommand;
import expenseincome.income.commands.ListCategoryIncomeCommand;
import expenseincome.income.commands.TopCategoryIncomeCommand;
import expenseincome.income.commands.BottomCategoryIncomeCommand;
import expenseincome.income.commands.HelpIncomeCommand;
import expenseincome.income.exceptions.IncomeException;


import java.time.LocalDate;

public class IncomeCommandParser {

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static IncomeParserResult parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new IncomeParserResult(null, "Please enter a command.");
        }

        String[] parts = input.trim().split(" ", 3);
        String commandType = parts[0].toLowerCase();

        return switch (commandType) {
            case "add" -> parseAdd(parts);
            case "edit" -> parseEdit(parts);
            case "delete" -> parseDelete(parts);
            case "list" -> parseList(parts);
            case "sort" -> parseSort(parts);
            case "top" -> new IncomeParserResult(new TopCategoryIncomeCommand(), null);
            case "bottom" -> new IncomeParserResult(new BottomCategoryIncomeCommand(), null);
            case "help" -> new IncomeParserResult(new HelpIncomeCommand(), null);
            default -> new IncomeParserResult(null, "Unknown command: " + commandType);
        };
    }

    private static IncomeParserResult parseAdd(String[] parts) {
        if (parts.length < 3) {
            return new IncomeParserResult(null, "Usage: add <source> <amount> <category> [yyyy-mm-dd]");
        }

        try {
            String source = parts[1];
            String[] args = parts[2].split(" ");
            if (args.length < 2) {
                throw new IncomeException("Please provide amount and category.");
            }

            double amount = Double.parseDouble(args[0]);
            if (amount <= 0) {
                throw new IncomeException("Amount must be greater than zero.");
            }

            String category = capitalize(args[1].trim());
            LocalDate date = (args.length >= 3) ? LocalDate.parse(args[2]) : LocalDate.now();

            return new IncomeParserResult(new AddIncomeCommand(source, amount, date, category), null);
        } catch (IncomeException e) {
            return new IncomeParserResult(null, e.getMessage());
        } catch (NumberFormatException e) {
            return new IncomeParserResult(null, "Invalid amount. Please enter a number.");
        } catch (Exception e) {
            return new IncomeParserResult(null, "Invalid date or command format.");
        }
    }

    private static IncomeParserResult parseEdit(String[] parts) {
        if (parts.length < 3) {
            return new IncomeParserResult(null, "Usage: edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            String[] args = parts[2].split(" ");
            if (args.length < 3) {
                throw new IncomeException("Please provide all required fields for editing.");
            }

            String newSource = args[0];
            double newAmount = Double.parseDouble(args[1]);
            if (newAmount <= 0) {
                throw new IncomeException("Amount must be greater than zero.");
            }

            String newCategory = capitalize(args[2].trim());
            LocalDate newDate = (args.length >= 4) ? LocalDate.parse(args[3]) : LocalDate.now();

            return new IncomeParserResult(new EditIncomeCommand(index, newSource, newAmount, newDate, newCategory), null);
        } catch (IncomeException e) {
            return new IncomeParserResult(null, e.getMessage());
        } catch (NumberFormatException e) {
            return new IncomeParserResult(null, "Invalid number format in edit command.");
        } catch (Exception e) {
            return new IncomeParserResult(null, "Invalid date or edit command format.");
        }
    }

    private static IncomeParserResult parseDelete(String[] parts) {
        if (parts.length < 2) {
            return new IncomeParserResult(null, "Usage: delete <index>");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            return new IncomeParserResult(new DeleteIncomeCommand(index), null);
        } catch (NumberFormatException e) {
            return new IncomeParserResult(null, "Invalid index. Please enter a number.");
        }
    }

    private static IncomeParserResult parseList(String[] parts) {
        if (parts.length >= 2 && parts[1].equalsIgnoreCase("category")) {
            if (parts.length < 3) {
                return new IncomeParserResult(null, "Usage: list category <categoryName>");
            }
            String category = capitalize(parts[2].trim());
            return new IncomeParserResult(new ListCategoryIncomeCommand(category), null);
        }
        return new IncomeParserResult(new ListIncomeCommand(), null);
    }

    private static IncomeParserResult parseSort(String[] parts) {
        if (parts.length < 2) {
            return new IncomeParserResult(null, "Usage: sort <recent|oldest>");
        }

        String sortType = parts[1].toLowerCase();
        return switch (sortType) {
            case "recent" -> new IncomeParserResult(new SortIncomeCommand(true), null);
            case "oldest" -> new IncomeParserResult(new SortIncomeCommand(false), null);
            default -> new IncomeParserResult(null, "Unknown sort type. Use 'recent' or 'oldest'.");
        };
    }
}
