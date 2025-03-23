package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

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
