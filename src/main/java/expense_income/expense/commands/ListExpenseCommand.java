package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

public class ListExpenseCommand extends ExpenseCommand {
    @Override
    public void execute(ExpenseManager expenseManager) {
        expenseManager.listExpenses();
    }
}
