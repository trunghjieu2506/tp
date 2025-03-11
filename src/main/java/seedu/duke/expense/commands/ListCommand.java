package seedu.duke.expense.commands;

import seedu.duke.expense.*;

public class ListCommand extends Command {
    @Override
    public void execute(ExpenseManager manager) {
        manager.listExpenses();
    }
}
