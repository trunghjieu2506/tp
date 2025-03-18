package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

public class DeleteCommand extends ExpenseCommand {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager manager) {
        manager.deleteExpense(index);
    }
}
