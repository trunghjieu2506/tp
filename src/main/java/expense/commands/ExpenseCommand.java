package expense.commands;

import expense.ExpenseManager;

public abstract class ExpenseCommand {
    public abstract void execute(ExpenseManager manager);
}
