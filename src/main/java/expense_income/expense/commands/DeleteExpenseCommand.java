package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

public class DeleteExpenseCommand extends ExpenseCommand {
    private int index;

    public DeleteExpenseCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager instance should not be null.";
        assert index >= 1 : "Index must be positive (should be checked before calling this method).";

        manager.deleteExpense(index);
    }
}
