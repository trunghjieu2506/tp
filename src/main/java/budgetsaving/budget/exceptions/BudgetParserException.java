package budgetsaving.budget.exceptions;

public class BudgetParserException extends BudgetException {
    private String detailMessage;

    public BudgetParserException(String message) {
        detailMessage = "Error in parsing your command: " + message;
    }

    public String getMessage(){
        return detailMessage;
    }
}
