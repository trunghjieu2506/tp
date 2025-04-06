
package expenseincome.expense;

import expenseincome.expense.commands.ExpenseCommand;

public class ExpenseParserResult {
    private final ExpenseCommand command;
    private final String feedback;

    public ExpenseParserResult(ExpenseCommand command, String feedback) {
        this.command = command;
        this.feedback = feedback;
    }

    public ExpenseCommand getCommand() {
        return command;
    }

    public String getFeedback() {
        return feedback;
    }

    public boolean hasCommand() {
        return command != null;
    }

    public boolean hasFeedback() {
        return feedback != null && !feedback.isEmpty();
    }
}
