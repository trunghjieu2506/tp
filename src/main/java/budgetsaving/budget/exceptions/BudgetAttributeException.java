package budgetsaving.budget.exceptions;

import budgetsaving.budget.Budget;
import utils.io.IOHandler;

public class BudgetAttributeException extends BudgetException {
    private String detailMessage;

    // Since the attribute class is a subclass of parser, we can say its a parsing error
    public BudgetAttributeException(String message) {
        detailMessage = "Error in parsing your command: " + message;
    }

    public String getMessage(){
        return detailMessage;
    }
}
