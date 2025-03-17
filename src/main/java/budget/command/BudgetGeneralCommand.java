package budget.command;

import cashflow.command.Command;
import cashflow.model.interfaces.BudgetManager;
import java.util.Scanner;

public class BudgetGeneralCommand implements Command {
    private static final String BUDGET_COMMANDS =
            "- set-budget n/BUDGET_NAME a/AMOUNT\n"
                    + "- check-budget\n"
                    + "- deduct-budget i/INDEX a/AMOUNT\n"
                    + "- add-budget i/INDEX a/AMOUNT\n";

    private Command command;

    /**
     * Constructs a BudgetGeneralCommand by parsing the user input and initializing the corresponding command.
     * If the input is exactly "budget", the user is prompted for further details.
     *
     * Expected budget subcommand formats:
     * - set-budget n/BUDGET_NAME a/AMOUNT
     * - check-budget
     * - deduct-budget i/INDEX a/AMOUNT
     * - add-budget i/INDEX a/AMOUNT
     *
     * @param input the full user input command string.
     * @param budgetManager the budget manager to operate on.
     */
    public BudgetGeneralCommand(String input, BudgetManager budgetManager) {
        if (input.trim().equalsIgnoreCase("budget")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(BUDGET_COMMANDS + "Enter budget command: ");
            input = scanner.nextLine().trim();
        }
        input = input.trim();
        String lowerInput = input.toLowerCase();
        try {
            if (lowerInput.startsWith("set-budget")) {
                command = parseSetBudgetCommand(input, budgetManager);
            } else if (lowerInput.startsWith("check-budget")) {
                command = parseCheckBudgetCommand(budgetManager);
            } else if (lowerInput.startsWith("deduct-budget")) {
                command = parseDeductBudgetCommand(input, budgetManager);
            } else if (lowerInput.startsWith("add-budget")) {
                command = parseAddBudgetCommand(input, budgetManager);
            } else {
                System.out.println("Unknown budget command.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount entered.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static Command parseSetBudgetCommand(String input, BudgetManager budgetManager)
            throws NumberFormatException {
        // Expected format: set-budget n/BUDGET_NAME a/AMOUNT
        int nIndex = input.indexOf("n/");
        int aIndex = input.indexOf("a/");
        if (nIndex == -1 || aIndex == -1) {
            throw new IllegalArgumentException("Invalid set-budget command format. " +
                    "Expected: set-budget n/BUDGET_NAME a/AMOUNT");
        }
        String name = input.substring(nIndex + 2, aIndex).trim();
        String amountStr = input.substring(aIndex + 2).trim();
        double amount = Double.parseDouble(amountStr);
        return new SetBudgetCommand(budgetManager, name, amount);
    }


    private static Command parseCheckBudgetCommand(BudgetManager budgetManager) {
        // Expected format: check-budget (no extra parameters)
        return new CheckBudgetCommand(budgetManager);
    }

    private static Command parseDeductBudgetCommand(String input, BudgetManager budgetManager)
            throws NumberFormatException {
        // Expected format: deduct-budget i/INDEX a/AMOUNT
        int iIndex = input.indexOf("i/");
        int aIndex = input.indexOf("a/");
        if (iIndex == -1 || aIndex == -1) {
            throw new IllegalArgumentException("Invalid deduct-budget command format.");
        }
        String indexStr = input.substring(iIndex + 2, aIndex).trim();
        String amountStr = input.substring(aIndex + 2).trim();
        int index = Integer.parseInt(indexStr);
        double amount = Double.parseDouble(amountStr);
        return new DeductFromBudgetCommand(budgetManager, index, amount);
    }

    private static Command parseAddBudgetCommand(String input, BudgetManager budgetManager)
            throws NumberFormatException {
        // Expected format: add-budget n/BUDGET_NAME a/AMOUNT
        int iIndex = input.indexOf("i/");
        int aIndex = input.indexOf("a/");
        if (iIndex == -1 || aIndex == -1) {
            throw new IllegalArgumentException("Invalid add-budget command format.");
        }
        String indexStr = input.substring(iIndex + 2, aIndex).trim();
        String amountStr = input.substring(aIndex + 2).trim();
        int index = Integer.parseInt(indexStr);
        double amount = Double.parseDouble(amountStr);
        return new AddToBudgetCommand(budgetManager, index, amount);
    }

    @Override
    public void execute() {
        try {
            command.execute();
        } catch (Exception e) {
            System.err.println("An error has occurred when executing the budget command.");
        }
    }
}
