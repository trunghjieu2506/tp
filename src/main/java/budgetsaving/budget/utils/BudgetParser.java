package budgetsaving.budget.utils;

import budgetsaving.budget.command.ModifyBudgetCommand;
import budgetsaving.budget.command.CheckBudgetCommand;
import budgetsaving.budget.command.DeductFromBudgetCommand;
import budgetsaving.budget.command.ListBudgetCommand;
import budgetsaving.budget.command.SetBudgetCommand;
import budgetsaving.budget.exceptions.BudgetAttributeException;
import budgetsaving.budget.exceptions.BudgetParserException;
import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;

import java.time.LocalDate;

//it should make sure that all the input to the command should follow its valid form, example:
//amount must be positive for set budget
public class BudgetParser {
    public static final String INDEX_IDENTIFIER = "i/";
    public static final String NAME_IDENTIFIER = "n/";
    public static final String AMOUNT_IDENTIFIER = "a/";
    public static final String END_DATE_IDENTIFIER = "e/";
    public static final String CATEGORY_IDENTIFIER = "c/";

    private static double isPositiveAmount(double amount) throws BudgetParserException {
        if (amount == -1){
            throw new BudgetParserException("Missing amount identifier or invalid amount.");
        }
        if (amount < 0) {
            throw new BudgetParserException("The amount you have entered is negative.");
        }
        if (amount < 0.01){
            throw new BudgetParserException("Minimum amount is 0.01.");
        }
        return amount;
    }

    private static LocalDate isFutureDate(LocalDate endDate) throws BudgetParserException {
        if (endDate != null && endDate.isBefore(LocalDate.now())) {
            throw new BudgetParserException("The end date you have entered cannot be before the start date.");
        }
        return endDate;
    }

    // each method will handle its own max index based on their size
    private static int isValidIndex(int index) throws BudgetParserException {
        if (index == -1){
            throw new BudgetParserException("Wrong index identifier or invalid index.");
        }
        if (index < 0) {
            throw new BudgetParserException("The index you have entered is out of range.");
        }
        return index;
    }

    private static String checkValidName(String name) throws BudgetParserException {
        if (name == null || name.isEmpty()){
            throw new BudgetParserException("Name identifier is missing or name is empty.");
        }
        return name;
    }

    private static String checkValidCategory(String category) throws BudgetParserException {
        if (category == null || category.isEmpty()){
            throw new BudgetParserException("Category identifier is missing or name is empty.");
        }
        return category;
    }

    public static Command parseSetBudgetCommand(String input, BudgetManager budgetManager)
            throws BudgetParserException, BudgetAttributeException {
        BudgetAttributes attributes = new BudgetAttributes(input);
        String name = checkValidName(attributes.getName());
        double amount = isPositiveAmount(attributes.getAmount());
        LocalDate endDate = isFutureDate(attributes.getEndDate());
        String category = checkValidCategory(attributes.getCategory());
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


    public static Command parseModifyBudgetCommand(String input, BudgetManager budgetManager)
            throws BudgetParserException, BudgetAttributeException {
        BudgetAttributes attributes = new BudgetAttributes(input);
        int index = isValidIndex(attributes.getIndex());
        String name = null;
        if (input.contains(NAME_IDENTIFIER)) {
            name = checkValidName(attributes.getName());
        }
        double amount = -1.0d;
        if (input.contains(AMOUNT_IDENTIFIER)) {
            amount = isPositiveAmount(attributes.getAmount());
        }
        LocalDate endDate = null;
        if (input.contains(END_DATE_IDENTIFIER)) {
            endDate = isFutureDate(attributes.getEndDate());
        }
        String category = null;
        if (input.contains(CATEGORY_IDENTIFIER)) {
            category = checkValidCategory(attributes.getCategory());
        }

        return new ModifyBudgetCommand(budgetManager, index, amount, name, endDate, category);
    }

}
