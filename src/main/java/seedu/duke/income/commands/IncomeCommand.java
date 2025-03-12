package seedu.duke.income.commands;

import seedu.duke.income.*;

public abstract class IncomeCommand {
    public abstract void execute(IncomeManager manager);
}
