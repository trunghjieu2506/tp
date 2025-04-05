package expenseincome.income;

import expenseincome.income.commands.IncomeCommand;

public class IncomeParserResult {
    private final IncomeCommand command;
    private final String errorMessage;

    public IncomeParserResult(IncomeCommand command, String errorMessage) {
        this.command = command;
        this.errorMessage = errorMessage;
    }

    public IncomeCommand getCommand() {
        return command;
    }

    public boolean hasCommand() {
        return command != null;
    }

    public String getFeedback() {
        return errorMessage;
    }

    public boolean hasFeedback() {
        return errorMessage != null;
    }
}
