package budgetsaving.budget.exceptions;

import utils.io.IOHandler;

public class BudgetException extends Exception {

    public static final String ERROR_NOTIFIER = "Error initialising a command from the budget manager.";


    public BudgetException() {
        super();
    }

    public BudgetException(String message) {
        IOHandler.writeError(message);
    }
}

