package seedu.duke.expense.commands;

import seedu.duke.expense.*;

public abstract class ExpenseCommand {
    public abstract void execute(ExpenseManager manager);
}
