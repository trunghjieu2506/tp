package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

public abstract class ExpenseCommand {
    public abstract void execute(ExpenseManager manager);
}
