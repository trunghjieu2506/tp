package seedu.duke.expense.commands;

import seedu.duke.expense.*;

public class ListExpenseCommand extends ExpenseCommand {
    @Override
    public void execute(ExpenseManager expenseManager) {
        expenseManager.listExpenses();
    }
}
