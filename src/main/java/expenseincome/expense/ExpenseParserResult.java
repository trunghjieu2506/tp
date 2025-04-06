
package expenseincome.expense;

import expenseincome.expense.commands.ExpenseCommand;
/**
 * Holds the result of parsing an expense command.
 * Can contain either a valid command or an error feedback message.
 */
public class ExpenseParserResult {
    private final ExpenseCommand command;
    private final String feedback;
    /**
     * Constructs a parser result with a command and feedback.
     *
     * @param command the parsed command (nullable if invalid)
     * @param feedback the feedback or error message (nullable if successful)
     */
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
    /**
     * Returns true if a valid command is present.
     *
     * @return true if command is not null
     */
    public boolean hasCommand() {
        return command != null;
    }
    /**
     * Returns true if feedback is present.
     *
     * @return true if feedback is non-empty
     */
    public boolean hasFeedback() {
        return feedback != null && !feedback.isEmpty();
    }
}
