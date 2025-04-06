package expenseincome.income;

import expenseincome.income.commands.IncomeCommand;
/**
 * Represents the result of parsing an income command.
 * Contains either a valid IncomeCommand or an error message.
 */
public class IncomeParserResult {
    private final IncomeCommand command;
    private final String errorMessage;
    /**
     * Constructs a parser result.
     *
     * @param command The parsed command object, or null if invalid.
     * @param errorMessage The error message to return to user, or null if no error.
     */
    public IncomeParserResult(IncomeCommand command, String errorMessage) {
        this.command = command;
        this.errorMessage = errorMessage;
    }
    /**
     * Returns the parsed command, if any.
     *
     * @return The IncomeCommand instance, or null if invalid.
     */
    public IncomeCommand getCommand() {
        return command;
    }
    /**
     * Checks if a command was successfully parsed.
     *
     * @return True if a command exists, false otherwise.
     */
    public boolean hasCommand() {
        return command != null;
    }
    /**
     * Gets the feedback message.
     *
     * @return The feedback or error message.
     */
    public String getFeedback() {
        return errorMessage;
    }
    /**
     * Checks if there is an error message.
     *
     * @return True if error feedback exists, false otherwise.
     */
    public boolean hasFeedback() {
        return errorMessage != null;
    }
}
