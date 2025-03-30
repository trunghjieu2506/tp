package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;

public class ListExpenseCommand extends ExpenseCommand {
    @Override
    public void execute(ExpenseManager expenseManager) {
        assert expenseManager != null : "ExpenseManager instance cannot be null.";
        expenseManager.listExpenses();
    }
}
