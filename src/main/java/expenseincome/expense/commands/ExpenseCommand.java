package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;

public abstract class ExpenseCommand {
    public abstract void execute(ExpenseManager manager);
}
