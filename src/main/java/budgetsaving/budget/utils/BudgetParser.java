package budgetsaving.budget.utils;

import budgetsaving.budget.command.AddToBudgetCommand;
import budgetsaving.budget.command.ModifyBudgetCommand;
import budgetsaving.budget.command.CheckBudgetCommand;
import budgetsaving.budget.command.DeductFromBudgetCommand;
import budgetsaving.budget.command.ListBudgetCommand;
import budgetsaving.budget.command.SetBudgetCommand;
import budgetsaving.budget.exceptions.BudgetAttributeException;
import budgetsaving.budget.exceptions.BudgetException;
import budgetsaving.budget.exceptions.BudgetParserException;
import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;

import java.time.LocalDate;

//it should make sure that all the input to the command should follow its valid form, example:
//amount must be positive for set budget
public class BudgetParser {

    private static double isPositiveAmount(double amount) throws BudgetParserException {
        if (amount < 0) {
            throw new BudgetParserException("The amount you have entered is negative.");
        }
        return amount;
    }

    private static LocalDate isFutureDate(LocalDate endDate) throws BudgetParserException {
        if (endDate.isBefore(LocalDate.now())) {
            throw new BudgetParserException("The end date you have entered cannot be before the start date.");
        }
        return endDate;
    }

    // each method will handle its own max index based on their size
    private static int isValidIndex(int index) throws BudgetParserException {
        if (index < 0) {
            throw new BudgetParserException("The index you have entered is out of range.");
        }
        return index;
    }

    public static Command parseSetBudgetCommand(String input, BudgetManager budgetManager)
            throws BudgetParserException, BudgetAttributeException {
        BudgetAttributes attributes = new BudgetAttributes(input);
        String name = attributes.getName();
        String category = attributes.getCategory();
        double amount = isPositiveAmount(attributes.getAmount());
        LocalDate endDate = isFutureDate(attributes.getEndDate());
        return new SetBudgetCommand(budgetManager, name, amount, endDate, category);
    }

    public static Command parseCheckBudgetCommand(String input, BudgetManager budgetManager)
            throws BudgetParserException, BudgetAttributeException {
        BudgetAttributes attributes = new BudgetAttributes(input);
        int index = isValidIndex(attributes.getIndex());
        return new CheckBudgetCommand(index, budgetManager);
    }

    public static Command parseListBudgetCommand(BudgetManager budgetManager) {
        return new ListBudgetCommand(budgetManager);
    }

    public static Command parseDeductBudgetCommand(String input, BudgetManager budgetManager)
            throws BudgetParserException, BudgetAttributeException {
        BudgetAttributes attributes = new BudgetAttributes(input);
        int index = isValidIndex(attributes.getIndex());
        double amount = isPositiveAmount(attributes.getAmount());
        return new DeductFromBudgetCommand(budgetManager, index, amount);
    }

    public static Command parseAddBudgetCommand(String input, BudgetManager budgetManager)
            throws NumberFormatException, BudgetAttributeException {
        // Expected format: add-budget n/BUDGET_NAME a/AMOUNT
        BudgetAttributes attributes = new BudgetAttributes(input);
        int index = attributes.getIndex();
        double amount = attributes.getAmount();
        return new AddToBudgetCommand(budgetManager, index, amount);
    }

    public static Command parseModifyBudgetCommand(String input, BudgetManager budgetManager)
            throws BudgetParserException, BudgetAttributeException {
        // Expected format: set-budget n/BUDGET_NAME a/AMOUNT
        BudgetAttributes attributes = new BudgetAttributes(input);
        int index = isValidIndex(attributes.getIndex());
        String name = attributes.getName();
        double amount = isPositiveAmount(attributes.getAmount());
        LocalDate endDate = isFutureDate(attributes.getEndDate());
        String category = attributes.getCategory();
        return new ModifyBudgetCommand(budgetManager, index, amount, name, endDate, category);
    }

}
