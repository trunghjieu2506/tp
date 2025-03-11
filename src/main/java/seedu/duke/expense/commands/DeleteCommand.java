package seedu.duke.expense.commands;

import seedu.duke.expense.*;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager manager) {
        manager.deleteExpense(index);
    }
}
