package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;

public class SortExpenseCommand extends ExpenseCommand {
    private boolean mostRecentFirst;

    public SortExpenseCommand(boolean mostRecentFirst) {
        this.mostRecentFirst = mostRecentFirst;
    }

    @Override
    public void execute(ExpenseManager manager) {
        manager.sortExpensesByDate(mostRecentFirst);
    }
}
