package budgetsaving.budget.exceptions;

public class BudgetRuntimeException extends BudgetException {

    private String detailMessage;

    public BudgetRuntimeException(String message) {
        detailMessage = "Error: " + message;
    }

    public String getMessage(){
        return detailMessage;
    }
}
