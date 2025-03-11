package seedu.duke.expense.commands;

import seedu.duke.expense.*;

public abstract class Command {
    public abstract void execute(ExpenseManager manager);
}
