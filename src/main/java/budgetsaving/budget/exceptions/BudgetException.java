package budgetsaving.budget.exceptions;

import utils.io.IOHandler;

public class BudgetException extends Exception {

    public BudgetException() {
        super();
    }

    public BudgetException(String message) {
        IOHandler.writeError(message);
    }
}

