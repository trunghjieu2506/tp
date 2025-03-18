package expense.commands;

import expense.ExpenseManager;

public class ListExpenseCommand extends ExpenseCommand {
    @Override
    public void execute(ExpenseManager expenseManager) {
        expenseManager.listExpenses();
    }
}
