package budgetsaving.budget.exceptions;

public class BudgetNullCommandException {
    private static final String NULL_MESSAGE = "The command is invalid or empty. Wow this is an unexpected error!";

    // Since the attribute class is a subclass of parser, we can say its a parsing error
    public BudgetNullCommandException() {}

    public String getMessage(){
        return NULL_MESSAGE;
    }
}
